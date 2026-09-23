package ui;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;

import mx.desarrollo.entity.Asignar;
import mx.desarrollo.entity.Horario;
import mx.desarrollo.entity.Materia;
import mx.desarrollo.entity.Profesor;
import mx.desarrollo.persistencia.integration.ServiceLocator;

import java.text.Collator;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Named("consultasBean")
@RequestScoped
public class Consultas {

    private List<ProfesorConsulta> profesores;

    public List<ProfesorConsulta> getProfesores() {
        if (profesores == null) {
            profesores = consultaGeneral();
        }
        return profesores;
    }

    public int getTotalProfesores() {
        return getProfesores().size();
    }

    public boolean isHayProfesores() {
        return getTotalProfesores() > 0;
    }
    public static class UnidadAsignada {
        public final String materia;
        public final String tipo;
        public final String horario;
        public final int horas;

        public UnidadAsignada(String materia, String tipo, String horario, int horas) {
            this.materia = materia;
            this.tipo = tipo;
            this.horario = horario;
            this.horas = horas;
        }

        public String getMateria() { return materia; }
        public String getTipo() { return tipo; }
        public String getHorario() { return horario; }
        public int getHoras() { return horas; }
    }

    public static class ProfesorConsulta {
        public final long id;
        public final String nombre;
        public final String apellidoPaterno;
        public final String apellidoMaterno;
        public final String rfc;
        public final List<UnidadAsignada> unidades = new ArrayList<>();

        public ProfesorConsulta(long id, String nombre, String apellidoPaterno,
                                String apellidoMaterno, String rfc) {
            this.id = id;
            this.nombre = vacioSiNulo(nombre);
            this.apellidoPaterno = vacioSiNulo(apellidoPaterno);
            this.apellidoMaterno = vacioSiNulo(apellidoMaterno);
            this.rfc = vacioSiNulo(rfc);
        }

        public long getId() { return id; }
        public String getNombre() { return nombre; }
        public String getApellidoPaterno() { return apellidoPaterno; }
        public String getApellidoMaterno() { return apellidoMaterno; }
        public String getRfc() { return rfc; }
        public List<UnidadAsignada> getUnidades() { return unidades; }

        public int getTotalHoras() {
            int total = 0;
            for (UnidadAsignada u : unidades) {
                total += u.horas;
            }
            return total;
        }
    }

    private static class Fila {
        final long profesorId;
        final String nombre, apellidoPaterno, apellidoMaterno, rfc;
        final String materia, tipo, horario;
        final int horas;

        Fila(long profesorId, String nombre, String apellidoPaterno, String apellidoMaterno,
             String rfc, String materia, String tipo, String horario, int horas) {
            this.profesorId = profesorId;
            this.nombre = nombre;
            this.apellidoPaterno = apellidoPaterno;
            this.apellidoMaterno = apellidoMaterno;
            this.rfc = rfc;
            this.materia = materia;
            this.tipo = tipo;
            this.horario = horario;
            this.horas = horas;
        }
    }

    private static final DateTimeFormatter HORA = DateTimeFormatter.ofPattern("HH:mm");
    private static final Collator COLLATOR = Collator.getInstance(Locale.forLanguageTag("es-MX"));

    private static List<Fila> obtenerFilas() {
        EntityManager em = ServiceLocator.getInstanceProfesorDAO().getEntityManager();

        List<Profesor> profesores = em
                .createQuery("SELECT p FROM Profesor p", Profesor.class)
                .getResultList();
        List<Asignar> asignaciones = em
                .createQuery("SELECT a FROM Asignar a", Asignar.class)
                .getResultList();

        Map<Integer, List<Asignar>> porProfesor = new HashMap<>();
        for (Asignar a : asignaciones) {
            if (a.getProfesor() != null && a.getMateria() != null) {
                porProfesor.computeIfAbsent(a.getProfesor().getId(), k -> new ArrayList<>()).add(a);
            }
        }

        List<Fila> filas = new ArrayList<>();
        for (Profesor p : profesores) {
            List<Asignar> suyas = porProfesor.get(p.getId());

            if (suyas == null || suyas.isEmpty()) {
                filas.add(new Fila(p.getId(), p.getNombre(), p.getApellidoP(),
                        p.getApellidoM(), p.getRfc(), null, null, null, 0));
                continue;
            }

            for (Asignar a : suyas) {
                String tipo = normalizarTipo(a.getTipo());
                filas.add(new Fila(p.getId(), p.getNombre(), p.getApellidoP(),
                        p.getApellidoM(), p.getRfc(),
                        a.getMateria().getNombre(), tipo,
                        textoHorario(a.getHorario()),
                        horasDelTipo(a.getMateria(), tipo)));
            }
        }
        return filas;
    }

    public static List<ProfesorConsulta> consultaGeneral() {
        Map<Long, ProfesorConsulta> porProfesor = new LinkedHashMap<>();

        for (Fila f : obtenerFilas()) {
            ProfesorConsulta p = porProfesor.get(f.profesorId);
            if (p == null) {
                p = new ProfesorConsulta(f.profesorId, f.nombre,
                        f.apellidoPaterno, f.apellidoMaterno, f.rfc);
                porProfesor.put(f.profesorId, p);
            }
            if (f.materia != null) {
                p.unidades.add(new UnidadAsignada(f.materia, f.tipo, f.horario, f.horas));
            }
        }

        List<ProfesorConsulta> lista = new ArrayList<>(porProfesor.values());

        lista.sort(Comparator
                .comparing((ProfesorConsulta p) -> p.nombre, COLLATOR)
                .thenComparing(p -> p.apellidoPaterno, COLLATOR)
                .thenComparing(p -> p.apellidoMaterno, COLLATOR));

        for (ProfesorConsulta p : lista) {
            p.unidades.sort(Comparator
                    .comparing((UnidadAsignada u) -> u.materia, COLLATOR)
                    .thenComparing(u -> u.tipo, COLLATOR));
        }
        return lista;
    }

    private static String normalizarTipo(String tipo) {
        if (tipo == null) {
            return "";
        }
        switch (tipo.trim().toLowerCase(Locale.ROOT)) {
            case "clase":
            case "teoria":
            case "teoría":
                return "Clase";
            case "taller":
                return "Taller";
            case "laboratorio":
                return "Laboratorio";
            default:
                return tipo.trim();
        }
    }

    private static int horasDelTipo(Materia m, String tipo) {
        switch (tipo) {
            case "Clase":
                return horas(m.getHoraC());
            case "Taller":
                return horas(m.getHoraT());
            case "Laboratorio":
                return horas(m.getHoraL());
            default:
                return 0;
        }
    }

    private static String textoHorario(Horario h) {
        if (h == null) {
            return "";
        }
        String dia = vacioSiNulo(h.getDia());
        if (h.getHoraI() == null || h.getHoraF() == null) {
            return dia;
        }
        return (dia + " " + HORA.format(h.getHoraI()) + " - " + HORA.format(h.getHoraF())).trim();
    }

    private static int horas(Integer valor) {
        return valor == null ? 0 : valor;
    }

    private static String vacioSiNulo(String texto) {
        return texto == null ? "" : texto;
    }
}
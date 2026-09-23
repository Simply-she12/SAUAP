package ui;

import jakarta.persistence.EntityManager;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import mx.desarrollo.entity.Asignar;
import mx.desarrollo.entity.Horario;
import mx.desarrollo.entity.Materia;
import mx.desarrollo.entity.Profesor;
import mx.desarrollo.persistencia.integration.ServiceLocator;

import java.io.IOException;
import java.text.Collator;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@WebServlet("/api/consulta-asignaciones")
public class Consultas extends HttpServlet {

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

        public int getTotalHoras() {
            int total = 0;
            for (UnidadAsignada u : unidades) {
                total += u.horas;
            }
            return total;
        }
    }

    static class Fila {
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

    static List<Fila> obtenerFilas() {
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

    private static final Collator COLLATOR = Collator.getInstance(Locale.forLanguageTag("es-MX"));

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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            resp.getWriter().write(aJson(consultaGeneral()));
        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"No se pudo consultar las asignaciones\"}");
        }
    }

    static String aJson(List<ProfesorConsulta> profesores) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < profesores.size(); i++) {
            if (i > 0) {
                sb.append(',');
            }
            ProfesorConsulta p = profesores.get(i);
            sb.append("{\"id\":").append(p.id)
                    .append(",\"nombre\":").append(texto(p.nombre))
                    .append(",\"apellidoPaterno\":").append(texto(p.apellidoPaterno))
                    .append(",\"apellidoMaterno\":").append(texto(p.apellidoMaterno))
                    .append(",\"rfc\":").append(texto(p.rfc))
                    .append(",\"totalHoras\":").append(p.getTotalHoras())
                    .append(",\"unidades\":[");
            for (int j = 0; j < p.unidades.size(); j++) {
                if (j > 0) {
                    sb.append(',');
                }
                UnidadAsignada u = p.unidades.get(j);
                sb.append("{\"materia\":").append(texto(u.materia))
                        .append(",\"tipo\":").append(texto(u.tipo))
                        .append(",\"horario\":").append(texto(u.horario))
                        .append(",\"horas\":").append(u.horas).append('}');
            }
            sb.append("]}");
        }
        return sb.append(']').toString();
    }

    private static String texto(String valor) {
        StringBuilder sb = new StringBuilder("\"");
        for (char c : valor.toCharArray()) {
            switch (c) {
                case '"':  sb.append("\\\""); break;
                case '\\': sb.append("\\\\"); break;
                case '\n': sb.append("\\n");  break;
                case '\r': sb.append("\\r");  break;
                case '\t': sb.append("\\t");  break;
                default:
                    if (c < 0x20) {
                        sb.append(String.format("\\u%04x", (int) c));
                    } else {
                        sb.append(c);
                    }
            }
        }
        return sb.append('"').toString();
    }
}
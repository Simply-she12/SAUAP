package ui;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;

import mx.desarrollo.entity.Asignar;
import mx.desarrollo.entity.Horario;
import mx.desarrollo.entity.Materia;
import mx.desarrollo.entity.Profesor;
import mx.desarrollo.persistencia.integration.ServiceLocator;

import java.io.Serializable;
import java.text.Collator;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Named("consultas")
@SessionScoped
public class Consultas implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final int POR_PAGINA = 5;

    private static final DateTimeFormatter HORA =
            DateTimeFormatter.ofPattern("HH:mm");

    private static final Collator COLLATOR =
            Collator.getInstance(Locale.forLanguageTag("es-MX"));

    private String busqueda = "";

    private int paginaActual = 1;

    private boolean buscando = false;

    private String tipoBusqueda = "";

    private String mensajeBusqueda = "";

    private List<ProfesorConsulta> resultados = new ArrayList<>();


    public Consultas() {
        cargarConsultaGeneral();
    }


    public static class UnidadAsignada {

        private final String materia;
        private final String tipo;
        private final String horario;
        private final int horas;


        public UnidadAsignada(
                String materia,
                String tipo,
                String horario,
                int horas) {

            this.materia = materia;
            this.tipo = tipo;
            this.horario = horario;
            this.horas = horas;
        }


        public String getMateria() {
            return materia;
        }


        public String getTipo() {
            return tipo;
        }


        public String getHorario() {
            return horario;
        }


        public int getHoras() {
            return horas;
        }
    }


    public static class ProfesorConsulta {

        private final long id;
        private final String nombre;
        private final String apellidoPaterno;
        private final String apellidoMaterno;
        private final String rfc;

        private final List<UnidadAsignada> unidades =
                new ArrayList<>();


        public ProfesorConsulta(
                long id,
                String nombre,
                String apellidoPaterno,
                String apellidoMaterno,
                String rfc) {

            this.id = id;
            this.nombre = vacioSiNulo(nombre);
            this.apellidoPaterno = vacioSiNulo(apellidoPaterno);
            this.apellidoMaterno = vacioSiNulo(apellidoMaterno);
            this.rfc = vacioSiNulo(rfc);
        }


        public long getId() {
            return id;
        }


        public String getNombre() {
            return nombre;
        }


        public String getApellidoPaterno() {
            return apellidoPaterno;
        }


        public String getApellidoMaterno() {
            return apellidoMaterno;
        }


        public String getRfc() {
            return rfc;
        }


        public List<UnidadAsignada> getUnidades() {
            return unidades;
        }


        public String getNombreCompleto() {

            return (nombre + " " +
                    apellidoPaterno + " " +
                    apellidoMaterno)
                    .trim()
                    .replaceAll("\\s+", " ");
        }


        public int getTotalHoras() {

            int total = 0;

            for (UnidadAsignada u : unidades) {
                total += u.getHoras();
            }

            return total;
        }


        public int getCantidadAsignaciones() {
            return unidades.size();
        }
    }


    static class Fila {

        final long profesorId;

        final String nombre;
        final String apellidoPaterno;
        final String apellidoMaterno;
        final String rfc;

        final String materia;
        final String tipo;
        final String horario;

        final int horas;


        Fila(
                long profesorId,
                String nombre,
                String apellidoPaterno,
                String apellidoMaterno,
                String rfc,
                String materia,
                String tipo,
                String horario,
                int horas) {

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


    public void cargarConsultaGeneral() {

        buscando = false;
        tipoBusqueda = "";
        mensajeBusqueda = "";

        paginaActual = 1;

        resultados = consultaGeneral();
    }


    public void buscar() {

        String texto = busqueda == null
                ? ""
                : busqueda.trim();

        paginaActual = 1;

        if (texto.isEmpty()) {

            cargarConsultaGeneral();

            return;
        }

        String filtro = normalizar(texto);

        List<ProfesorConsulta> porNombre =
                buscarPorNombre(filtro);

        if (!porNombre.isEmpty()) {

            resultados = porNombre;

            buscando = true;

            tipoBusqueda = "profesor";

            mensajeBusqueda =
                    "Búsqueda de profesor: " + texto;

            return;
        }


        List<ProfesorConsulta> porMateria =
                buscarPorMateria(filtro);

        if (!porMateria.isEmpty()) {

            resultados = porMateria;

            buscando = true;

            tipoBusqueda = "materia";

            mensajeBusqueda =
                    "Profesores que imparten: " + texto;

            return;
        }


        resultados = new ArrayList<>();

        buscando = true;

        tipoBusqueda = "";

        mensajeBusqueda =
                "No se encontraron resultados para: " + texto;
    }


    public void paginaAnterior() {

        if (paginaActual > 1) {

            paginaActual--;
        }
    }


    public void paginaSiguiente() {

        if (paginaActual < getTotalPaginas()) {

            paginaActual++;
        }
    }


    public List<ProfesorConsulta> getResultadosPagina() {

        List<ProfesorConsulta> pagina =
                new ArrayList<>();

        int inicio =
                (paginaActual - 1) * POR_PAGINA;

        int fin =
                Math.min(
                        inicio + POR_PAGINA,
                        resultados.size()
                );

        if (inicio >= resultados.size()) {

            return pagina;
        }

        for (int i = inicio; i < fin; i++) {

            pagina.add(resultados.get(i));
        }

        return pagina;
    }


    public int getTotalPaginas() {

        if (resultados == null ||
                resultados.isEmpty()) {

            return 1;
        }

        return (int) Math.ceil(
                (double) resultados.size()
                        / POR_PAGINA
        );
    }


    public boolean isPuedeAnterior() {

        return paginaActual > 1;
    }


    public boolean isPuedeSiguiente() {

        return paginaActual < getTotalPaginas();
    }


    public int getPaginaActual() {

        return paginaActual;
    }


    public String getPaginaTexto() {

        return paginaActual +
                " / " +
                getTotalPaginas();
    }


    public boolean isSinResultados() {

        return resultados == null ||
                resultados.isEmpty();
    }


    public boolean isBusquedaPorMateria() {

        return "materia".equals(tipoBusqueda);
    }


    public boolean isBusquedaPorProfesor() {

        return "profesor".equals(tipoBusqueda);
    }


    public String getMensajeBusqueda() {

        return mensajeBusqueda;
    }


    public String getBusqueda() {

        return busqueda;
    }


    public void setBusqueda(String busqueda) {

        this.busqueda = busqueda;
    }


    public List<ProfesorConsulta> consultaGeneral() {

        Map<Long, ProfesorConsulta> porProfesor =
                new LinkedHashMap<>();

        for (Fila f : obtenerFilas()) {

            ProfesorConsulta p =
                    porProfesor.get(f.profesorId);

            if (p == null) {

                p = new ProfesorConsulta(
                        f.profesorId,
                        f.nombre,
                        f.apellidoPaterno,
                        f.apellidoMaterno,
                        f.rfc
                );

                porProfesor.put(
                        f.profesorId,
                        p
                );
            }

            if (f.materia != null) {

                p.getUnidades().add(
                        new UnidadAsignada(
                                f.materia,
                                f.tipo,
                                f.horario,
                                f.horas
                        )
                );
            }
        }


        List<ProfesorConsulta> lista =
                new ArrayList<>(
                        porProfesor.values()
                );


        /*
         * Los profesores con ID más alto
         * son los agregados más recientemente.
         */
        lista.sort(
                Comparator.comparingLong(
                        ProfesorConsulta::getId
                ).reversed()
        );


        for (ProfesorConsulta p : lista) {

            p.getUnidades().sort(
                    Comparator
                            .comparing(
                                    UnidadAsignada::getMateria,
                                    COLLATOR
                            )
                            .thenComparing(
                                    UnidadAsignada::getTipo,
                                    COLLATOR
                            )
            );
        }


        return lista;
    }


    private List<ProfesorConsulta> buscarPorNombre(
            String filtro) {

        List<ProfesorConsulta> encontrados =
                new ArrayList<>();

        for (ProfesorConsulta p : consultaGeneral()) {

            String nombre =
                    normalizar(
                            p.getNombreCompleto()
                    );

            if (nombre.contains(filtro)) {

                encontrados.add(p);
            }
        }


        encontrados.sort(
                Comparator.comparingLong(
                        ProfesorConsulta::getId
                ).reversed()
        );


        return encontrados;
    }


    private List<ProfesorConsulta> buscarPorMateria(
            String filtro) {

        List<ProfesorConsulta> encontrados =
                new ArrayList<>();

        for (ProfesorConsulta p : consultaGeneral()) {

            List<UnidadAsignada> coincidencias =
                    new ArrayList<>();

            for (UnidadAsignada u :
                    p.getUnidades()) {

                String materia =
                        normalizar(
                                u.getMateria()
                        );

                if (materia.contains(filtro)) {

                    coincidencias.add(u);
                }
            }


            if (!coincidencias.isEmpty()) {

                ProfesorConsulta resultado =
                        new ProfesorConsulta(
                                p.getId(),
                                p.getNombre(),
                                p.getApellidoPaterno(),
                                p.getApellidoMaterno(),
                                p.getRfc()
                        );


                /*
                 * IMPORTANTE:
                 * solamente agregamos las materias
                 * que coinciden con la búsqueda.
                 */
                resultado.getUnidades()
                        .addAll(coincidencias);


                encontrados.add(resultado);
            }
        }


        encontrados.sort(
                Comparator.comparingLong(
                        ProfesorConsulta::getId
                ).reversed()
        );


        return encontrados;
    }


    static List<Fila> obtenerFilas() {

        EntityManager em =
                ServiceLocator
                        .getInstanceProfesorDAO()
                        .getEntityManager();


        List<Profesor> profesores =
                em.createQuery(
                        "SELECT p FROM Profesor p",
                        Profesor.class
                ).getResultList();


        List<Asignar> asignaciones =
                em.createQuery(
                        "SELECT a FROM Asignar a",
                        Asignar.class
                ).getResultList();


        Map<Integer, List<Asignar>> porProfesor =
                new HashMap<>();


        for (Asignar a : asignaciones) {

            if (a.getProfesor() != null &&
                    a.getMateria() != null) {

                porProfesor
                        .computeIfAbsent(
                                a.getProfesor().getId(),
                                k -> new ArrayList<>()
                        )
                        .add(a);
            }
        }


        List<Fila> filas =
                new ArrayList<>();


        for (Profesor p : profesores) {

            List<Asignar> suyas =
                    porProfesor.get(p.getId());


            if (suyas == null ||
                    suyas.isEmpty()) {

                filas.add(
                        new Fila(
                                p.getId(),
                                p.getNombre(),
                                p.getApellidoP(),
                                p.getApellidoM(),
                                p.getRfc(),
                                null,
                                null,
                                null,
                                0
                        )
                );

                continue;
            }


            for (Asignar a : suyas) {

                String tipo =
                        normalizarTipo(
                                a.getTipo()
                        );


                filas.add(
                        new Fila(
                                p.getId(),
                                p.getNombre(),
                                p.getApellidoP(),
                                p.getApellidoM(),
                                p.getRfc(),
                                a.getMateria()
                                        .getNombre(),
                                tipo,
                                textoHorario(
                                        a.getHorario()
                                ),
                                horasDelTipo(
                                        a.getMateria(),
                                        tipo
                                )
                        )
                );
            }
        }


        return filas;
    }


    private static String normalizarTipo(
            String tipo) {

        if (tipo == null) {

            return "";
        }


        switch (
                tipo.trim()
                        .toLowerCase(
                                Locale.ROOT
                        )
        ) {

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


    private static int horasDelTipo(
            Materia m,
            String tipo) {

        switch (tipo) {

            case "Clase":
                return horas(
                        m.getHoraC()
                );

            case "Taller":
                return horas(
                        m.getHoraT()
                );

            case "Laboratorio":
                return horas(
                        m.getHoraL()
                );

            default:
                return 0;
        }
    }


    private static String textoHorario(
            Horario h) {

        if (h == null) {

            return "";
        }


        String dia =
                vacioSiNulo(
                        h.getDia()
                );


        if (h.getHoraI() == null ||
                h.getHoraF() == null) {

            return dia;
        }


        return (
                dia +
                        " " +
                        HORA.format(
                                h.getHoraI()
                        ) +
                        " - " +
                        HORA.format(
                                h.getHoraF()
                        )
        ).trim();
    }


    private static int horas(
            Integer valor) {

        return valor == null
                ? 0
                : valor;
    }


    private static String vacioSiNulo(
            String texto) {

        return texto == null
                ? ""
                : texto;
    }


    private static String normalizar(
            String texto) {

        if (texto == null) {

            return "";
        }

        return texto
                .normalize(
                        java.text.Normalizer.Form.NFD
                )
                .replaceAll(
                        "\\p{M}",
                        ""
                )
                .toLowerCase(
                        Locale.ROOT
                )
                .trim();
    }
}
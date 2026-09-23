package helper;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import mx.avanti.SAUAP.autenticacion.integration.ServiceFacadeLocator;
import mx.desarrollo.entity.Asignar;
import mx.desarrollo.entity.Materia;
import mx.desarrollo.entity.Profesor;

@Named("asignarHelper")
@ViewScoped
public class AsignarHelper implements Serializable {

    private Integer profesorId;
    private Integer materiaId;

    private String tipo;
    private String dia;

    private String horaInicio;
    private String horaFin;

    private String mensajeError;


    public void asignar() {

        mensajeError = null;


        /*
         * VALIDAR CAMPOS
         */

        if (profesorId == null ||
                materiaId == null ||
                tipo == null ||
                tipo.trim().isEmpty() ||
                dia == null ||
                dia.trim().isEmpty() ||
                horaInicio == null ||
                horaInicio.trim().isEmpty() ||
                horaFin == null ||
                horaFin.trim().isEmpty()) {

            mensajeError = "Debe completar todos los campos.";

            return;
        }


        /*
         * CONVERTIR LAS HORAS
         */

        LocalTime inicio;
        LocalTime fin;

        try {

            inicio = LocalTime.parse(horaInicio);
            fin = LocalTime.parse(horaFin);

        } catch (Exception e) {

            mensajeError = "Las horas no son válidas.";

            return;
        }


        /*
         * VALIDAR QUE LA HORA FINAL NO SEA
         * ANTES DE LA HORA INICIAL
         *
         * IMPORTANTE:
         * Si son iguales, son 0 horas y SÍ se permite.
         */

        if (fin.isBefore(inicio)) {

            mensajeError =
                    "La hora de fin no puede ser anterior a la hora de inicio.";

            return;
        }


        /*
         * CALCULAR DURACIÓN
         */

        long minutos = Duration
                .between(inicio, fin)
                .toMinutes();


        /*
         * VALIDAR MÍNIMO 0 HORAS
         *
         * Con la validación anterior no puede existir
         * una duración negativa.
         *
         * Por lo tanto:
         *
         * 08:00 - 08:00 = 0 horas -> permitido.
         */


        if (minutos < 0) {

            mensajeError =
                    "La duración no puede ser menor a 0 horas.";

            return;
        }


        /*
         * VALIDAR MÁXIMO 4 HORAS
         *
         * 4 horas = 240 minutos
         */

        if (minutos > 240) {

            mensajeError =
                    "La duración no puede ser mayor a 4 horas.";

            return;
        }


        /*
         * OBTENER ASIGNACIONES EXISTENTES
         */

        List<Asignar> asignaciones =
                ServiceFacadeLocator
                        .getInstanceFacadeAsignar()
                        .obtenerTodos();


        /*
         * VALIDAR TRASLAPES
         */

        for (Asignar asignacion : asignaciones) {

            if (asignacion.getProfesor() == null ||
                    asignacion.getHorario() == null) {

                continue;
            }


            Integer profesorExistente =
                    asignacion
                            .getProfesor()
                            .getId();


            String diaExistente =
                    asignacion
                            .getHorario()
                            .getDia();


            LocalTime inicioExistente =
                    asignacion
                            .getHorario()
                            .getHoraI();


            LocalTime finExistente =
                    asignacion
                            .getHorario()
                            .getHoraF();


            /*
             * REVISAR SOLO SI ES EL MISMO PROFESOR
             * Y EL MISMO DÍA
             */

            if (profesorExistente != null &&
                    profesorExistente.equals(profesorId) &&
                    diaExistente != null &&
                    diaExistente.equalsIgnoreCase(dia) &&
                    inicioExistente != null &&
                    finExistente != null) {


                /*
                 * VALIDAR TRASLAPE
                 */

                boolean hayTraslape =
                        inicio.isBefore(finExistente) &&
                                fin.isAfter(inicioExistente);


                if (hayTraslape) {

                    mensajeError =
                            "El profesor ya tiene una asignación que se traslapa con este horario.";

                    return;
                }
            }
        }


        /*
         * GUARDAR ASIGNACIÓN
         */

        ServiceFacadeLocator
                .getInstanceFacadeAsignar()
                .guardarAsignacion(
                        profesorId,
                        materiaId,
                        tipo,
                        dia,
                        inicio,
                        fin
                );


        /*
         * LIMPIAR FORMULARIO
         */

        profesorId = null;
        materiaId = null;
        tipo = null;
        dia = null;
        horaInicio = null;
        horaFin = null;


        /*
         * MENSAJE DE ÉXITO
         */

        mensajeError =
                "Asignación guardada correctamente.";
    }


    public Integer getProfesorId() {
        return profesorId;
    }

    public void setProfesorId(Integer profesorId) {
        this.profesorId = profesorId;
    }


    public Integer getMateriaId() {
        return materiaId;
    }

    public void setMateriaId(Integer materiaId) {
        this.materiaId = materiaId;
    }


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }


    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }


    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }


    public String getMensajeError() {
        return mensajeError;
    }


    public List<Profesor> getProfesores() {

        return ServiceFacadeLocator
                .getInstanceFacadeUsuario()
                .obtenerTodos();
    }


    public List<Materia> getMaterias() {

        return ServiceFacadeLocator
                .getInstanceFacadeMateria()
                .obtenerTodos();
    }


    public List<Asignar> getAsignaciones() {

        return ServiceFacadeLocator
                .getInstanceFacadeAsignar()
                .obtenerTodos();
    }
}
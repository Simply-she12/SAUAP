package helper;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;

import mx.avanti.SAUAP.autenticacion.integration.ServiceFacadeLocator;
import mx.desarrollo.entity.Asignar;
import mx.desarrollo.entity.Horario;
import mx.desarrollo.entity.Materia;
import mx.desarrollo.entity.Profesor;


import java.util.ArrayList;
import java.util.List;

@Named("asignarHelper")
@ViewScoped
public class AsignarHelper implements Serializable {

    private Integer profesorId;
    private Integer materiaId;
    private Integer horarioId;
    private String tipo;
    private String mensajeError;


    public void asignar() {

        mensajeError = null;

        if (profesorId == null ||
                materiaId == null ||
                horarioId == null ||
                tipo == null) {

            mensajeError = "Debe completar todos los campos.";
            return;
        }

        List<Asignar> asignaciones =
                ServiceFacadeLocator
                        .getInstanceFacadeAsignar()
                        .obtenerTodos();

        for (Asignar asignacion : asignaciones) {

            if (asignacion.getProfesor() != null &&
                    asignacion.getHorario() != null) {

                Integer profesorExistente =
                        asignacion.getProfesor().getId();

                Integer horarioExistente =
                        asignacion.getHorario().getIdhorario();

                if (profesorExistente.equals(profesorId) &&
                        horarioExistente.equals(horarioId)) {

                    mensajeError =
                            "El profesor ya tiene una asignacion en este horario.";

                    return;
                }
            }
        }

        ServiceFacadeLocator
                .getInstanceFacadeAsignar()
                .guardarAsignacion(
                        profesorId,
                        materiaId,
                        horarioId,
                        tipo
                );
    }
    public void actualizarHorarios() {

        System.out.println(
                "Actualizando horarios"
        );

        System.out.println(
                "Materia seleccionada: " + materiaId
        );

        System.out.println(
                "Tipo seleccionado: " + tipo
        );

        horarioId = null;
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
    public Integer getHorarioId() {
        return horarioId;
    }

    public void setHorarioId(Integer horarioId) {
        this.horarioId = horarioId;
    }
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
    public List<Horario> getHorarios() {

        System.out.println(
                "Buscando horarios"
        );

        System.out.println(
                "Materia: " + materiaId
        );

        System.out.println(
                "Tipo: " + tipo
        );

        if (materiaId == null || tipo == null) {
            return new ArrayList<>();
        }

        return ServiceFacadeLocator
                .getInstanceFacadeHorario()
                .obtenerPorMateriaYTipo(
                        materiaId,
                        tipo
                );
    }
    public List<Asignar> getAsignaciones() {

        return ServiceFacadeLocator
                .getInstanceFacadeAsignar()
                .obtenerTodos();
    }
}
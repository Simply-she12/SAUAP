package helper;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import mx.avanti.SAUAP.autenticacion.integration.ServiceFacadeLocator;
import mx.desarrollo.entity.Asignar;
import mx.desarrollo.entity.Materia;
import mx.desarrollo.entity.Profesor;

import java.util.List;

import static mx.desarrollo.entity.Asignar_.materia;

@Named("asignarHelper")
@RequestScoped
public class AsignarHelper {

    private Integer profesorId;
    private Integer materiaId;

    private String tipo;
    private Integer horas;




    public void asignar() {

        if (profesorId == null ||
                materiaId == null ||
                tipo == null ||
                horas == null) {

            System.out.println("Faltan datos");
            return;
        }

        if (horas < 0 || horas > 4) {

            System.out.println(
                    "Las horas deben estar entre 0 y 4"
            );

            return;
        }

        System.out.println("ID Profesor: " + profesorId);
        System.out.println("ID Materia: " + materiaId);
        System.out.println("Tipo: " + tipo);
        System.out.println("Horas: " + horas);

        ServiceFacadeLocator
                .getInstanceFacadeAsignar()
                .guardarAsignacion(
                        profesorId,
                        materiaId,
                        tipo,
                        horas
                );

        System.out.println(
                "Asignación guardada correctamente"
        );
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

    public Integer getHoras() {
        return horas;
    }

    public void setHoras(Integer horas) {
        this.horas = horas;
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
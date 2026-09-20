package helper;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named("asignarHelper")
@RequestScoped
public class AsignarHelper {

    private String profesor;
    private String materia;
    private String tipo;
    private Integer horas;

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
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

    public void asignar() {

        System.out.println("Profesor: " + profesor);
        System.out.println("Materia: " + materia);
        System.out.println("Tipo: " + tipo);
        System.out.println("Horas: " + horas);

    }
}
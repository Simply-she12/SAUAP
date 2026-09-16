package mx.desarrollo.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "asignar")
@IdClass(Asignar.class)
public class Asignar {

    @Id
    @ManyToOne
    @JoinColumn(name = "idProfesor")
    private Profesor profesor;

    @Id
    @ManyToOne
    @JoinColumn(name = "idmaterias")
    private Materia materia;

    public Asignar() {
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    @Override
    public String toString() {
        return "Asignar{" +
                "profesor=" + profesor +
                ", materia=" + materia +
                '}';
    }
}
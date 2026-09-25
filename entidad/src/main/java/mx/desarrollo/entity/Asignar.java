package mx.desarrollo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "asignar")
@IdClass(AsignarId.class)
public class Asignar {

    @Id
    @ManyToOne
    @JoinColumn(name = "idprofesor")
    private Profesor profesor;

    @Id
    @ManyToOne
    @JoinColumn(name = "idmaterias")
    private Materia materia;

    @Id
    @ManyToOne
    @JoinColumn(name = "idhorario")
    private Horario horario;

    @Column(name = "tipo")
    private String tipo;

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

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Asignar{" +
                "profesor=" + profesor +
                ", materia=" + materia +
                ", horario=" + horario +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
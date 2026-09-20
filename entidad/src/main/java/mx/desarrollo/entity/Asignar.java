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

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "hora")
    private Integer hora;

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getHora() {
        return hora;
    }

    public void setHora(Integer hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "Asignar{" +
                "profesor=" + profesor +
                ", materia=" + materia +
                ", tipo='" + tipo + '\'' +
                ", hora=" + hora +
                '}';
    }
}
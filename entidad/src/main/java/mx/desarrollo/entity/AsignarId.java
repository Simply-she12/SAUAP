package mx.desarrollo.entity;

import java.io.Serializable;
import java.util.Objects;

public class AsignarId implements Serializable {

    private Integer profesor;
    private Integer materia;
    private Integer horario;

    public AsignarId() {
    }

    public AsignarId(Integer profesor, Integer materia, Integer horario) {
        this.profesor = profesor;
        this.materia = materia;
        this.horario = horario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AsignarId)) return false;

        AsignarId that = (AsignarId) o;

        return Objects.equals(profesor, that.profesor)
                && Objects.equals(materia, that.materia)
                && Objects.equals(horario, that.horario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profesor, materia, horario);
    }
}
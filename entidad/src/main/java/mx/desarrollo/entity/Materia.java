package mx.desarrollo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "materia", schema = "sauap")
public class Materia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmaterias", nullable = false)
    private Integer id;

    @OneToMany(mappedBy = "materia")
    private List<Horario> horarios;
    @Size(max = 50)
    @Column(name = "nombre", length = 50)
    private String nombre;

    @Column(name = "horaC")
    private Integer horaC;

    @Column(name = "horaT")
    private Integer horaT;

    @Column(name = "horaL")
    private Integer horaL;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getHoraC() {
        return horaC;
    }

    public void setHoraC(Integer horaC) {
        this.horaC = horaC;
    }

    public Integer getHoraT() {
        return horaT;
    }

    public void setHoraT(Integer horaT) {
        this.horaT = horaT;
    }

    public Integer getHoraL() {
        return horaL;
    }

    public void setHoraL(Integer horaL) {
        this.horaL = horaL;
    }

    public List<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<Horario> horarios) {
        this.horarios = horarios;
    }

}
package helper;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import mx.avanti.SAUAP.autenticacion.integration.ServiceFacadeLocator;
import mx.desarrollo.entity.Materia;

@Named("materiaHelper")
@RequestScoped
public class MateriaHelper {

    private Integer id;
    private String nombre;
    private Integer horaC;
    private Integer horaT;
    private Integer horaL;

    public void registrar() {

        Materia materia = new Materia();

        materia.setId(id);
        materia.setNombre(nombre);
        materia.setHoraC(horaC);
        materia.setHoraT(horaT);
        materia.setHoraL(horaL);

        ServiceFacadeLocator
                .getInstanceFacadeMateria()
                .guardarMateria(materia);

        nombre = null;
        horaC = null;
        horaT = null;
        horaL = null;
    }


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
}
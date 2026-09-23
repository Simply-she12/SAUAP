package helper;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import mx.avanti.SAUAP.autenticacion.integration.ServiceFacadeLocator;
import mx.desarrollo.entity.Materia;

@Named("materiaHelper")
@RequestScoped
public class MateriaHelper {

    private String nombre;
    private Integer horaC;
    private Integer horaT;
    private Integer horaL;

    private String mensajeError;


    public void registrar() {

        mensajeError = null;


        if (horaC == null) {
            horaC = 0;
        }

        if (horaT == null) {
            horaT = 0;
        }

        if (horaL == null) {
            horaL = 0;
        }


        if (horaC < 0 || horaC > 4) {

            mensajeError =
                    "Las horas de Clase deben estar entre 0 y 4.";

            return;
        }


        if (horaT < 0 || horaT > 4) {

            mensajeError =
                    "Las horas de Taller deben estar entre 0 y 4.";

            return;
        }


        if (horaL < 0 || horaL > 4) {

            mensajeError =
                    "Las horas de Laboratorio deben estar entre 0 y 4.";

            return;
        }

        Materia materia = new Materia();


        materia.setNombre(nombre);

        materia.setHoraC(horaC);

        materia.setHoraT(horaT);

        materia.setHoraL(horaL);

        ServiceFacadeLocator
                .getInstanceFacadeMateria()
                .guardarMateria(materia);

        mensajeError =
                "Materia registrada correctamente.";
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


    public String getMensajeError() {

        return mensajeError;
    }
}
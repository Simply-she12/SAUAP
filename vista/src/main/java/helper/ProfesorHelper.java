package helper;

import mx.avanti.SAUAP.autenticacion.delegate.DelegateProfesor;
import mx.desarrollo.entity.Profesor;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named("profesorHelper")
@RequestScoped
public class ProfesorHelper implements Serializable {

    private Profesor profesor;
    private DelegateProfesor delegateProfesor;
    private String mensaje;

    public ProfesorHelper() {
        this.profesor = new Profesor();
        this.delegateProfesor = new DelegateProfesor();
    }

    public void registrar() {
        try {

            String rfc = profesor.getRfc();

            if (rfc == null || !rfc.toUpperCase().matches("^[A-ZÑ&]{4}\\d{6}[A-Z0-9]{3}$")) {
                this.mensaje = "El RFC no tiene un formato válido";
                return;
            }

            profesor.setRfc(rfc.toUpperCase());

            delegateProfesor.saveProfesor(profesor);

            this.mensaje = "Profesor registrado correctamente";
            this.profesor = new Profesor();

        } catch (Exception e) {
            e.printStackTrace();
            this.mensaje = "No se pudo registrar el profesor, intenta de nuevo";
        }
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
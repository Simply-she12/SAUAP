package helper;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import mx.avanti.SAUAP.autenticacion.integration.ServiceFacadeLocator;
import mx.desarrollo.entity.Profesor;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

@Named("loginHelper")
@RequestScoped
public class LoginHelper {

    private String idProfesor;
    private String password;
    private boolean recordarme;
    private boolean error;

    public String getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(String idProfesor) {
        this.idProfesor = idProfesor;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRecordarme() {
        return recordarme;
    }

    public void setRecordarme(boolean recordarme) {
        this.recordarme = recordarme;
    }
    public boolean isError() {
        return error;
    }
    public String login() {

        Profesor profesor = ServiceFacadeLocator
                .getInstanceFacadeUsuario()
                .login(idProfesor, password);

        if (profesor != null) {
            error = false;
            return "Menu.xhtml?faces-redirect=true";
        }

        error = true;
        return null;
    }
}
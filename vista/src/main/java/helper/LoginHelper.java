package helper;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import mx.avanti.SAUAP.autenticacion.integration.ServiceFacadeLocator;
import mx.desarrollo.entity.Profesor;

@Named("loginHelper")
@RequestScoped
public class LoginHelper {

    private String idProfesor;
    private String password;
    private boolean recordarme;

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
    public String login() {

        Profesor profesor = ServiceFacadeLocator
                .getInstanceFacadeUsuario()
                .login(idProfesor);

        if (profesor != null) {
            return "menu.xhtml?faces-redirect=true";
        }

        return null;
    }
}
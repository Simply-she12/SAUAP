package ui;

import helper.LoginHelper;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import mx.desarrollo.entity.Materia;
import mx.desarrollo.entity.Profesor;

import java.io.IOException;
import java.io.Serializable;

@Named("loginUI")
@SessionScoped
public class LoginBeanUI implements Serializable {
    private LoginHelper loginHelper;
    private Profesor profesor;

    public LoginBeanUI() {
        loginHelper = new LoginHelper();
    }

    /**
     * Metodo postconstructor todo lo que este dentro de este metodo
     * sera la primero que haga cuando cargue la pagina
     */
    @PostConstruct
    public void init(){
        profesor= new Profesor();
    }

    public void login() throws IOException {
        String appURL = "/index.xhtml";
        // los atributos de usuario vienen del xhtml
        Profesor pr= new Profesor();
        pr.setId(0);
        pr = loginHelper.Login(profesor.getNombre(), profesor.getRfc());
        if(pr != null && pr.getId()!=null){
            // asigno el usuario encontrado al usuario de esta clase para que
            // se muestre correctamente en la pagina de informacion
            profesor=pr;
            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + appURL);
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario o contraseña incorrecta:", "Intente de nuevo"));
        }
    }


    /* getters y setters*/

    public Profesor getUsuario() {
        return profesor;
    }

    public void setUsuario(Profesor usuario) {
        this.profesor = usuario;
    }












}

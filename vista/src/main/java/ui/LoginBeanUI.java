package ui;

import helper.LoginHelper;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named("loginUI")
@SessionScoped
public class LoginBeanUI implements Serializable {

    private LoginHelper loginHelper;

    public LoginBeanUI() {
        loginHelper = new LoginHelper();
    }
}
package helper;

import mx.avanti.SAUAP.autenticacion.integration.ServiceFacadeLocator;
import mx.desarrollo.entity.Profesor;

public class LoginHelper {

    /**
     * Metodo para hacer login llamara a la instancia de usuarioFacade
     *
     * @param correo
     * @param password
     * @return
     */
    public Profesor Login(String correo, String password){
        return ServiceFacadeLocator.getInstanceFacadeUsuario().login(password, correo);
    }
}

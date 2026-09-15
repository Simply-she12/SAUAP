package helper;

import mx.avanti.SAUAP.autenticacion.integration.ServiceFacadeLocator;
import mx.desarrollo.entity.Profesor;

public class LoginHelper {

    /**
     * Metodo para hacer login llamara a la instancia de usuarioFacade
     *
     * @param rfc
     * @return
     */
    public Profesor Login(String rfc){
        return ServiceFacadeLocator.getInstanceFacadeUsuario().login(rfc);
    }
}

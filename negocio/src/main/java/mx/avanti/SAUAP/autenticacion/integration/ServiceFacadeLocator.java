package mx.avanti.SAUAP.autenticacion.integration;

import mx.avanti.SAUAP.autenticacion.facade.FacadeMateria;
import mx.avanti.SAUAP.autenticacion.facade.FacadeProfesor;

public class ServiceFacadeLocator {
    private static FacadeMateria facadeMateria;
    private static FacadeProfesor facadeProfesor;

    public static FacadeMateria getInstanceFacadeMateria() {
        if (facadeMateria == null) {
            facadeMateria = new FacadeMateria();
            return facadeMateria;
        } else {
            return facadeMateria;
        }
    }

    public static FacadeProfesor getInstanceFacadeUsuario() {
        if (facadeProfesor == null) {
            facadeProfesor = new FacadeProfesor();
            return facadeProfesor;
        } else {
            return facadeProfesor;
        }
    }
}

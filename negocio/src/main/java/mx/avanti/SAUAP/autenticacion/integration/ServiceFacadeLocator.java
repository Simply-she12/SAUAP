package mx.avanti.SAUAP.autenticacion.integration;

import mx.avanti.SAUAP.autenticacion.facade.FacadeAsignar;
import mx.avanti.SAUAP.autenticacion.facade.FacadeMateria;
import mx.avanti.SAUAP.autenticacion.facade.FacadeProfesor;

public class ServiceFacadeLocator {

    private static FacadeMateria facadeMateria;
    private static FacadeProfesor facadeProfesor;
    private static FacadeAsignar facadeAsignar;

    public static FacadeMateria getInstanceFacadeMateria() {

        if (facadeMateria == null) {
            facadeMateria = new FacadeMateria();
        }

        return facadeMateria;
    }

    public static FacadeProfesor getInstanceFacadeUsuario() {

        if (facadeProfesor == null) {
            facadeProfesor = new FacadeProfesor();
        }

        return facadeProfesor;
    }

    public static FacadeAsignar getInstanceFacadeAsignar() {

        if (facadeAsignar == null) {
            facadeAsignar = new FacadeAsignar();
        }

        return facadeAsignar;
    }
}
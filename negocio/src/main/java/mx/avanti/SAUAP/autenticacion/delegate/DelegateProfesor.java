package mx.avanti.SAUAP.autenticacion.delegate;

import mx.desarrollo.entity.Profesor;
import mx.desarrollo.persistencia.integration.ServiceLocator;

import java.util.List;

public class DelegateProfesor {

    public Profesor login(String idProfesor, String password) {

        List<Profesor> profesores =
                ServiceLocator.getInstanceProfesorDAO().findAll();

        for (Profesor pr : profesores) {

            System.out.println("ID BD: " + pr.getId());
            System.out.println("Password BD: " + pr.getPassword());
            System.out.println("ID recibido: " + idProfesor);
            System.out.println("Password recibido: " + password);

            if (String.valueOf(pr.getId()).equals(idProfesor)
                    && password != null
                    && password.equals(pr.getPassword())) {

                System.out.println("ID y contraseña correctos");
                return pr;
            }
        }

        System.out.println("ID o contraseña incorrectos");
        return null;
    }


    public void saveProfesor(Profesor profesor) {
        ServiceLocator.getInstanceProfesorDAO().save(profesor);
    }

    public List<Profesor> obtenerTodos() {
        return ServiceLocator.getInstanceProfesorDAO().obtenerTodos();
    }
}
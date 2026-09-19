package mx.avanti.SAUAP.autenticacion.delegate;

import mx.desarrollo.entity.Profesor;
import mx.desarrollo.persistencia.integration.ServiceLocator;

import java.util.List;

public class DelegateProfesor {

    public Profesor login(String idProfesor) {

        System.out.println("ID recibido: " + idProfesor);

        List<Profesor> profesores =
                ServiceLocator.getInstanceProfesorDAO().findAll();

        for (Profesor pr : profesores) {

            System.out.println("ID encontrado en BD: " + pr.getId());

            if (String.valueOf(pr.getId()).equals(idProfesor)) {
                System.out.println("Profesor encontrado");
                return pr;
            }
        }

        System.out.println("Profesor NO encontrado");
        return null;
    }

//    public Profesor login(String idProfesor) {
//
//        List<Profesor> profesores =
//                ServiceLocator.getInstanceProfesorDAO().findAll();
//
//        for (Profesor pr : profesores) {
//
//            if (String.valueOf(pr.getId()).equals(idProfesor)) {
//                return pr;
//            }
//        }
//
//        return null;
//    }

    public void saveProfesor(Profesor profesor) {
        ServiceLocator.getInstanceProfesorDAO().save(profesor);
    }

    public List<Profesor> obtenerTodos() {
        return ServiceLocator.getInstanceProfesorDAO().obtenerTodos();
    }
}
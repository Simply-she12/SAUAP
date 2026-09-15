package mx.avanti.SAUAP.autenticacion.delegate;

import mx.desarrollo.entity.Profesor;
import mx.desarrollo.persistencia.integration.ServiceLocator;

import java.util.List;

public class DelegateProfesor {
    public Profesor login(String rfc){
        Profesor profesor = new Profesor();
        List<Profesor> profesores = ServiceLocator.getInstanceProfesorDAO().findAll();

        for(Profesor pr:profesores){
            if(pr.getRfc().equalsIgnoreCase(rfc)){
                profesor = pr;
            }
        }
        return profesor;
    }

    public void saveProfesor(Profesor profesor){
        ServiceLocator.getInstanceProfesorDAO().save(profesor);
    }

}
package mx.avanti.SAUAP.autenticacion.delegate;

import mx.desarrollo.entity.Profesor;
import mx.desarrollo.persistencia.integration.ServiceLocator;

import java.util.List;

public class DelegateProfesor {
    public Profesor login(String password, String correo){
        Profesor profesor = new Profesor();
        List<Profesor> profesores = ServiceLocator.getInstanceProfesorDAO().findAll();

        for(Profesor pr:profesores){
            if(pr.getNombre().equalsIgnoreCase(password) && pr.getRfc().equalsIgnoreCase(correo)){
                profesor = pr;
            }
        }
        return profesor;
    }

    public void saveProfesor(Profesor usuario){
        ServiceLocator.getInstanceProfesorDAO().save(usuario);
    }

}
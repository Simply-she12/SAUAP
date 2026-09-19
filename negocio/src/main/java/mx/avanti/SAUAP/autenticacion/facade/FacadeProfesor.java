package mx.avanti.SAUAP.autenticacion.facade;

import mx.avanti.SAUAP.autenticacion.delegate.DelegateProfesor;
import mx.desarrollo.entity.Profesor;
import mx.desarrollo.persistencia.integration.ServiceLocator;

import java.util.List;

public class FacadeProfesor {
          private final DelegateProfesor delegateProfesor;

        public FacadeProfesor() {

            this.delegateProfesor = new DelegateProfesor();
        }

    public Profesor login(String idProfesor) {
        return delegateProfesor.login(idProfesor);
    }

        public void saveProfesor(Profesor profesor){

            delegateProfesor.saveProfesor(profesor);
        }

    public List<Profesor> obtenerTodos(){
        return ServiceLocator.getInstanceProfesorDAO().obtenerTodos();
    }
}

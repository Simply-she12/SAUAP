package mx.avanti.SAUAP.autenticacion.facade;

import mx.avanti.SAUAP.autenticacion.delegate.DelegateProfesor;
import mx.desarrollo.entity.Profesor;

public class FacadeProfesor {
          private final DelegateProfesor delegateProfesor;

        public FacadeProfesor() {
            this.delegateProfesor = new DelegateProfesor();
        }

        public Profesor login(String rfc){
            return delegateProfesor.login(rfc);
        }

        public void saveProfesor(Profesor profesor){
            delegateProfesor.saveProfesor(profesor);
        }

}

package mx.avanti.SAUAP.autenticacion.facade;

import mx.avanti.SAUAP.autenticacion.delegate.DelegateProfesor;
import mx.desarrollo.entity.Profesor;

public class FacadeProfesor {
          private final DelegateProfesor delegateProfesor;

        public FacadeProfesor() {
            this.delegateProfesor = new DelegateProfesor();
        }

        public Profesor login(String password, String correo){
            return delegateProfesor.login(password, correo);
        }

        public void saveUsario(Profesor profesor){
            delegateProfesor.saveProfesor(profesor);
        }

}

package mx.avanti.SAUAP.autenticacion.delegate;

import mx.desarrollo.entity.Materia;
import mx.avanti.SAUAP.*;
import mx.desarrollo.persistencia.integration.ServiceLocator;

public class DelegateMateria {
    public void saveMateria(Materia materia){
        ServiceLocator.getInstanceMateriaDAO().save(materia);
    }

}
package mx.avanti.SAUAP.autenticacion.delegate;

import mx.desarrollo.entity.Materia;
import mx.desarrollo.persistencia.integration.ServiceLocator;

import java.util.List;

public class DelegateMateria {
    public void saveMateria(Materia materia){
        ServiceLocator.getInstanceMateriaDAO().save(materia);

    }
    public List<Materia> obtenerTodos(){
        return ServiceLocator.getInstanceMateriaDAO().obtenerTodos();
    }
}
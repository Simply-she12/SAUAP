package mx.avanti.SAUAP.autenticacion.facade;

import mx.avanti.SAUAP.autenticacion.delegate.DelegateAsignar;
import mx.desarrollo.entity.Asignar;

import java.util.List;

public class FacadeAsignar {

    private final DelegateAsignar delegateAsignar;

    public FacadeAsignar() {
        this.delegateAsignar = new DelegateAsignar();
    }

    public void guardarAsignacion(
            Integer idProfesor,
            Integer idMateria,
            String tipo,
            Integer hora) {

        delegateAsignar.guardarAsignacion(
                idProfesor,
                idMateria,
                tipo,
                hora
        );
    }

    public List<Asignar> obtenerTodos() {
        return delegateAsignar.obtenerTodos();
    }
}
package mx.avanti.SAUAP.autenticacion.delegate;

import mx.desarrollo.entity.Asignar;
import mx.desarrollo.persistencia.integration.ServiceLocator;

import java.util.List;

public class DelegateAsignar {

    public void guardarAsignacion(
            Integer idProfesor,
            Integer idMateria,
            Integer idHorario,
            String tipo) {

        ServiceLocator
                .getInstanceAsignarDAO()
                .guardarAsignacion(
                        idProfesor,
                        idMateria,
                        idHorario,
                        tipo
                );
    }

    public List<Asignar> obtenerTodos() {
        return ServiceLocator
                .getInstanceAsignarDAO()
                .obtenerTodos();
    }
}
package mx.avanti.SAUAP.autenticacion.delegate;

import mx.desarrollo.entity.Asignar;
import mx.desarrollo.persistencia.integration.ServiceLocator;

import java.util.List;

public class DelegateAsignar {

    public void guardarAsignacion(
            Integer idProfesor,
            Integer idMateria,
            String tipo,
            Integer hora) {

        ServiceLocator
                .getInstanceAsignarDAO()
                .guardarAsignacion(
                        idProfesor,
                        idMateria,
                        tipo,
                        hora
                );
    }

    public List<Asignar> obtenerTodos() {
        return ServiceLocator
                .getInstanceAsignarDAO()
                .obtenerTodos();
    }
}
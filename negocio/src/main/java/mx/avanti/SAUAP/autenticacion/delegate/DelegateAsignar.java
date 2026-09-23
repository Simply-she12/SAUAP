package mx.avanti.SAUAP.autenticacion.delegate;

import mx.desarrollo.entity.Asignar;
import mx.desarrollo.persistencia.integration.ServiceLocator;

import java.time.LocalTime;
import java.util.List;

public class DelegateAsignar {


    public void guardarAsignacion(
            Integer idProfesor,
            Integer idMateria,
            String tipo,
            String dia,
            LocalTime horaInicio,
            LocalTime horaFin) {


        ServiceLocator
                .getInstanceAsignarDAO()
                .guardarAsignacion(
                        idProfesor,
                        idMateria,
                        tipo,
                        dia,
                        horaInicio,
                        horaFin
                );
    }


    public List<Asignar> obtenerTodos() {

        return ServiceLocator
                .getInstanceAsignarDAO()
                .obtenerTodos();
    }
}
package mx.avanti.SAUAP.autenticacion.delegate;

import mx.desarrollo.entity.Horario;
import mx.desarrollo.persistencia.integration.ServiceLocator;

import java.util.List;

public class DelegateHorario {

    public List<Horario> obtenerTodos() {

        return ServiceLocator
                .getInstanceHorarioDAO()
                .obtenerTodos();
    }

    public List<Horario> obtenerPorMateriaYTipo(
            Integer idMateria,
            String tipo) {

        return ServiceLocator
                .getInstanceHorarioDAO()
                .obtenerPorMateriaYTipo(
                        idMateria,
                        tipo
                );
    }
}
package mx.avanti.SAUAP.autenticacion.facade;

import mx.avanti.SAUAP.autenticacion.delegate.DelegateHorario;
import mx.desarrollo.entity.Horario;

import java.util.List;

public class FacadeHorario {

    private final DelegateHorario delegateHorario;

    public FacadeHorario() {
        this.delegateHorario = new DelegateHorario();
    }

    public List<Horario> obtenerTodos() {

        return delegateHorario.obtenerTodos();
    }

    public List<Horario> obtenerPorMateriaYTipo(
            Integer idMateria,
            String tipo) {

        return delegateHorario.obtenerPorMateriaYTipo(
                idMateria,
                tipo
        );
    }
}
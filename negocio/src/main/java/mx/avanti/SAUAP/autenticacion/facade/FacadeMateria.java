package mx.avanti.SAUAP.autenticacion.facade;


import mx.avanti.SAUAP.autenticacion.delegate.DelegateMateria;
import mx.desarrollo.entity.Materia;

public class FacadeMateria {

    private final DelegateMateria delegateMateria;

    public FacadeMateria() {
        this.delegateMateria = new DelegateMateria();
    }

    public void guardarMateria(Materia materia){
        delegateMateria.saveMateria(materia);
    }

}

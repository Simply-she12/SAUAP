package mx.desarrollo.persistencia.dao;

import jakarta.persistence.EntityManager;
import mx.desarrollo.persistencia.*;
import mx.desarrollo.entity.Profesor;
import mx.desarrollo.persistencia.persistence.AbstractDAO;

import java.util.List;

public class ProfesorDAO extends AbstractDAO<Profesor> {
    private final EntityManager entityManager;

    public ProfesorDAO(EntityManager em) {
        super(Profesor.class);
        this.entityManager=em;
    }

    public List<Profesor> obtenerTodos(){
        return entityManager
                .createQuery("SELECT u FROM Profesor u", Profesor.class)
                .getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}

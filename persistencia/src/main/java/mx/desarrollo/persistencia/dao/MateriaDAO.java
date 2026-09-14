package mx.desarrollo.persistencia.dao;

import jakarta.persistence.EntityManager;
import mx.desarrollo.persistencia.*;
import mx.desarrollo.entity.Materia;
import mx.desarrollo.persistencia.persistence.AbstractDAO;


import java.util.List;


public class MateriaDAO extends AbstractDAO<Materia> {
    private final EntityManager entityManager;

    public MateriaDAO(EntityManager em) {
        super(Materia.class);
        this.entityManager = em;
    }

    public List<Materia> obtenerTodos(){
        return entityManager
                .createQuery("SELECT a FROM Materia a", Materia.class)
                .getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}


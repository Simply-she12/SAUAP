package mx.desarrollo.persistencia.dao;

import jakarta.persistence.EntityManager;
import mx.desarrollo.entity.Asignar;

import java.util.List;

public class AsignarDAO {

    private EntityManager entityManager;

    public AsignarDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Asignar asignar) {
        entityManager.getTransaction().begin();
        entityManager.persist(asignar);
        entityManager.getTransaction().commit();
    }

    public List<Asignar> findAll() {
        return entityManager
                .createQuery("SELECT a FROM Asignar a", Asignar.class)
                .getResultList();
    }
}
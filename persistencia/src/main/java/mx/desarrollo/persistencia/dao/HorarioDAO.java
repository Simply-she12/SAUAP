package mx.desarrollo.persistencia.dao;

import jakarta.persistence.EntityManager;
import mx.desarrollo.entity.Horario;
import mx.desarrollo.persistencia.persistence.AbstractDAO;

import java.util.List;

public class HorarioDAO extends AbstractDAO<Horario> {

    private final EntityManager entityManager;

    public HorarioDAO(EntityManager em) {
        super(Horario.class);
        this.entityManager = em;
    }

    public List<Horario> obtenerTodos() {

        return entityManager
                .createQuery(
                        "SELECT h FROM Horario h",
                        Horario.class
                )
                .getResultList();
    }

    public List<Horario> obtenerPorMateriaYTipo(
            Integer idMateria,
            String tipo) {

        System.out.println("DAO materia: " + idMateria);
        System.out.println("DAO tipo: " + tipo);

        return entityManager
                .createQuery(
                        "SELECT h FROM Horario h " +
                                "WHERE h.materia.id = :idMateria " +
                                "AND h.tipo = :tipo",
                        Horario.class
                )
                .setParameter("idMateria", idMateria)
                .setParameter("tipo", tipo)
                .getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
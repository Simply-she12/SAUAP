package mx.desarrollo.persistencia.dao;

import jakarta.persistence.EntityManager;
import mx.desarrollo.entity.Asignar;
import mx.desarrollo.entity.Materia;
import mx.desarrollo.entity.Profesor;
import mx.desarrollo.persistencia.persistence.AbstractDAO;

import java.util.List;

public class AsignarDAO extends AbstractDAO<Asignar> {

    private final EntityManager entityManager;

    public AsignarDAO(EntityManager em) {
        super(Asignar.class);
        this.entityManager = em;
    }

    public void guardarAsignacion(
            Integer idProfesor,
            Integer idMateria,
            String tipo,
            Integer hora) {

        System.out.println("DAO: ENTRÓ A guardarAsignacion");

        executeInsideTransaction(em -> {

            System.out.println("DAO: INICIANDO TRANSACCIÓN");

            Profesor profesor =
                    em.getReference(
                            Profesor.class,
                            idProfesor
                    );

            Materia materia =
                    em.getReference(
                            Materia.class,
                            idMateria
                    );

            System.out.println("DAO: Profesor = " + idProfesor);
            System.out.println("DAO: Materia = " + idMateria);

            Asignar asignar = new Asignar();

            asignar.setProfesor(profesor);
            asignar.setMateria(materia);
            asignar.setTipo(tipo);
            asignar.setHora(hora);

            System.out.println("DAO: ANTES DE PERSIST");

            em.persist(asignar);

            System.out.println("DAO: DESPUÉS DE PERSIST");

            em.flush();

            System.out.println("DAO: DESPUÉS DE FLUSH");
        });

        System.out.println("DAO: TRANSACCIÓN TERMINADA");
    }

    public List<Asignar> obtenerTodos() {
        return entityManager
                .createQuery(
                        "SELECT a FROM Asignar a",
                        Asignar.class
                )
                .getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
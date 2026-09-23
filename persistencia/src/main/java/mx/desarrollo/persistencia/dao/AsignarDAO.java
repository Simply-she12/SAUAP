package mx.desarrollo.persistencia.dao;

import jakarta.persistence.EntityManager;

import mx.desarrollo.entity.Asignar;
import mx.desarrollo.entity.Horario;
import mx.desarrollo.entity.Materia;
import mx.desarrollo.entity.Profesor;
import mx.desarrollo.persistencia.persistence.AbstractDAO;

import java.time.LocalTime;
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
            String dia,
            LocalTime horaInicio,
            LocalTime horaFin) {


        System.out.println(
                "DAO: ENTRÓ A guardarAsignacion"
        );


        executeInsideTransaction(em -> {


            System.out.println(
                    "DAO: INICIANDO TRANSACCIÓN"
            );


            /*
             * OBTENER PROFESOR
             */

            Profesor profesor =
                    em.getReference(
                            Profesor.class,
                            idProfesor
                    );


            /*
             * OBTENER MATERIA
             */

            Materia materia =
                    em.getReference(
                            Materia.class,
                            idMateria
                    );


            /*
             * CREAR HORARIO
             */

            Horario horario =
                    new Horario();


            horario.setDia(dia);

            horario.setHoraI(horaInicio);

            horario.setHoraF(horaFin);

            horario.setTipo(tipo);

            horario.setMateria(materia);


            /*
             * GUARDAR HORARIO
             */

            em.persist(horario);

            em.flush();


            System.out.println(
                    "DAO: HORARIO GUARDADO"
            );

            System.out.println(
                    "DAO: ID HORARIO = "
                            + horario.getIdhorario()
            );


            /*
             * CREAR ASIGNACIÓN
             */

            Asignar asignar =
                    new Asignar();


            asignar.setProfesor(profesor);

            asignar.setMateria(materia);

            asignar.setHorario(horario);

            asignar.setTipo(tipo);


            /*
             * GUARDAR ASIGNACIÓN
             */

            em.persist(asignar);

            em.flush();


            System.out.println(
                    "DAO: ASIGNACIÓN GUARDADA"
            );

        });


        System.out.println(
                "DAO: TRANSACCIÓN TERMINADA"
        );
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
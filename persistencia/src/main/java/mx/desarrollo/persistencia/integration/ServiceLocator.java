package mx.desarrollo.persistencia.integration;

import jakarta.persistence.EntityManager;
import mx.desarrollo.persistencia.*;
import mx.desarrollo.persistencia.dao.AsignarDAO;
import mx.desarrollo.persistencia.dao.MateriaDAO;
import mx.desarrollo.persistencia.dao.ProfesorDAO;
import mx.desarrollo.persistencia.persistence.HibernateUtil;
import mx.desarrollo.persistencia.dao.HorarioDAO;

public class ServiceLocator {

    private static MateriaDAO materiaDAO;
    private static ProfesorDAO profesorDAO;
    private static AsignarDAO asignarDAO;
    private static HorarioDAO horarioDAO;

    private static EntityManager getEntityManager(){
        return HibernateUtil.getEntityManager();
    }

    /**
     * se crea la instancia para alumno DAO si esta no existe
     */
    public static MateriaDAO getInstanceMateriaDAO(){
        if(materiaDAO == null){
            materiaDAO = new MateriaDAO(getEntityManager());
            return materiaDAO;
        } else{
            return materiaDAO;
        }
    }
    /**
     * se crea la instancia de usuarioDAO si esta no existe
     */
    public static ProfesorDAO getInstanceProfesorDAO(){
        if(profesorDAO == null){
            profesorDAO = new ProfesorDAO(getEntityManager());
            return profesorDAO;
        } else{
            return profesorDAO;
        }
    }


    public static AsignarDAO getInstanceAsignarDAO() {
        if (asignarDAO == null) {
            asignarDAO = new AsignarDAO(getEntityManager());
            return asignarDAO;
        } else {
            return asignarDAO;
        }
    }
    public static HorarioDAO getInstanceHorarioDAO() {
        if (horarioDAO == null) {
            horarioDAO = new HorarioDAO(getEntityManager());
            return horarioDAO;
        } else {
            return horarioDAO;
        }
    }

}

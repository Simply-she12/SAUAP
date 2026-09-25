package mx.desarrollo.persistencia;
import mx.desarrollo.entity.Asignar;
import mx.desarrollo.entity.Materia;
import mx.desarrollo.entity.Profesor;
import mx.desarrollo.persistencia.dao.AsignarDAO;
import mx.desarrollo.persistencia.dao.MateriaDAO;
import mx.desarrollo.persistencia.dao.ProfesorDAO;
import mx.desarrollo.persistencia.persistence.HibernateUtil;

public class testDAO {

    public static void main(String[] args) {
        MateriaDAO materiaDAO = new MateriaDAO(HibernateUtil.getEntityManager());
        ProfesorDAO profesorDAO = new ProfesorDAO(HibernateUtil.getEntityManager());
        AsignarDAO asignarDAO = new AsignarDAO(HibernateUtil.getEntityManager());



        for (Materia materia : materiaDAO.findAll()) {
            System.out.println(materia + "|| id [" + materia.getId()+ "]");
        }

        for (Profesor profesor : profesorDAO.findAll()) {
            System.out.println(profesor + "|| id [" + profesor.getId()+ "]");
        }

        for (Asignar asignar : asignarDAO.findAll()) {
            System.out.println(asignar);
        }
    }
}

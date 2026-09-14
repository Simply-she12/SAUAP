package mx.desarrollo.persistencia;
import mx.desarrollo.entity.Materia;
import mx.desarrollo.persistencia.dao.MateriaDAO;
import mx.desarrollo.persistencia.persistence.HibernateUtil;

public class testDAO {

    public static void main(String[] args) {
        MateriaDAO materiaDAO = new MateriaDAO(HibernateUtil.getEntityManager());



        for (Materia materia : materiaDAO.findAll()) {
            System.out.println(materia + "|| id [" + materia.getId()+ "]");
        }
    }
}

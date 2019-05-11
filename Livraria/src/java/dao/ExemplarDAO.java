package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import model.Exemplar;
import util.PersistenceUtil;

public class ExemplarDAO implements CrudDAO<Exemplar> {

    public static ExemplarDAO exemplarDAO;

    public static ExemplarDAO getInstance() {
        if (exemplarDAO == null) {
            exemplarDAO = new ExemplarDAO();
        }
        return exemplarDAO;
    }    

    @Override
    public Exemplar buscarId(int id) { 
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Exemplar> query = em.createQuery("select e from Exemplar e where e.id =:id ", Exemplar.class);
        query.setParameter("id", id);

        try {
            return query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public List<Exemplar> buscarTodas() {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Exemplar> query = em.createQuery("from Exemplar As e", Exemplar.class);
        return query.getResultList();
    }

    @Override
    public List<Exemplar> buscarInstancia() {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Exemplar> query = em.createQuery("select distinct e from Exemplar e group by e.circular", Exemplar.class);
        return query.getResultList();
    }

    @Override
    public void remover(Exemplar exemplar) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        if (!em.contains(exemplar)) {
            exemplar = em.merge(exemplar);
        }
        em.remove(exemplar);
        em.getTransaction().commit();
    }

    @Override
    public Exemplar persistir(Exemplar exemplar) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        try {
            exemplar = em.merge(exemplar);
            em.getTransaction().commit();
            System.out.println("Registro Exemplar gravado com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exemplar;
    }

    @Override
    public void removeAll() {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery(" delete from Exemplar");
        query.executeUpdate();
        em.getTransaction().commit();
    }

}

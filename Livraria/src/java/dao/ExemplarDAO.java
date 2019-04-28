/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import model.Emprestimo;
import model.Exemplar;
import util.PersistenceUtil;

/**
 *
 * @author John Peter
 */
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
        Query query = em.createQuery("select a from Exemplar a where a.id =:id ");
        query.setParameter("id", id);

        List<Exemplar> exemplar = query.getResultList();
        if (exemplar != null && exemplar.size() > 0) {
            return exemplar.get(0);
        }
        return null;
    }

    @Override
    public List<Exemplar> buscarTodas() {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("from Exemplar As a");
        return query.getResultList();
    }

    @Override
    public List<Exemplar> buscarInstancia() {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("select distinct a from Exemplar a group by a.exemplar");
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import model.Exemplar;
import util.PersistenceUtil;

/**
 *
 * @author John Peter
 */
public class ExemplarDAO implements CrudDAO<Exemplar>{
    
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
        Query query = em.createQuery("from Editora As a");
        return query.getResultList();
    }

    @Override
    public List<Exemplar> buscarInstancia() {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("select distinct a from Editora a group by a.editora");
        return query.getResultList();
    }
    
    @Override
    public void remover(Exemplar editora) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        if (!em.contains(editora)) {
            editora = em.merge(editora);
        }
        em.remove(editora);
        em.getTransaction().commit();
    }

    @Override
    public Exemplar persistir(Exemplar editora) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        try {
            editora = em.merge(editora);
            em.getTransaction().commit();
            System.out.println("Registro Editora gravado com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return editora;
    }

    @Override
    public void removeAll() {
       EntityManager em = PersistenceUtil.getEntityManager();
       em.getTransaction().begin();
       Query query = em.createQuery(" delete from Editora");
       query.executeUpdate();
       em.getTransaction().commit();
    }
    
}

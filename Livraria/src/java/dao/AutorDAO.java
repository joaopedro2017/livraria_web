/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.Autor;
import util.PersistenceUtil;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author John Peter
 */
public class AutorDAO implements Serializable{
    
    public static AutorDAO autoresDAO;

    public static AutorDAO getInstance() {
        if (autoresDAO == null) {
            autoresDAO = new AutorDAO();
        }
        return autoresDAO;
    }
    
    public Autor buscar(String nome) {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("select a from Autor a where a.nomeAutor =:nome ");
        query.setParameter("nome", nome);

        List<Autor> autores = query.getResultList();
        if (autores != null && autores.size() > 0) {
            return autores.get(0);
        }
        return null;
    }

    public List<Autor> buscarTodas() {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("from Autor As a");
        return query.getResultList();
    }

    public List<Autor> buscarTbAutoresInstancia() {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("select distinct a from Autor a group by a.autores");
        return query.getResultList();
    }
    
    public void remover(Autor autores) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        if (!em.contains(autores)) {
            autores = em.merge(autores);
        }
        em.remove(autores);
        em.getTransaction().commit();
    }

    public Autor persistir(Autor autores) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        try {
            autores = em.merge(autores);
            em.getTransaction().commit();
            System.out.println("Registro Autor gravado com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return autores;
    }

    public void removeAll() {
       EntityManager em = PersistenceUtil.getEntityManager();
       em.getTransaction().begin();
       Query query = em.createQuery(" delete from Autor ");
       query.executeUpdate();
       em.getTransaction().commit();
    }    
}

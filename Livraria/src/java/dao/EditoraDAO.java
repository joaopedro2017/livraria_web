/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.Editora;
import util.PersistenceUtil;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author John Peter
 */
public class EditoraDAO implements Serializable, CrudDAO<Editora>{
    
    public static EditoraDAO editoraDAO;

    public static EditoraDAO getInstance() {
        if (editoraDAO == null) {
            editoraDAO = new EditoraDAO();
        }
        return editoraDAO;
    }    
    
    @Override
    public Editora buscarId(int id) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Editora> query = em.createQuery("select e from Editora e where e.id =:id ", Editora.class);
        query.setParameter("id", id);

        Editora editora = query.getSingleResult();
        if (editora != null) {
            return editora;
        }
        return null;
    }
    
    public Editora buscar(String nome) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Editora> query = em.createQuery("select e from Editora e where e.nomeEditora =:nome ", Editora.class);
        query.setParameter("nome", nome);

        Editora editora = query.getSingleResult();
        if (editora != null) {
            return editora;
        }
        return null;
    }

    @Override
    public List<Editora> buscarTodas() {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Editora> query = em.createQuery("from Editora As e", Editora.class);
        return query.getResultList();
    }

    @Override
    public List<Editora> buscarInstancia() {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Editora> query = em.createQuery("select distinct e from Editora e group by e.nomeEditora", Editora.class);
        return query.getResultList();
    }
    
    @Override
    public void remover(Editora editora) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        if (!em.contains(editora)) {
            editora = em.merge(editora);
        }
        em.remove(editora);
        em.getTransaction().commit();
    }

    @Override
    public Editora persistir(Editora editora) {
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

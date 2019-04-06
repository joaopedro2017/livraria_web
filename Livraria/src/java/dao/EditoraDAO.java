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

/**
 *
 * @author John Peter
 */
public class EditoraDAO implements Serializable{
    
    public static EditoraDAO editoraDAO;

    public static EditoraDAO getInstance() {
        if (editoraDAO == null) {
            editoraDAO = new EditoraDAO();
        }
        return editoraDAO;
    }
    
    public Editora buscarId(int id) {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("select a from Editora a where a.id =:id ");
        query.setParameter("id", id);

        List<Editora> editora = query.getResultList();
        if (editora != null && editora.size() > 0) {
            return editora.get(0);
        }

        return null;
    }
    
    public Editora buscar(String nome) {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("select a from Editora a where a.nomeEditora =:nome ");
        query.setParameter("nome", nome);

        List<Editora> editora = query.getResultList();
        if (editora != null && editora.size() > 0) {
            return editora.get(0);
        }

        return null;
    }

    public List<Editora> buscarTodas() {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("from Editora As a");
        return query.getResultList();
    }

    public List<Editora> buscarTbEditoraInstancia() {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("select distinct a from Editora a group by a.editora");
        return query.getResultList();
    }
    
    public void remover(Editora editora) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        if (!em.contains(editora)) {
            editora = em.merge(editora);
        }
        em.remove(editora);
        em.getTransaction().commit();
    }

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

    public void removeAll() {
       EntityManager em = PersistenceUtil.getEntityManager();
       em.getTransaction().begin();
       Query query = em.createQuery(" delete from Editora");
       query.executeUpdate();
       em.getTransaction().commit();
    }
    
}

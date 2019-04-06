package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import util.PersistenceUtil;
import model.Assunto;
import java.io.Serializable;

public class AssuntoDAO implements Serializable{

    public static AssuntoDAO assuntoDAO;

    public static AssuntoDAO getInstance() {
        if (assuntoDAO == null) {
            assuntoDAO = new AssuntoDAO();
        }
        return assuntoDAO;
    }
    
    public Assunto buscar(String nome) {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("select a from Assunto a where a.nomeAssunto =:nome ");
        query.setParameter("nome", nome);

        List<Assunto> assunto = query.getResultList();
        if (assunto != null && assunto.size() > 0) {
            return assunto.get(0);
        }

        return null;
    }

    public List<Assunto> buscarTodas() {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("from Assunto As a");
        return query.getResultList();
    }

    public List<Assunto> buscarTbAssuntoInstancia() {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("select distinct a from Assunto a group by a.assunto");
        return query.getResultList();
    }
    
    public void remover(Assunto assunto) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        if (!em.contains(assunto)) {
            assunto = em.merge(assunto);
        }
        em.remove(assunto);
        em.getTransaction().commit();
    }

    public Assunto persistir(Assunto assunto) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        try {
            assunto = em.merge(assunto);
            em.getTransaction().commit();
            System.out.println("Registro Assunto gravado com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return assunto;
    }

    public void removeAll() {
       EntityManager em = PersistenceUtil.getEntityManager();
       em.getTransaction().begin();
       Query query = em.createQuery(" delete from Assunto ");
       query.executeUpdate();
       em.getTransaction().commit();
    }

}

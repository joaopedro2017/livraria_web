package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import util.PersistenceUtil;
import model.Assunto;
import java.io.Serializable;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class AssuntoDAO implements Serializable, CrudDAO<Assunto> {

    public static AssuntoDAO assuntoDAO;

    public static AssuntoDAO getInstance() {
        if (assuntoDAO == null) {
            assuntoDAO = new AssuntoDAO();
        }
        return assuntoDAO;
    }

    public Assunto buscar(String nome) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Assunto> query = em.createQuery("select a from Assunto a where a.nomeAssunto =:nome ", Assunto.class);
        query.setParameter("nome", nome);

        try {
            return query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public Assunto buscarId(int id) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Assunto> query = em.createQuery("select a from Assunto a where a.id =:id ", Assunto.class);
        query.setParameter("id", id);

        try {
            return query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public List<Assunto> buscarTodas() {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Assunto> query = em.createQuery("from Assunto As a", Assunto.class);
        return query.getResultList();
    }

    @Override
    public List<Assunto> buscarInstancia() {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Assunto> query = em.createQuery("select distinct a from Assunto a group by a.assunto", Assunto.class);
        return query.getResultList();
    }

    @Override
    public void remover(Assunto assunto
    ) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        if (!em.contains(assunto)) {
            assunto = em.merge(assunto);
        }
        em.remove(assunto);
        em.getTransaction().commit();
    }

    @Override
    public Assunto persistir(Assunto assunto
    ) {
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

    @Override
    public void removeAll() {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery(" delete from Assunto ");
        query.executeUpdate();
        em.getTransaction().commit();
    }
}

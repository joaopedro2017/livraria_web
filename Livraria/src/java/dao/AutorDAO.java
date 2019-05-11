package dao;

import model.Autor;
import util.PersistenceUtil;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class AutorDAO implements Serializable, CrudDAO<Autor>{
    
    public static AutorDAO autorDAO;

    public static AutorDAO getInstance() {
        if (autorDAO == null) {
            autorDAO = new AutorDAO();
        }
        return autorDAO;
    }
    
    public Autor buscar(String nome) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Autor> query = em.createQuery("select a from Autor a where a.nomeAutor =:nome ", Autor.class);
        query.setParameter("nome", nome);

        try {
            return query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
    
    @Override
    public Autor buscarId(int id) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Autor> query = em.createQuery("select a from Autor a where a.id =:id ", Autor.class);
        query.setParameter("id", id);

        try {
            return query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public List<Autor> buscarTodas() {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Autor> query = em.createQuery("from Autor As a", Autor.class);
        return query.getResultList();
    }

    @Override
    public List<Autor> buscarInstancia() {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Autor> query = em.createQuery("select distinct a from Autor a group by a.nomeAutor", Autor.class);
        return query.getResultList();
    }
    
    @Override
    public void remover(Autor autores) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        if (!em.contains(autores)) {
            autores = em.merge(autores);
        }
        em.remove(autores);
        em.getTransaction().commit();
    }

    @Override
    public Autor persistir(Autor autor) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        try {
            autor = em.merge(autor);
            em.getTransaction().commit();
            System.out.println("Registro Autor gravado com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return autor;
    }

    @Override
    public void removeAll() {
       EntityManager em = PersistenceUtil.getEntityManager();
       em.getTransaction().begin();
       Query query = em.createQuery(" delete from Autor ");
       query.executeUpdate();
       em.getTransaction().commit();
    }    
}

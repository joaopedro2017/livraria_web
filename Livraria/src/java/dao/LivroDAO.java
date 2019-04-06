package dao;

import model.Livro;
import util.PersistenceUtil;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class LivroDAO implements Serializable {

    public static LivroDAO livroDAO;

    public static LivroDAO getInstance() {
        if (livroDAO == null) {
            livroDAO = new LivroDAO();
        }
        return livroDAO;
    }
    
    public Livro buscar(String titulo) {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("select a from Livro a where a.titulo =:titulo ");
        query.setParameter("titulo", titulo);

        List<Livro> livro = query.getResultList();
        if (livro != null && livro.size() > 0) {
            return livro.get(0);
        }

        return null;
    }

    public List<Livro> buscarTodas() {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("from Livro As a");
        return query.getResultList();
    }

    public List<Livro> buscarTbEditoraInstancia() {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("select distinct a from Livro a group by a.livro");
        return query.getResultList();
    }
    
    public void remover(Livro livro) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        if (!em.contains(livro)) {
            livro = em.merge(livro);
        }
        em.remove(livro);
        em.getTransaction().commit();
    }

    public Livro persistir(Livro livro) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        try {
            livro = em.merge(livro);
            em.getTransaction().commit();
            System.out.println("Registro Livro gravado com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return livro;
    }

    public void removeAll() {
       EntityManager em = PersistenceUtil.getEntityManager();
       em.getTransaction().begin();
       Query query = em.createQuery(" delete from Livro");
       query.executeUpdate();
       em.getTransaction().commit();
    }   
    
}

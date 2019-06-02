package dao;

import model.Livro;
import util.PersistenceUtil;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class LivroDAO implements Serializable, CrudDAO<Livro> {

    public static LivroDAO livroDAO;

    public static LivroDAO getInstance() {
        if (livroDAO == null) {
            livroDAO = new LivroDAO();
        }
        return livroDAO;
    }

    public Livro buscar(String titulo) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Livro> query = em.createQuery("select l from Livro l where l.titulo =:titulo ", Livro.class);
        query.setParameter("titulo", titulo);

        try {
            return query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public List<Object[]> qntLivroExemplares() {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Object[]> query = em.createQuery("SELECT l.titulo,"
                + "l.isbn,"
                + "l.edicao,"
                + "l.ano,"
                + "l.assuntoid.nomeAssunto,"
                + "l.editoraid.nomeEditora,"
                + "(Select COUNT(e) from Exemplar e WHERE e.circular =:valor AND e.livroid.id = l.id),"
                + "(Select COUNT(e) from Exemplar e WHERE e.circular =:falso AND e.livroid.id = l.id)"
                + "from Livro l", Object[].class);

        query.setParameter("valor", Boolean.TRUE);
        query.setParameter("falso", Boolean.FALSE);

        return query.getResultList();
    }

    @Override
    public Livro buscarId(int id) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Livro> query = em.createQuery("select l from Livro l where l.id =:id ", Livro.class);
        query.setParameter("id", id);

        try {
            return query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public List<Livro> buscarTodas() {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Livro> query = em.createQuery("from Livro As l", Livro.class);
        return query.getResultList();
    }

    @Override
    public List<Livro> buscarInstancia() {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Livro> query = em.createQuery("select distinct l from Livro l group by l.editoraid.nomeEditora", Livro.class);
        return query.getResultList();
    }

    @Override
    public void remover(Livro livro) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        if (!em.contains(livro)) {
            livro = em.merge(livro);
        }
        em.remove(livro);
        em.getTransaction().commit();
    }

    @Override
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

    @Override
    public void removeAll() {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery(" delete from Livro");
        query.executeUpdate();
        em.getTransaction().commit();
    }

}

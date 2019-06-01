package dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import model.Emprestimo;
import model.Livro;
import util.PersistenceUtil;

public class EmprestimoDAO implements CrudDAO<Emprestimo> {

    public Long verificarDebito(int id) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Long> query = em.createQuery("select COUNT(e) from Emprestimo e "
                + "where e.usuarioid.id =:id "
                + "AND e.dataDevolucao IS NULL "
                + "AND e.dataPrevista < current_date", Long.class);
        query.setParameter("id", id);

        return query.getSingleResult();
    }

    public Date diasBloqueado(int id) throws SQLException {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Date> query = em.createQuery("SELECT MAX(  e.dataDevolucao ) from Emprestimo e "
                + "WHERE e.usuarioid.id =:id "
                + "AND e.dataDevolucao IS NOT NULL "
                + "AND e.dataDevolucao > dataPrevista", Date.class);
        query.setParameter("id", id);

        return query.getSingleResult();
    }

    public Long verificarEmAberto(int id) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Long> query = em.createQuery("select COUNT(e) from Emprestimo e "
                + "where e.usuarioid.id =:id "
                + "AND e.dataDevolucao IS NULL "
                + "AND e.dataPrevista > current_date ", Long.class);
        query.setParameter("id", id);

        return query.getSingleResult();
    }

    public Long exemplarDisponivel(int id) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Long> query = em.createQuery("select COUNT(e) from Emprestimo e "
                + "where e.dataDevolucao IS NULL "
                + "AND e.exemplarid.id =:id ", Long.class);
        query.setParameter("id", id);

        return query.getSingleResult();
    }

    public List<Livro> livrosEmprestados() {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Livro> query = em.createQuery("SELECT e.exemplarid.livroid from Emprestimo e "
                + "WHERE e.dataDevolucao IS NULL", Livro.class);
        return query.getResultList();
    }

    public List<Livro> livrosAtrasados() {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Livro> query = em.createQuery("SELECT e.exemplarid.livroid from Emprestimo e "
                + "WHERE e.dataDevolucao IS NULL AND e.dataPrevista < current_date", Livro.class);

        return query.getResultList();
    }

    @Override
    public Emprestimo buscarId(int id) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Emprestimo> query = em.createQuery("select e from Emprestimo e where e.id =:id ", Emprestimo.class);
        query.setParameter("id", id);

        try {
            return query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public List<Emprestimo> buscarTodas() {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Emprestimo> query = em.createQuery("from Emprestimo As e", Emprestimo.class);
        return query.getResultList();
    }

    @Override
    public List<Emprestimo> buscarInstancia() {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Emprestimo> query = em.createQuery("select distinct e from Emprestimo e group by e.dataEmprestimo", Emprestimo.class);
        return query.getResultList();
    }

    @Override
    public void remover(Emprestimo emprestimo) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        if (!em.contains(emprestimo)) {
            emprestimo = em.merge(emprestimo);
        }
        em.remove(emprestimo);
        em.getTransaction().commit();
    }

    @Override
    public Emprestimo persistir(Emprestimo emprestimo) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        try {
            emprestimo = em.merge(emprestimo);
            em.getTransaction().commit();
            System.out.println("Registro Emprestimo gravado com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emprestimo;
    }

    @Override
    public void removeAll() {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery(" delete from Emprestimo");
        query.executeUpdate();
        em.getTransaction().commit();
    }

}

package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import model.Livro;
import model.Reserva;
import util.PersistenceUtil;

public class ReservaDAO implements CrudDAO<Reserva> {

    public List<Livro> livrosReservados() {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Livro> query = em.createQuery("SELECT r.exemplarid.livroid from Reserva r WHERE r.cancelar is null", Livro.class);
        return query.getResultList();
    }

    public List<Reserva> cancelamentoAutomatico() {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Reserva> query = em.createQuery("Select r from Reserva r Where r.emprestimoid is null "
                + "AND r.cancelar is null AND r.dataReserva < current_date", Reserva.class);
        return query.getResultList();
    }

    @Override
    public Reserva buscarId(int id) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Reserva> query = em.createQuery("select r from Reserva r where r.id =:id ", Reserva.class);
        query.setParameter("id", id);

        try {
            return query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public List<Reserva> buscarTodas() {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Reserva> query = em.createQuery("from Reserva As r", Reserva.class);
        return query.getResultList();
    }

    @Override
    public List<Reserva> buscarInstancia() {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Reserva> query = em.createQuery("select distinct r from Reserva r group by r.cancelar", Reserva.class);
        return query.getResultList();
    }

    @Override
    public void remover(Reserva reserva) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        if (!em.contains(reserva)) {
            reserva = em.merge(reserva);
        }
        em.remove(reserva);
        em.getTransaction().commit();
    }

    @Override
    public Reserva persistir(Reserva reserva) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        try {
            reserva = em.merge(reserva);
            em.getTransaction().commit();
            System.out.println("Registro Reserva gravado com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reserva;
    }

    @Override
    public void removeAll() {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery(" delete from Reserva ");
        query.executeUpdate();
        em.getTransaction().commit();
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import model.Reserva;
import util.PersistenceUtil;

/**
 *
 * @author alunoces
 */
public class ReservaDAO implements CrudDAO<Reserva> {

    @Override
    public Reserva buscarId(int id) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Reserva> query = em.createQuery("select r from Reserva r where r.id =:id ", Reserva.class);
        query.setParameter("id", id);

        Reserva reserva = query.getSingleResult();
        if (reserva != null) {
            return reserva;
        }
        return null;
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

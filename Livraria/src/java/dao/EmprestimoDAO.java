/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import model.Emprestimo;
import util.PersistenceUtil;

/**
 *
 * @author John Peter
 */
public class EmprestimoDAO implements CrudDAO<Emprestimo> {

    public Integer verificarDebito(int id) {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("select COUNT(e) from Emprestimo e where e.usuarioid.id =:id and e.dataDevolucao <= :hoje ");
        query.setParameter("id", id);
        query.setParameter("hoje", new Date());

        return Integer.parseInt(query.getSingleResult().toString());      
    }
    
    public Integer verificarEmAberto(int id) {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("select COUNT(e) from Emprestimo e where e.usuarioid.id =:id and e.dataDevolucao >= :hoje ");
        query.setParameter("id", id);
        query.setParameter("hoje", new Date());

        return Integer.parseInt(query.getSingleResult().toString());        
    }
    
    public Integer exemplarDisponivel(int id) {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("select COUNT(e) from Emprestimo e where e.exemplarid.id =:id ");
        query.setParameter("id", id);        

        return Integer.parseInt(query.getSingleResult().toString());        
    }   

    @Override
    public Emprestimo buscarId(int id) {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("select e from Emprestimo e where e.id =:id ");
        query.setParameter("id", id);

        List<Emprestimo> emprestimo = query.getResultList();
        if (emprestimo != null && emprestimo.size() > 0) {
            return emprestimo.get(0);
        }
        return null;
    }

    @Override
    public List<Emprestimo> buscarTodas() {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("from Emprestimo As e");
        return query.getResultList();
    }

    @Override
    public List<Emprestimo> buscarInstancia() {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("select distinct e from Emprestimo e group by e.dataEmprestimo");
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

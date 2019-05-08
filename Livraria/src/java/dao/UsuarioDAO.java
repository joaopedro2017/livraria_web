/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import model.Usuario;
import util.PersistenceUtil;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author John Peter
 */
public class UsuarioDAO implements Serializable, CrudDAO<Usuario> {

    public static UsuarioDAO usuarioDAO;

    public static UsuarioDAO getInstance() {
        if (usuarioDAO == null) {
            usuarioDAO = new UsuarioDAO();
        }
        return usuarioDAO;
    }

    public Usuario buscar(String nome) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Usuario> query = em.createQuery("select u from Usuario u where u.nomeUsuario =:nome ", Usuario.class);
        query.setParameter("nome", nome);

        Usuario usuario = query.getSingleResult();
        if (usuario != null) {
            return usuario;
        }
        return null;
    }

    @Override
    public Usuario buscarId(int id) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Usuario> query = em.createQuery("select u from Usuario u where u.id =:id ", Usuario.class);
        query.setParameter("id", id);

        Usuario usuario = query.getSingleResult();
        if (usuario != null) {
            return usuario;
        }
        return null;
    }

    @Override
    public List<Usuario> buscarTodas() {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Usuario> query = em.createQuery("from Usuario As u", Usuario.class);
        return query.getResultList();
    }

    @Override
    public List<Usuario> buscarInstancia() {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Usuario> query = em.createQuery("select distinct u from Usuario u group by u.nomeUsuario", Usuario.class);
        return query.getResultList();
    }

    @Override
    public void remover(Usuario usuario) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        if (!em.contains(usuario)) {
            usuario = em.merge(usuario);
        }
        em.remove(usuario);
        em.getTransaction().commit();
    }

    @Override
    public Usuario persistir(Usuario usuario) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        try {
            usuario = em.merge(usuario);
            em.getTransaction().commit();
            System.out.println("Registro Usuario gravado com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }

    @Override
    public void removeAll() {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery(" delete from Usuario ");
        query.executeUpdate();
        em.getTransaction().commit();
    }

    public Usuario autenticacao(String nome, String senha) {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("select u from Usuario u where u.nomeUsuario =:nome AND u.senha=:senha ");
        query.setParameter("nome", nome);
        query.setParameter("senha", senha);

        List<Usuario> usuario = query.getResultList();
        if (usuario != null && usuario.size() > 0) {
            return usuario.get(0);
        }
        return null;
    }

}

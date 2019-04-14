/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import model.Livro;
import dao.LivroDAO;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author alunoces
 */
@ManagedBean
@ViewScoped
public class livroBean extends crudBean<Livro, LivroDAO>{
    
    private LivroDAO livroDAO;

    @Override
    public LivroDAO getDao() {
        if (livroDAO == null) {
            livroDAO = new LivroDAO();
        }
        return livroDAO;
    }

    @Override
    public Livro novo() {
        return new Livro();
    }    
}

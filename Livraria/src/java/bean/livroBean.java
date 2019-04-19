/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import model.Livro;
import dao.LivroDAO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author alunoces
 */
@ManagedBean
@ViewScoped
public class livroBean extends crudBean<Livro, LivroDAO>{
    
    private LivroDAO livroDAO;
    public List<SelectItem> itens;

    public List<SelectItem> getItens() {
        List<SelectItem> list = new ArrayList<SelectItem>();
        List<Livro> livros = livroDAO.buscarTodas();

        for (Livro livro : livros) {
            list.add(new SelectItem(livro, livro.getTitulo())); //nome livro ira aparecer no combo
        }
        return list;
    }
    
    public Livro buscarId(int id) {
        return new LivroDAO().buscarId(id);
    }

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

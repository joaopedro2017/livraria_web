/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.AutorDAO;
import dao.LivroDAO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import model.Autor;
import model.Livro;

/**
 *
 * @author alunoces
 */
@ManagedBean
@ViewScoped
public class livroBean extends crudBean<Livro, LivroDAO> {

    private LivroDAO livroDAO;
    public List<SelectItem> itens; //Combobox 1 x n    
    private Integer autorId; //Relacionamento n x n

    //Metodo para aparecer no combo box
    public List<SelectItem> getItens() {
        List<SelectItem> list = new ArrayList<SelectItem>();
        List<Livro> livros = null;
        livros = livroDAO.buscarTodas();

        for (Livro livro : livros) {
            list.add(new SelectItem(livro, livro.getTitulo())); //nome livro ira aparecer no combo
        }
        return list;
    }

    //Relacionamento n x n
    public List<Autor> getAutores() {
        return new AutorDAO().buscarTodas();
    }

    public Integer getAutorId() {
        return autorId;
    }

    public void setAutorId(Integer autorId) {
        this.autorId = autorId;
    }

    public void adicionarAutor() {
        Autor autor = new AutorDAO().buscarId(this.autorId);
        getEntidade().getAutorList().add(autor);
    }
    
    public void removerAutor(Autor autor) {        
        getEntidade().getAutorList().remove(autor);
    }

    public List<Autor> getAutoresDoLivro() {
        return getEntidade().getAutorList();
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

package bean;

import dao.AutorDAO;
import dao.LivroDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import model.Autor;
import model.Livro;

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
        if (this.autorId != null) {
            for (Autor a : getEntidade().getAutorList()) {
                if (Objects.equals(a.getId(), this.autorId)) {
                    return;
                }
            }
            Autor autor = new AutorDAO().buscarId(this.autorId);
            getEntidade().getAutorList().add(autor);
        }
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

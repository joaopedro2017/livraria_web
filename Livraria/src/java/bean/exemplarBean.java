package bean;

import dao.ExemplarDAO;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import model.Exemplar;
import model.Livro;

@ManagedBean
@ViewScoped
public class exemplarBean extends crudBean<Exemplar, ExemplarDAO> {

    private ExemplarDAO exemplarDAO;

    private Integer livroId = null;
    @ManagedProperty(value = "#{livroBean}")
    private livroBean livro = new livroBean();

    public List<SelectItem> itens;

    public Exemplar buscarId(int id) {
        return new ExemplarDAO().buscarId(id);
    }

    public List<Exemplar> getExemplaresCirculantes() {
        return getDao().exemplarCirculante();
    }

    //get e set aux
    public Integer getLivroId() {
        return livroId;
    }

    public void setLivroId(Integer livroId) {
        this.livroId = livroId;
    }

    public livroBean getLivro() {
        return livro;
    }

    public void setLivro(livroBean livro) {
        this.livro = livro;
    }

    //Metodo da classe
    public void gravar(ActionEvent actionEvent) {
        if (livroId != null) {
            Livro liv = livro.buscarId(livroId);
            getEntidade().setLivroid(liv);
            record(actionEvent);
            livroId = null;
        }
    }

    @Override
    public ExemplarDAO getDao() {
        if (exemplarDAO == null) {
            exemplarDAO = new ExemplarDAO();
        }
        return exemplarDAO;
    }

    @Override
    public Exemplar novo() {
        return new Exemplar();
    }

}

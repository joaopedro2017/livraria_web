package bean;

import dao.LivroDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import model.Assunto;
import model.Autor;
import model.Editora;
import model.Livro;
import relatorio.Relatorio;

@ManagedBean
@ViewScoped
public class livroBean extends crudBean<Livro, LivroDAO> {

    private LivroDAO livroDAO;
    private Integer assuntoId;
    private Integer autorId;
    private Integer editoraId;

    //ManagedProperty's
    @ManagedProperty(value = "#{autorBean}")
    private autorBean autor = new autorBean();
    @ManagedProperty(value = "#{assuntoBean}")
    private assuntoBean assunto = new assuntoBean();
    @ManagedProperty(value = "#{editoraBean}")
    private editoraBean editora = new editoraBean();

    //get e set dos bean's
    public autorBean getAutor() {
        return autor;
    }

    public void setAutor(autorBean autor) {
        this.autor = autor;
    }

    public assuntoBean getAssunto() {
        return assunto;
    }

    public void setAssunto(assuntoBean assunto) {
        this.assunto = assunto;
    }

    public editoraBean getEditora() {
        return editora;
    }

    public void setEditora(editoraBean editora) {
        this.editora = editora;
    }

    //Métodos do livro
    public void adicionarAutor() {
        //autorId = null;
        try {
            for (Autor a : getEntidade().getAutorList()) {
                if (Objects.equals(a.getId(), this.autorId)) {
                    return;
                }
            }
            Autor aut = autor.getDao().buscarId(autorId);
            getEntidade().getAutorList().add(aut);
        } catch (Exception ex) {
            adicionarMensagem("Selecione um autor", FacesMessage.SEVERITY_WARN);
        }
    }

    public void removerAutor(Autor autor) {
        getEntidade().getAutorList().remove(autor);
    }

    public List<Autor> getAutoresDoLivro() {
        return getEntidade().getAutorList();
    }

    //get e set de IdAux
    public Integer getAssuntoId() {
        return assuntoId;
    }

    public void setAssuntoId(Integer assuntoId) {
        this.assuntoId = assuntoId;
    }

    public Integer getAutorId() {
        return autorId;
    }

    public void setAutorId(Integer autorId) {
        this.autorId = autorId;
    }

    public Integer getEditoraId() {
        return editoraId;
    }

    public void setEditoraId(Integer editoraId) {
        this.editoraId = editoraId;
    }

    //Demais métodos
    public Livro buscarId(int id) {
        assuntoId = null;
        autorId = null;
        editoraId = null;
        return new LivroDAO().buscarId(id);
    }

    public List<Object[]> getExemplares() {
        return getDao().qntLivroExemplares();
    }

    public void gravar(ActionEvent actionEvent) {
        if (assuntoId != null && editoraId != null) {
            System.out.println("Assunto: " + assuntoId + "Editora: " + editoraId);
            Assunto ass = assunto.buscarId(assuntoId);
            getEntidade().setAssuntoid(ass);
            Editora edit = editora.buscarId(editoraId);
            getEntidade().setEditoraid(edit);
            record(actionEvent);
            assuntoId = null;
            editoraId = null;
        }
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

    //Relatorio
    public void gerarRelatorioAction() {
        try {
            Relatorio relatorio = new Relatorio();
            relatorio.setCaminho("exemplar");
            relatorio.getRelatorio();

        } catch (SQLException ex) {
            Logger.getLogger(livroBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

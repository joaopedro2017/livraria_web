package bean;

import dao.EmprestimoDAO;
import dao.ReservaDAO;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import model.Emprestimo;
import model.Reserva;

@ManagedBean
@ViewScoped
public class reservaBean extends crudBean<Reserva, ReservaDAO> {

    private Integer usuarioId = null;
    private Integer exemplarId = null;
    private ReservaDAO reservaDAO;

    @ManagedProperty(value = "#{emprestimoBean}")
    private emprestimoBean bean;
    @ManagedProperty(value = "#{exemplarBean}")
    private exemplarBean exemplar;
    @ManagedProperty(value = "#{usuarioBean}")
    private usuarioBean usuario;

    public void cancelar(ActionEvent actionEvent) {
        if (getEntidade().getId() != null) {
            getEntidade().setCancelar("Usuário");
            record(actionEvent);
        }
    }

    public void realizarEmprestimo(ActionEvent actionEvent) {
        if (getEntidade().getId() != null) {
            bean.getEntidade().setUsuarioid(getEntidade().getUsuarioid());
            bean.getEntidade().setExemplarid(getEntidade().getExemplarid());
            bean.calcularData(getEntidade().getExemplarid(), getEntidade().getUsuarioid());

            Emprestimo e = new EmprestimoDAO().persistir(bean.getEntidade());

            getEntidade().setEmprestimoid(e);
            record(actionEvent);
        }
    }

    //get e set bean's
    public emprestimoBean getBean() {
        return bean;
    }

    public void setBean(emprestimoBean bean) {
        this.bean = bean;
    }

    public exemplarBean getExemplar() {
        return exemplar;
    }

    public void setExemplar(exemplarBean exemplar) {
        this.exemplar = exemplar;
    }

    public usuarioBean getUsuario() {
        return usuario;
    }

    public void setUsuario(usuarioBean usuario) {
        this.usuario = usuario;
    }

    //get e set aux
    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getExemplarId() {
        return exemplarId;
    }

    public void setExemplarId(Integer exemplarId) {
        this.exemplarId = exemplarId;
    }

    public void gravar(ActionEvent actionEvent) {
        getEntidade().setUsuarioid(usuario.buscarId(usuarioId));
        getEntidade().setExemplarid(exemplar.buscarId(exemplarId));
        record(actionEvent);
        usuarioId = null;
        exemplarId = null;
    }

    @Override
    public ReservaDAO getDao() {
        if (reservaDAO == null) {
            reservaDAO = new ReservaDAO();
        }
        return reservaDAO;
    }

    @Override
    public Reserva novo() {
        return new Reserva();
    }

}

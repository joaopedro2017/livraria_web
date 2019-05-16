package bean;

import dao.EmprestimoDAO;
import dao.ReservaDAO;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import model.Emprestimo;
import model.Reserva;

@ManagedBean
@ViewScoped
public class reservaBean extends crudBean<Reserva, ReservaDAO> {

    private ReservaDAO reservaDAO;
    @ManagedProperty(value = "#{emprestimoBean}")
    private emprestimoBean bean;

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
            bean.getEntidade().setDataEmprestimo(new Date());
            bean.calcularData(getEntidade().getExemplarid(), getEntidade().getUsuarioid());

            Emprestimo e = new EmprestimoDAO().persistir(bean.getEntidade());

            getEntidade().setEmprestimoid(e);
            record(actionEvent);
        }
    }

    public emprestimoBean getBean() {
        return bean;
    }

    public void setBean(emprestimoBean bean) {
        this.bean = bean;
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
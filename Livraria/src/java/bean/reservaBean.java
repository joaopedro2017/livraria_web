package bean;

import dao.EmprestimoDAO;
import dao.ReservaDAO;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import model.Emprestimo;
import model.Reserva;
import util.SessionUtil;

@ManagedBean
@ViewScoped
public class reservaBean extends crudBean<Reserva, ReservaDAO> {

    private Integer usuarioId = null;
    private Integer exemplarId = null;
    private ReservaDAO reservaDAO;

    private String msg = " ";
    private boolean debito = true;

    @ManagedProperty(value = "#{emprestimoBean}")
    private emprestimoBean bean;
    @ManagedProperty(value = "#{exemplarBean}")
    private exemplarBean exemplar;
    @ManagedProperty(value = "#{usuarioBean}")
    private usuarioBean usuario;

    public void cancelar(ActionEvent actionEvent) {
        if (getEntidade().getId() != null) {
            getEntidade().setCancelar("Usuário");
            getDao().persistir(getEntidade());
            adicionarMensagem("Cancelada com sucesso!", FacesMessage.SEVERITY_INFO);
        }
    }

    public void verificarDebito() {
        Long valor = bean.getDao().verificarDebito(usuarioId);
        if (valor > 0) {
            setMsg("Usuário está em débito!");
            setDebito(true);

        } else {
            setMsg("Usuário OK");
            setDebito(false);
        }
    }

    public void dataReserva() {
        Date data = bean.dataEntregaExemplar(exemplarId);
        Date hoje = new Date();
        if (data == null || data.before(hoje)) {
            getEntidade().setDataReserva(hoje);
        } else {
            getEntidade().setDataReserva(data);
        }
    }

    public void cancelarReservaSistema() {
        if ("Administrador".equals(SessionUtil.getUserTipo()) || "Funcionário".equals(SessionUtil.getUserTipo())) {
            List<Reserva> reservas = getDao().cancelamentoAutomatico();
            LocalDate data2 = LocalDate.now();

            if (reservas.size() > 0) {
                for (Reserva reserva : reservas) {
                    LocalDate data1 = reserva.getDataReserva().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    long intervalo = ChronoUnit.DAYS.between(data1, data2);

                    if (intervalo > 3) {
                        Reserva aux = reserva;
                        aux.setCancelar("Sistema");
                        getDao().persistir(aux);
                        adicionarMensagem("Reserva " + aux.getId() + " cancelada!", FacesMessage.SEVERITY_INFO);
                    }
                }
            }
        }
    }

    public boolean renderizar(Reserva reserva) {
        return reserva.getCancelar() == null && reserva.getEmprestimoid() == null;
    }

    public void realizarEmprestimo(ActionEvent actionEvent) {
        if (getEntidade().getId() != null) {
            bean.getEntidade().setUsuarioid(getEntidade().getUsuarioid());
            bean.getEntidade().setExemplarid(getEntidade().getExemplarid());
            bean.calcularData(getEntidade().getExemplarid(), getEntidade().getUsuarioid());

            Emprestimo e = new EmprestimoDAO().persistir(bean.getEntidade());

            getEntidade().setEmprestimoid(e);
            getDao().persistir(getEntidade());
            adicionarMensagem("Empréstimo feito com Sucesso!", FacesMessage.SEVERITY_INFO);
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isDebito() {
        return debito;
    }

    public void setDebito(boolean debito) {
        this.debito = debito;
    }

    public void gravar(ActionEvent actionEvent) {
        getEntidade().setUsuarioid(usuario.buscarId(usuarioId));
        getEntidade().setExemplarid(exemplar.buscarId(exemplarId));
        record(actionEvent);
        usuarioId = null;
        exemplarId = null;
        setDebito(true);
        setMsg(" ");
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

package bean;

import dao.EmprestimoDAO;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import model.Emprestimo;
import model.Exemplar;
import model.Usuario;
import org.primefaces.event.FlowEvent;

@ViewScoped
@ManagedBean
public class emprestimoBean extends crudBean<Emprestimo, EmprestimoDAO> {

    private EmprestimoDAO emprestimoDAO;
    private String msg = "";
    private boolean debito = false;

    private Integer usuarioId = null;
    private Integer exemplarId = null;
    @ManagedProperty(value = "#{usuarioBean}")
    private usuarioBean usuario;
    @ManagedProperty(value = "#{exemplarBean}")
    private exemplarBean exemplar;

    public void emprestimoAtraso() throws SQLException {
        Long atraso = getDao().verificarDebito(usuarioId);

        if (atraso > 0) {
            setDebito(true);
            setMsg("Possui " + atraso + " empréstimo em atraso");
            return;
        }

        Date bloqueio = getDao().diasBloqueado(usuarioId);
        if (bloqueio != null) {
            LocalDate data1 = bloqueio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate data2 = LocalDate.now();
            long intervalo = ChronoUnit.DAYS.between(data1, data2);

            if (intervalo < 30) {
                setDebito(true);
                setMsg("Usuário bloqueado(a) há " + intervalo + " dias, máximo 30 dias!");
                return;
            }
        }

        getEntidade().setUsuarioid(usuario.buscarId(usuarioId));
        String tipo = getEntidade().getUsuarioid().getTipo();
        Long aberto = getDao().verificarEmAberto(usuarioId);
        if (("Professor".equals(tipo) && aberto >= 5)) {
            setDebito(true);
            setMsg("O usuário(a) atingiu limite de empréstimo");
            return;
        }

        if ((!"Professor".equals(tipo) && aberto >= 3)) {
            setDebito(true);
            setMsg("O usuário(a) atingiu limite de empréstimo");
            return;
        }

        setDebito(false);
        setMsg("Clique em próximo!");
    }

    public Date dataEntregaExemplar(int id) {
        return getDao().dataEntrega(id);
    }

    public void verificarExemplar() {
        Long disponivel = getDao().exemplarDisponivel(exemplarId);
        if (disponivel != 0) {
            System.out.println("Verificar Quantidade se pode " + disponivel);
            setDebito(true);
        } else {
            getEntidade().setExemplarid(exemplar.buscarId(exemplarId));
            calcularData(getEntidade().getExemplarid(), getEntidade().getUsuarioid());
            setDebito(false);
        }
    }

    public void gravar(ActionEvent actionEvent) {
        record(actionEvent);
        usuarioId = null;
        exemplarId = null;
    }

    public void calcularData(Exemplar ex, Usuario us) {
        getEntidade().setDataEmprestimo(new Date());
        boolean opcao = ex.getCircular();
        String tipo = us.getTipo();
        Calendar c = new GregorianCalendar();
        c.setTime(new Date());

        if (opcao) {
            if ("Professor".equals(tipo)) {
                c.add(Calendar.DATE, 15);
                getEntidade().setDataPrevista(c.getTime());
            } else {
                c.add(Calendar.DATE, 10);
                getEntidade().setDataPrevista(c.getTime());

            }
        } else {
            proximoDiaUtil(c);
            getEntidade().setDataPrevista(c.getTime());

        }
    }

    private void proximoDiaUtil(Calendar c) {
        switch (c.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.FRIDAY:
                c.add(Calendar.DATE, 3);
                break;
            case Calendar.SATURDAY:
                c.add(Calendar.DATE, 2);
                break;
            default:
                c.add(Calendar.DATE, 1);
                break;
        }
    }

    public String proximaAba(FlowEvent event) {
        if (debito) {
            setMsg("");
            return event.getOldStep();
        } else {
            return event.getNewStep();
        }
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

    public void devolver(ActionEvent actionEvent) {
        if (getEntidade().getId() != null) {
            getEntidade().setDataDevolucao(new Date());
            record(actionEvent);
        }
    }

    //aux
    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public usuarioBean getUsuario() {
        return usuario;
    }

    public void setUsuario(usuarioBean usuario) {
        this.usuario = usuario;
    }

    public Integer getExemplarId() {
        return exemplarId;
    }

    public void setExemplarId(Integer exemplarId) {
        this.exemplarId = exemplarId;
    }

    public exemplarBean getExemplar() {
        return exemplar;
    }

    public void setExemplar(exemplarBean exemplar) {
        this.exemplar = exemplar;
    }

    @Override
    public EmprestimoDAO getDao() {
        if (emprestimoDAO == null) {
            emprestimoDAO = new EmprestimoDAO();
        }
        return emprestimoDAO;
    }

    @Override
    public Emprestimo novo() {
        usuarioId = null;
        exemplarId = null;
        return new Emprestimo();
    }

}

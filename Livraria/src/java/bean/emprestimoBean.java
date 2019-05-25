package bean;

import dao.EmprestimoDAO;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import model.Emprestimo;
import model.Exemplar;
import model.Livro;
import model.Usuario;
import org.primefaces.event.FlowEvent;
import relatorio.Relatorio;

@ViewScoped
@ManagedBean
public class emprestimoBean extends crudBean<Emprestimo, EmprestimoDAO> {

    private EmprestimoDAO emprestimoDAO;
    private boolean debito = false;

    private Integer usuarioId = null;
    private Integer exemplarId = null;
    @ManagedProperty(value = "#{usuarioBean}")
    private usuarioBean usuario;
    @ManagedProperty(value = "#{exemplarBean}")
    private exemplarBean exemplar;

    public void emprestimoAtraso() {
        getEntidade().setUsuarioid(usuario.buscarId(usuarioId));
        Long atraso = getDao().verificarDebito(usuarioId);
        Long aberto = getDao().verificarEmAberto(usuarioId);
        String tipo = getEntidade().getUsuarioid().getTipo();

        System.out.println("id: " + usuarioId + " Atraso: " + atraso + " aberto: " + aberto + " tipo: " + tipo + "\n");

        if (atraso > 0) {
            setDebito(true);
        } else if (("Professor".equals(tipo) && aberto >= 5)) {
            setDebito(true);
        } else if ((!"Professor".equals(tipo) && aberto >= 3)) {
            setDebito(true);
        } else {
            setDebito(false);
        }
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
            return event.getOldStep();
        } else {
            return event.getNewStep();
        }
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

    public List<Livro> getEmprestados() {
        return getDao().livrosEmprestados();
    }
    
    public List<Livro> getAtrasados() {
        return getDao().livrosAtrasados();
    }
    
    public void gerarRelatorioAction() {
        try {
            Relatorio relatorio = new Relatorio();
            relatorio.setCaminho("emprestado");
            relatorio.getRelatorio();
        } catch (SQLException ex) {
            Logger.getLogger(usuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gerarRelatorioAtrasadoAction() {
        try {
            Relatorio relatorio = new Relatorio();
            relatorio.setCaminho("atrasado");
            relatorio.getRelatorio();
        } catch (SQLException ex) {
            Logger.getLogger(usuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
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

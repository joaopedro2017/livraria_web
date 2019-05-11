package bean;

import dao.EmprestimoDAO;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import model.Emprestimo;
import org.primefaces.event.FlowEvent;

@ManagedBean
@ViewScoped
public class emprestimoBean extends crudBean<Emprestimo, EmprestimoDAO> {

    private EmprestimoDAO emprestimoDAO;
    private boolean debito = false;

    public void emprestimoAtraso() {
        int id = getEntidade().getUsuarioid().getId();
        Long atraso = getDao().verificarDebito(id);
        Long aberto = getDao().verificarEmAberto(id);
        String tipo = getEntidade().getUsuarioid().getTipo();

        System.out.println("id: " + id + " Atraso: " + atraso + " aberto: " + aberto + " tipo: " + tipo + "\n");

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
        int idExemplar = getEntidade().getExemplarid().getId();
        Long disponivel = getDao().exemplarDisponivel(idExemplar);
        if (disponivel != 0) {
            System.out.println("Verificar Quantidade se pode " + disponivel);
            setDebito(true);
        } else {
            setDebito(false);
        }
    }

    public void gravar(ActionEvent actionEvent) {
        getEntidade().setDataEmprestimo(new Date());
        if (getEntidade().getExemplarid().getCircular()) {
            if ("Professor".equals(getEntidade().getUsuarioid().getTipo())) {
                calcularData(1);
            } else {
                calcularData(0);
            }
        } else {
            calcularData(2);
        }
        record(actionEvent);
    }

    public void calcularData(int opcao) {
        Calendar c = new GregorianCalendar();
        c.setTime(new Date());

        switch (opcao) {
            case 0:
                c.add(Calendar.DATE, 10);
                getEntidade().setDataPrevista(c.getTime());
                break;
            case 1:
                c.add(Calendar.DATE, 15);
                getEntidade().setDataPrevista(c.getTime());
                break;
            case 2:
                proximoDiaUtil(c);
                getEntidade().setDataPrevista(c.getTime());
                break;
            default:
                break;
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
    
    public void devolver(ActionEvent actionEvent){
        getEntidade().setDataDevolucao(new Date());
        record(actionEvent);
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
        return new Emprestimo();
    }

}

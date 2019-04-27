package bean;

import dao.EmprestimoDAO;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import model.Emprestimo;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author John Peter
 */
@ManagedBean
@ViewScoped
public class emprestimoBean extends crudBean<Emprestimo, EmprestimoDAO> {

    private EmprestimoDAO emprestimoDAO;
    private Integer qnt;
    
    public void emprestimoAtraso(){
        qnt = getDao().verificarDebito(getEntidade().getUsuarioid().getId());
        System.out.println("Quantidade de debitos " + qnt);
        adicionarMensagem("Possui " + qnt + " de débito(s)", FacesMessage.SEVERITY_INFO);       
    }

    public Integer getQnt() {
        return qnt;
    }

    public void setQnt(Integer qnt) {
        this.qnt = qnt;
    }   

    public void calcularDevolucao(ActionEvent actionEvent) {
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
                getEntidade().setDataDevolucao(c.getTime());
                break;
            case 1:
                c.add(Calendar.DATE, 15);
                getEntidade().setDataDevolucao(c.getTime());
                break;
            case 2:
                proximoDiaUtil(c);
                getEntidade().setDataDevolucao(c.getTime());
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
        if(qnt == 0) {            
            return event.getOldStep();
        }
        else {
            return event.getNewStep();
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
        return new Emprestimo();
    }

}

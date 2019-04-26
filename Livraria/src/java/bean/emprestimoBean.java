package bean;

import dao.EmprestimoDAO;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import model.Emprestimo;

/**
 *
 * @author John Peter
 */
@ManagedBean
@ViewScoped
public class emprestimoBean extends crudBean<Emprestimo, EmprestimoDAO>{
    
    private EmprestimoDAO emprestimoDAO;
    
    public void calcularDevolucao(ActionEvent actionEvent){
        getEntidade().setDataEmprestimo(new Date());
        if(!getEntidade().getExemplarid().getCircular()){
            System.out.println("Calcula proximo dia Util");
            getEntidade().setDataDevolucao(new Date());
        }else{
            if("Professor".equals(getEntidade().getUsuarioid().getTipo())){
                System.out.println("Acrescenta mais 15 dias na devolucao");
                getEntidade().setDataDevolucao(new Date());
            }else{
                System.out.println("Para outros tipo adiciona 10 dias ");
                getEntidade().setDataDevolucao(new Date());
            }
        }
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

package bean;

import dao.EmprestimoDAO;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.Emprestimo;

/**
 *
 * @author John Peter
 */
@ManagedBean
@ViewScoped
public class emprestimoBean extends crudBean<Emprestimo, EmprestimoDAO>{
    
    private EmprestimoDAO emprestimoDAO;

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

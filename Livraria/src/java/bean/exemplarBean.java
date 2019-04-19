package bean;

import dao.ExemplarDAO;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.Exemplar;

/**
 *
 * @author John Peter
 */
@ManagedBean
@ViewScoped
public class exemplarBean extends crudBean<Exemplar, ExemplarDAO> {

    private ExemplarDAO exemplarDAO;
    
    @Override
    public ExemplarDAO getDao() {
        if (exemplarDAO == null) {
            exemplarDAO = new ExemplarDAO();
        }
        return exemplarDAO;
    }

    @Override
    public Exemplar novo() {
        return new Exemplar();
    }

}

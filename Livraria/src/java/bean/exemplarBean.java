package bean;

import dao.ExemplarDAO;
import model.Exemplar;

/**
 *
 * @author John Peter
 */
public class exemplarBean extends crudBean<Exemplar, ExemplarDAO>{
    
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

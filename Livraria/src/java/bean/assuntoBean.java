package bean;

import dao.AssuntoDAO;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;
import model.Assunto;

@ManagedBean
@ViewScoped
public class assuntoBean extends crudBean<Assunto, AssuntoDAO>{
    
    private AssuntoDAO assuntoDAO;
    
    public Assunto buscarId(int id) {
        return new AssuntoDAO().buscarId(id);
    }
    
    @Override
    public AssuntoDAO getDao() {
        if (assuntoDAO == null) {
            assuntoDAO = new AssuntoDAO();
        }
        return assuntoDAO;
    }

    @Override
    public Assunto novo() {
        return new Assunto();
    }    
}
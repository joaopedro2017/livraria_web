package bean;

import dao.ExemplarDAO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import model.Exemplar;

/**
 *
 * @author John Peter
 */
@ManagedBean
@ViewScoped
public class exemplarBean extends crudBean<Exemplar, ExemplarDAO> {

    private ExemplarDAO exemplarDAO;
    
    public List<SelectItem> itens;

    public List<SelectItem> getItens() {
        List<SelectItem> list = new ArrayList<SelectItem>();
        List<Exemplar> exemplares = exemplarDAO.buscarTodas();

        for (Exemplar exemplar : exemplares) {
            list.add(new SelectItem(exemplar, exemplar.getLivroid().getTitulo())); //nome Editora ira aparecer no combo
        }
        return list;
    }
    
     public Exemplar buscarId(int id) {
        return new ExemplarDAO().buscarId(id);
    }
    
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

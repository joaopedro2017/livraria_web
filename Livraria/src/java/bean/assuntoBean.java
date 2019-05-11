package bean;

import dao.AssuntoDAO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import model.Assunto;

@ManagedBean
@ViewScoped
public class assuntoBean extends crudBean<Assunto, AssuntoDAO>{
    
    private AssuntoDAO assuntoDAO;    
    public List<SelectItem> itens;

    public List<SelectItem> getItens() {
        List<SelectItem> list = new ArrayList<SelectItem>();
        List<Assunto> assuntos = null;
        assuntos = assuntoDAO.buscarTodas();

        for (Assunto assunto : assuntos) {
            list.add(new SelectItem(assunto, assunto.getNomeAssunto())); //nome assunto ira aparecer no combo
        }
        return list;
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
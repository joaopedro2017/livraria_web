package bean;

import model.Autor;
import dao.AutorDAO;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class autorBean extends crudBean<Autor, AutorDAO> {

    private AutorDAO autorDAO;

    @Override
    public AutorDAO getDao() {
        if (autorDAO == null) {
            autorDAO = new AutorDAO();
        }
        return autorDAO;
    }

    @Override
    public Autor novo() {
        return new Autor();
    }
}

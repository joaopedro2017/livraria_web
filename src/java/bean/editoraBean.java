package bean;

import model.Editora;
import dao.EditoraDAO;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class editoraBean extends crudBean<Editora, EditoraDAO> {

    private EditoraDAO editoraDAO;

    public Editora buscarId(int id) {
        return new EditoraDAO().buscarId(id);
    }

    @Override
    public EditoraDAO getDao() {
        if (editoraDAO == null) {
            editoraDAO = new EditoraDAO();
        }
        return editoraDAO;
    }

    @Override
    public Editora novo() {
        return new Editora();
    }

}

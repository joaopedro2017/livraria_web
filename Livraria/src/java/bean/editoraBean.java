package bean;

import model.Editora;
import dao.EditoraDAO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

@ManagedBean
@ViewScoped
public class editoraBean extends crudBean<Editora, EditoraDAO> {

    private EditoraDAO editoraDAO;
    public List<SelectItem> itens;

    public List<SelectItem> getItens() {
        List<SelectItem> list = new ArrayList<SelectItem>();
        List<Editora> editoras = editoraDAO.buscarTodas();

        for (Editora editora : editoras) {
            list.add(new SelectItem(editora, editora.getNomeEditora())); //nome Editora ira aparecer no combo
        }
        return list;
    }

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

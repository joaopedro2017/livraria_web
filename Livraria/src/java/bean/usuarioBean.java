package bean;

import model.Usuario;
import dao.UsuarioDAO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

@ManagedBean
@ViewScoped
public class usuarioBean extends crudBean<Usuario, UsuarioDAO> {

    private UsuarioDAO usuarioDAO;
    public List<SelectItem> itens;

    public List<SelectItem> getItens() {
        List<SelectItem> list = new ArrayList<SelectItem>();
        List<Usuario> usuarios = null;
        usuarios = usuarioDAO.buscarTodas();

        for (Usuario usuario : usuarios) {
            list.add(new SelectItem(usuario, usuario.getNomeUsuario())); //nome Usuario ira aparecer no combo
        }
        return list;
    }

    public Usuario buscarId(int id) {
        return new UsuarioDAO().buscarId(id);
    }
    
    @Override
    public UsuarioDAO getDao() {
        if (usuarioDAO == null) {
            usuarioDAO = new UsuarioDAO();
        }
        return usuarioDAO;
    }

    @Override
    public Usuario novo() {
        return new Usuario();
    }
}

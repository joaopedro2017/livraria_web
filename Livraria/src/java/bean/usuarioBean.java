/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.EditoraDAO;
import model.Usuario;
import dao.UsuarioDAO;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import model.Editora;

/**
 *
 * @author John Peter
 */
@ManagedBean
@ViewScoped
public class usuarioBean extends crudBean<Usuario, UsuarioDAO> {

    private UsuarioDAO usuarioDAO;
    public List<SelectItem> itens;

    public List<SelectItem> getItens() {
        List<SelectItem> list = new ArrayList<SelectItem>();
        List<Usuario> editoras = usuarioDAO.buscarTodas();

        for (Usuario usuario : editoras) {
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import model.Usuario;
import dao.UsuarioDAO;
import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author John Peter
 */
@ManagedBean
@ViewScoped
public class usuarioBean extends crudBean<Usuario, UsuarioDAO> {

    private UsuarioDAO usuarioDAO;
    
    public void validar(ActionEvent actionEvent) throws IOException {
        Usuario tipo = usuarioDAO.autenticacao(super.getEntidade());
        try {
            if ("Administrador".equals(tipo.getTipo())) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
        }
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

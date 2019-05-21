package bean;

import model.Usuario;
import dao.UsuarioDAO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import relatorio.Relatorio;

@ManagedBean
@ViewScoped
public class usuarioBean extends crudBean<Usuario, UsuarioDAO> {

    private UsuarioDAO usuarioDAO;

    public Usuario buscarId(int id) {
        return new UsuarioDAO().buscarId(id);
    }

    public void gerarRelatorioAction() {
        try {
            Relatorio relatorio = new Relatorio();
            relatorio.getRelatorio();
        } catch (SQLException ex) {
            Logger.getLogger(usuarioBean.class.getName()).log(Level.SEVERE, null, ex);
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

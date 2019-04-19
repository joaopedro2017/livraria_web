package converter;

import dao.UsuarioDAO;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import model.Usuario;

/**
 *
 * @author John Peter
 */
@FacesConverter(forClass = Usuario.class)
public class UsuarioConverter implements Converter, Serializable {
    
    private static final long serialVersionUID = 1L;

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String codigo) {
        if (codigo != null && !codigo.isEmpty()) {
            int cod = Integer.parseInt(codigo);
            return (Usuario) new UsuarioDAO().buscarId(cod);
        }
        return codigo;
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object objeto) {
        if (objeto != null) {
            Usuario usuario = (Usuario) objeto;
            return usuario.getId() != null && usuario.getId() > 0 ? usuario.getId().toString() : null;
        }
        return null;
    }
    
}

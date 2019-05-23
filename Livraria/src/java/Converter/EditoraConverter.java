package Converter;

import dao.EditoraDAO;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import model.Editora;

@FacesConverter(forClass = Editora.class, value = "editoraConverter")
public class EditoraConverter implements Converter, Serializable {
    
    private static final long serialVersionUID = 1L;

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String codigo) {
        if (codigo != null && !codigo.isEmpty()) {            
            return (Editora) new EditoraDAO().buscarId(Integer.parseInt(codigo));
        }
        return codigo;
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object objeto) {
        if (objeto != null) {
            Editora editora = (Editora) objeto;
            return editora.getId() != null && editora.getId() > 0 ? editora.getId().toString() : null;
        }
        return null;
    }
    
}

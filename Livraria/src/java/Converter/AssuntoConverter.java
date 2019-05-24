package Converter;

import dao.AssuntoDAO;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import model.Assunto;

@FacesConverter(forClass = Assunto.class, value = "assuntoConverter")
public class AssuntoConverter implements Converter, Serializable {
    
    private static final long serialVersionUID = 1L;

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String codigo) {
        if (codigo != null && !codigo.isEmpty()) {            
            return (Assunto) new AssuntoDAO().buscarId(Integer.parseInt(codigo));
        }
        return codigo;
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object objeto) {
        if (objeto != null) {
            Assunto assunto = (Assunto) objeto;
            return assunto.getId() != null && assunto.getId() > 0 ? assunto.getId().toString() : null;
        }
        return null;
    }
    
}
package converter;

import dao.ExemplarDAO;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import model.Exemplar;

/**
 *
 * @author John Peter
 */
@FacesConverter(forClass = Exemplar.class)
@ManagedBean
public class ExemplarConverter implements Converter, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String codigo) {
        if (codigo != null && !codigo.isEmpty()) {
            int cod = Integer.parseInt(codigo);
            return (Exemplar) new ExemplarDAO().buscarId(cod);
        }
        return codigo;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object objeto) {
        if (objeto != null) {
            Exemplar exemplar = (Exemplar) objeto;
            return exemplar.getId() != null && exemplar.getId() > 0 ? exemplar.getId().toString() : null;
        }
        return null;
    }
}

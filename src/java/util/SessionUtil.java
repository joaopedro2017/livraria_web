package util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import model.Usuario;

public class SessionUtil {

    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    public static String getUserName() {
        return ((String) getSession().getAttribute("nome"));
    }
    
    public static String getUserTipo() {
        return ((String) getSession().getAttribute("tipo"));
    }

    public static Usuario getUser() {
        return ((Usuario) getSession().getAttribute("user"));
    }

}

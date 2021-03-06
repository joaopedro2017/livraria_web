package bean;

import dao.UsuarioDAO;
import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import model.Usuario;
import util.SessionUtil;
import static util.SessionUtil.getRequest;

@ManagedBean
@ViewScoped
public class loginBean {

    private String nomeUsuario;
    private String senha;

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void login(ActionEvent actionEvent) throws IOException {
        try {
            Usuario user = new UsuarioDAO().autenticacao(nomeUsuario, senha);
            HttpServletRequest request = SessionUtil.getRequest();
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("nome", user.getNomeUsuario());
            request.getSession().setAttribute("tipo", user.getTipo());
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().redirect("index.xhtml");
        } catch (Exception ex) {
            setNomeUsuario("");
            setSenha("");
            adicionarMensagem();
        }
    }

    public void logoff() throws IOException {
        getRequest().getSession().invalidate();
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().redirect("/Livraria/faces/login.xhtml");
        setNomeUsuario("");
        setSenha("");
    }

    public void adicionarMensagem() {
        String cabecalho = "Acesso negado";
        String mensagem = "Usuário e/ou senha estão incorretos";
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, cabecalho, mensagem);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

    public Usuario getUser() {
        return SessionUtil.getUser();
    }

    public String getTipo() {
        return SessionUtil.getUserTipo();
    }

    public String getPerfil() {
        String perfil = SessionUtil.getUserName();
        if (perfil == null) {
            return null;
        }
        return perfil;
    }
}

package bean;

import dao.EmprestimoDAO;
import dao.LivroDAO;
import dao.ReservaDAO;
import dao.UsuarioDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.Livro;
import model.Usuario;
import relatorio.Relatorio;

@ManagedBean
@ViewScoped
public class relatorioBean {

    //GERAR RELATORIO E TABELA DE EMPRESTIMOS ATRASADOS
    public List<Livro> getAtrasados() {
        EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
        return emprestimoDAO.livrosAtrasados();
    }

    public void gerarRelatorioAtrasadoAction() {
        try {
            Relatorio relatorio = new Relatorio();
            relatorio.setCaminho("atrasado");
            relatorio.getRelatorio();
        } catch (SQLException ex) {
            Logger.getLogger(usuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //GERAR RELATORIO E TABELA DE EMPRESTIMO EM ABERTO
    public List<Livro> getEmprestados() {
        EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
        return emprestimoDAO.livrosEmprestados();
    }

    public void gerarRelatorioEmprestadosAction() {
        try {
            Relatorio relatorio = new Relatorio();
            relatorio.setCaminho("emprestado");
            relatorio.getRelatorio();
        } catch (SQLException ex) {
            Logger.getLogger(usuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //GERAR RELATORIO E TABELA DE NUMERO DE EXEMPLARES
    public List<Object[]> getExemplares() {
        LivroDAO livroDAO = new LivroDAO();
        return livroDAO.qntLivroExemplares();
    }

    public void gerarRelatorioExemplaresAction() {
        try {
            Relatorio relatorio = new Relatorio();
            relatorio.setCaminho("exemplar");
            relatorio.getRelatorio();

        } catch (SQLException ex) {
            Logger.getLogger(livroBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //GERAR RELATORIO E TABELA DE RESERVAS
    public List<Livro> getLivrosReservados() {
        ReservaDAO reservaDAO = new ReservaDAO();
        return reservaDAO.livrosReservados();
    }

    public void gerarRelatorioReservaAction() {
        try {
            Relatorio relatorio = new Relatorio();
            relatorio.setCaminho("reservado");
            relatorio.getRelatorio();
        } catch (SQLException ex) {
            Logger.getLogger(usuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //GERAR RELATORIO E TABELA DE USUARIO
    public List<Usuario> getUsuarios() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        return usuarioDAO.buscarTodas();
    }

    public void gerarRelatorioUsuariosAction() {
        try {
            Relatorio relatorio = new Relatorio();
            relatorio.setCaminho("usuario");
            relatorio.getRelatorio();
        } catch (SQLException ex) {
            Logger.getLogger(usuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

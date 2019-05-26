package bean;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import relatorio.Relatorio;

@ManagedBean
@ViewScoped
public class graficoBean {
    
    public void gerarRelatorioEmprestadoAssuntoAction() {
        try {
            Relatorio relatorio = new Relatorio();
            relatorio.setCaminho("livrosEmprestadosPorAssunto");            
            relatorio.getRelatorio();

        } catch (SQLException ex) {
            Logger.getLogger(graficoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gerarRelatorioEmprestadoMesAction() {
        try {
            Relatorio relatorio = new Relatorio();
            relatorio.setCaminho("livrosEmprestadosPorMes");            
            relatorio.getRelatorio();

        } catch (SQLException ex) {
            Logger.getLogger(graficoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gerarRelatorioReservadoAssuntoAction() {
        try {
            Relatorio relatorio = new Relatorio();
            relatorio.setCaminho("livrosReservadosPorAssunto");            
            relatorio.getRelatorio();

        } catch (SQLException ex) {
            Logger.getLogger(graficoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gerarRelatorioReservadoMesAction() {
        try {
            Relatorio relatorio = new Relatorio();
            relatorio.setCaminho("livrosReservadosPorMes");            
            relatorio.getRelatorio();

        } catch (SQLException ex) {
            Logger.getLogger(graficoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

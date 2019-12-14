package bean;

import dao.RelatorioGerencialDAO;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import relatorio.RelatorioGerencial;

@ManagedBean
@ViewScoped
public class relatorioGerencialBean {

    private Date dataInicial = null;
    private Date dataFinal = null;
    private List<Object[]> relatorio = null;

    public void pesquisar() throws SQLException {

        if (dataInicial != null && dataFinal != null) {
            if (dataFinal.before(dataInicial)) {
                FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Intervalo inválido");
                FacesContext.getCurrentInstance().addMessage(null, fm);
            } else {
                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                String d1 = formato.format(getDataInicial());
                String d2 = formato.format(getDataFinal());
                RelatorioGerencialDAO dao = new RelatorioGerencialDAO();
                setRelatorio(dao.relatorioGerencial(d1, d2));

            }
        }
    }

    public void gerarRelatorioGerencial() {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String d1 = formato.format(getDataInicial());
        String d2 = formato.format(getDataFinal());
        try {
            RelatorioGerencial relatorioGerente = new RelatorioGerencial();
            relatorioGerente.setDataInicio(d1);
            relatorioGerente.setDataFim(d2);
            relatorioGerente.getRelatorio();
        } catch (SQLException ex) {
            Logger.getLogger(usuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public List<Object[]> getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(List<Object[]> relatorio) {
        this.relatorio = relatorio;
    }

}

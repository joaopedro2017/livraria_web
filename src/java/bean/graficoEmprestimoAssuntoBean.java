package bean;

import dao.GraficoDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

@ManagedBean
@ViewScoped
public class graficoEmprestimoAssuntoBean {

    private BarChartModel livrosModel;

    @PostConstruct
    public void init() {
        try {
            createBarModel();
        } catch (SQLException ex) {
            Logger.getLogger(graficoEmprestimoAssuntoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private BarChartModel initBarModel() {
        List<Object[]> lista = new ArrayList<>();
        try {
            lista = new GraficoDAO().emprestimoPorAssunto("Emprestimo");
        } catch (SQLException ex) {
            Logger.getLogger(graficoEmprestimoMesBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.livrosModel = new BarChartModel();

        for (Object[] obj : lista) {
            System.out.println("Mês: " + obj[0] + " Quantidade: " + obj[1] + " Assunto: " + obj[2]);
        }

        String aux = "serie";
        for (Object[] obj : lista) {
            if (this.livrosModel.getSeries().isEmpty()) {
                aux = (String) obj[2];
                ChartSeries serie = new ChartSeries();
                serie.setLabel(aux);
                this.livrosModel.addSeries(serie);
            } else if (!aux.equals(obj[2])) {
                ChartSeries serie2 = new ChartSeries();
                serie2.setLabel((String) obj[2]);
                this.livrosModel.addSeries(serie2);
                aux = (String) obj[2];
            }
        }

        for (ChartSeries serie : this.livrosModel.getSeries()) {
            for (Object[] obj : lista) {
                if (obj[2].equals(serie.getLabel())) {
                    serie.set((String) obj[0], (Number) obj[1]);
                }
            }
        }

        for (ChartSeries serie : this.livrosModel.getSeries()) {
            System.out.println("Serie: " + serie.getLabel() + " Valor: " + serie.getData());

        }

        return this.livrosModel;
    }

    public BarChartModel getLivrosModel() {
        return this.livrosModel;
    }

    private void createBarModel() throws SQLException {
        this.livrosModel = initBarModel();

        this.livrosModel.setTitle("Empréstimo por Assunto");
        this.livrosModel.setLegendPosition("ne");
        this.livrosModel.setBarWidth(120); //Largura da barra
        this.livrosModel.setAnimate(true);

        Axis xAxis = this.livrosModel.getAxis(AxisType.X);
        xAxis.setLabel("Meses");

        Axis yAxis = this.livrosModel.getAxis(AxisType.Y);
        yAxis.setLabel("Livros");
        yAxis.setMin(0);
        yAxis.setMax(20);
    }

}

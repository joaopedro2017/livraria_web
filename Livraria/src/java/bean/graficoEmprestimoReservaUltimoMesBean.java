package bean;

import dao.GraficoDAO;
import java.sql.SQLException;
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
public class graficoEmprestimoReservaUltimoMesBean {

    private BarChartModel livrosModel;

    @PostConstruct
    public void init() {
        try {
            createBarModel();
        } catch (SQLException ex) {
            Logger.getLogger(graficoEmprestimoMesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private BarChartModel initBarModel() {
        Object[] lista = new Object[2];
        try {
            lista = new GraficoDAO().reservaEmprestimoUltimoMes();
        } catch (SQLException ex) {
            Logger.getLogger(graficoEmprestimoMesBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.livrosModel = new BarChartModel();

        ChartSeries serie = new ChartSeries();
        serie.setLabel("Empréstimo");
        serie.set("Livros", (Number) lista[1]);

        ChartSeries serie2 = new ChartSeries();
        serie2.setLabel("Reserva");
        serie2.set("Livros", (Number) lista[0]);

        this.livrosModel.addSeries(serie);
        this.livrosModel.addSeries(serie2);

        return this.livrosModel;
    }

    public BarChartModel getLivrosModel() {
        return this.livrosModel;
    }

    private void createBarModel() throws SQLException {
        this.livrosModel = initBarModel();

        this.livrosModel.setTitle("Empréstimo e Reserva último mês");
        this.livrosModel.setLegendPosition("ne");
        this.livrosModel.setAnimate(true);

        Axis xAxis = this.livrosModel.getAxis(AxisType.X);
        xAxis.setLabel("Mês");

        Axis yAxis = this.livrosModel.getAxis(AxisType.Y);
        yAxis.setLabel("Livros");
        yAxis.setMin(0);
        yAxis.setMax(10);
    }

}

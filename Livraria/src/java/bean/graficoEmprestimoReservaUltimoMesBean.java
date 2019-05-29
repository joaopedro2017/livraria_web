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
            Logger.getLogger(graficoEmpresimoMesBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private BarChartModel initBarModel() {
        Object[] lista = new Object[2];
        try {
            lista = new GraficoDAO().reservaEmprestimoUltimoMes();
        } catch (SQLException ex) {
            Logger.getLogger(graficoEmpresimoMesBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        livrosModel = new BarChartModel();

        ChartSeries serie = new ChartSeries();
        serie.setLabel("Empréstimo");
        serie.set("Livros", (Number) lista[1]);
                
        ChartSeries serie2 = new ChartSeries();
        serie2.setLabel("Reserva");
        serie2.set("Livros", (Number) lista[0]);      
        
        livrosModel.addSeries(serie);
        livrosModel.addSeries(serie2);
     
        return livrosModel;
    }

    public BarChartModel getLivrosModel() {
        return livrosModel;
    }

    private void createBarModel() throws SQLException {
        livrosModel = initBarModel();

        livrosModel.setTitle("Empréstimo e Reserva último mês");
        livrosModel.setLegendPosition("ne");
        livrosModel.setSeriesColors("20B2AA, 8B8B00");        

        Axis xAxis = livrosModel.getAxis(AxisType.X);
        xAxis.setLabel("Mês");

        Axis yAxis = livrosModel.getAxis(AxisType.Y);
        yAxis.setLabel("Livros");
        yAxis.setMin(0);
        yAxis.setMax(10);
    }

}

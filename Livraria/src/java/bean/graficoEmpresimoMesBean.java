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
public class graficoEmpresimoMesBean {

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
        List<Object[]> lista = new ArrayList<>();
        try {
            lista = new GraficoDAO().emprestimoPorMes("Emprestimo");
        } catch (SQLException ex) {
            Logger.getLogger(graficoEmpresimoMesBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        livrosModel = new BarChartModel();

        for (Object[] obj : lista) {
            ChartSeries serie = new ChartSeries();
            serie.setLabel((String) obj[0]);

            serie.set((String) obj[0], (Number) obj[1]);
            livrosModel.addSeries(serie);
        }
        return livrosModel;
    }

    public BarChartModel getLivrosModel() {
        return livrosModel;
    }

    private void createBarModel() throws SQLException {
        livrosModel = initBarModel();

        livrosModel.setTitle("Empréstimo por Mês");
        livrosModel.setLegendPosition("ne");

        Axis xAxis = livrosModel.getAxis(AxisType.X);
        xAxis.setLabel("Meses");

        Axis yAxis = livrosModel.getAxis(AxisType.Y);
        yAxis.setLabel("Livros");
        yAxis.setMin(0);
        yAxis.setMax(10);
    }

}

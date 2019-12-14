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
public class graficoReservaMesBean {

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
        List<Object[]> lista = new ArrayList<>();
        try {
            lista = new GraficoDAO().emprestimoPorMes("Reserva");
        } catch (SQLException ex) {
            Logger.getLogger(graficoEmprestimoMesBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.livrosModel = new BarChartModel();

        for (Object[] obj : lista) {
            ChartSeries serie = new ChartSeries();
            serie.setLabel((String) obj[0]);

            serie.set((String) obj[0], (Number) obj[1]);
            this.livrosModel.addSeries(serie);
        }
        return this.livrosModel;
    }

    public BarChartModel getLivrosModel() {
        return this.livrosModel;
    }

    private void createBarModel() throws SQLException {
        this.livrosModel = initBarModel();

        this.livrosModel.setTitle("Reserva por Mês");
        this.livrosModel.setLegendPosition("ne");
        this.livrosModel.setAnimate(true);

        Axis xAxis = this.livrosModel.getAxis(AxisType.X);
        xAxis.setLabel("Meses");

        Axis yAxis = this.livrosModel.getAxis(AxisType.Y);
        yAxis.setLabel("Livros");
        yAxis.setMin(0);
        yAxis.setMax(20);
    }

}

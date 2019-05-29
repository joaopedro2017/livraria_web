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
            Logger.getLogger(graficoEmpresimoMesBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        livrosModel = new BarChartModel();

        for (Object[] obj : lista) {
            System.out.println("Mês: " + obj[0] + " Quantidade: " + obj[1] + " Assunto: " + obj[2]);
        }

        String aux = "serie";
        for (Object[] obj : lista) {
            if (livrosModel.getSeries().isEmpty()) {
                aux = (String) obj[0];
                ChartSeries serie = new ChartSeries();
                serie.setLabel(aux);
                livrosModel.addSeries(serie);
            } else if (!aux.equals(obj[0])) {
                ChartSeries serie2 = new ChartSeries();
                serie2.setLabel((String) obj[0]);
                livrosModel.addSeries(serie2);
                aux = (String) obj[0];
            }
        }

        for (ChartSeries serie : livrosModel.getSeries()) {
            for (Object[] obj : lista) {
                if (obj[0].equals(serie.getLabel())) {
                    serie.set((String) obj[2], (Number) obj[1]);
                }
            }
        }

        for (ChartSeries serie : livrosModel.getSeries()) {
            System.out.println("Serie: " + serie.getLabel() + " Valor: " + serie.getData());

        }

        return livrosModel;
    }

    public BarChartModel getLivrosModel() {
        return livrosModel;
    }

    private void createBarModel() throws SQLException {
        livrosModel = initBarModel();

        livrosModel.setTitle("Empréstimo por Assunto");
        livrosModel.setLegendPosition("ne");
        livrosModel.setBarWidth(120); //Largura da barra

        Axis xAxis = livrosModel.getAxis(AxisType.X);
        xAxis.setLabel("Meses");

        Axis yAxis = livrosModel.getAxis(AxisType.Y);
        yAxis.setLabel("Livros");
        yAxis.setMin(0);
        yAxis.setMax(10);
    }

}

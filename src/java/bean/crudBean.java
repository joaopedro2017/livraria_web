package bean;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import dao.CrudDAO;
import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.RollbackException;
import javax.servlet.ServletContext;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 *
 * @author John Peter
 * @param <E>
 * @param <D>
 */
public abstract class crudBean<E, D extends CrudDAO> {

    @EJB
    public abstract D getDao();
    @EJB
    public abstract E novo();
    @EJB
    private boolean tabela = true; //Controle de troca de tabela
    @EJB
    private E entidade;
    @EJB
    private List<E> entidades;

    //Métodos dos botões
    @PostConstruct
    public void init(){
        entidade = novo();
        entidades = null;
        entidades = getDao().buscarTodas();
    }  
    
    public void record(ActionEvent actionEvent) {
        try {
            getDao().persistir(entidade);
            entidades = getDao().buscarTodas();
            adicionarMensagem("Salvo(a) com sucesso!", FacesMessage.SEVERITY_INFO);
            entidade = novo();
        } catch (RollbackException ex) {
            adicionarMensagem("Erro ao salvar!" + ex, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void exclude(ActionEvent actionEvent) {
        try {
            getDao().remover(entidade);
            entidades = getDao().buscarTodas();
            adicionarMensagem("Excluído(a) com sucesso!", FacesMessage.SEVERITY_INFO);
            entidade = novo();
        } catch (RollbackException ex) {
            String texto = entidade.getClass().getName().replace("model.", "");
            adicionarMensagem("Não é possível excluir " + texto + ", pois se relaciona com outro elemento.", FacesMessage.SEVERITY_WARN);
        }
    }

    //getters and setters
    public E getEntidade() {
        return entidade;
    }

    public void setEntidade(E entidade) {
        this.entidade = entidade;
    }

    public List getEntidades() {
        return entidades;
    }

    public void setEntidades(List entidades) {
        this.entidades = entidades;
    }
    
    public void editar(E entidade) {
        this.entidade = entidade;        
    }
    
    public boolean isTabela() {
        return tabela;
    }

    public void setTabela(boolean tabela) {
        this.tabela = tabela;
    }

    public void adicionarMensagem(String mensagem, FacesMessage.Severity tipoErro) {
        String cabecalho = entidade.getClass().getName().replace("model.", "");
        FacesMessage fm = new FacesMessage(tipoErro, cabecalho, mensagem);
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

    public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);

        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
            HSSFCell cell = header.getCell(i);

            cell.setCellStyle(cellStyle);
        }
    }

    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    }

}

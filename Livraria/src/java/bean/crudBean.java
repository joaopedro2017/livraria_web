/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import dao.CrudDAO;
import java.io.IOException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
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

    public abstract D getDao();

    public abstract E novo();

    private E entidade = novo();
    private List<E> entidades = getDao().buscarTodas();

    //Métodos dos botões 
    public void record(ActionEvent actionEvent) {
        getDao().persistir(entidade);
        entidades = getDao().buscarTodas();
        adicionarMensagem("Salvo(a) com sucesso!", FacesMessage.SEVERITY_INFO);
        entidade = novo();
    }

    public void exclude(ActionEvent actionEvent) {
        getDao().remover(entidade);
        entidades = getDao().buscarTodas();
        adicionarMensagem("Excluído(a) com sucesso!", FacesMessage.SEVERITY_INFO);
        entidade = novo();
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

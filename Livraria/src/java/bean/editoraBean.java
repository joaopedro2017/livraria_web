/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import model.Editora;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import dao.EditoraDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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
 */
@ManagedBean
@ViewScoped
public class editoraBean {
    
    Editora editora = new Editora();

    List editoras = new ArrayList();

    //construtor
    public editoraBean() {
        editoras = new EditoraDAO().buscarTodas();
        editora = new Editora();
    }

    //Métodos dos botões 
    public void record(ActionEvent actionEvent) {
        new EditoraDAO().persistir(editora);
        editoras = new EditoraDAO().buscarTodas();
        editora = new Editora();
    }

    public void exclude(ActionEvent actionEvent) {
        new EditoraDAO().remover(editora);
        editoras = new EditoraDAO().buscarTodas();
        editora = new Editora();
    }
    
    public Editora buscarId(int id){
        return editora = new EditoraDAO().buscarId(id);
    }

    //getters and setters

    public Editora getEditora() {
        return editora;
    }

    public void setEditora(Editora editora) {
        this.editora = editora;
    }

    public List getEditoras() {
        return editoras;
    }

    public void setEditoras(List editoras) {
        this.editoras = editoras;
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

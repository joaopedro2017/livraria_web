/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import model.Usuario;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import dao.UsuarioDAO;
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
import util.TipoEnum;

/**
 *
 * @author John Peter
 */
@ManagedBean
@ViewScoped
public class usuarioBean {
    
    Usuario usuario = new Usuario();
    List usuarios = new ArrayList();   

    /*
    public TipoEnum[] getStatuses() {
        return TipoEnum.values();
    }*/ //Revisar depois

    //construtor
    public usuarioBean() {
        usuarios = new UsuarioDAO().buscarTodas();
        usuario = new Usuario();
    }

    //Métodos dos botões 
    public void record(ActionEvent actionEvent) {
        new UsuarioDAO().persistir(usuario);
        usuarios = new UsuarioDAO().buscarTodas();
        usuario = new Usuario();
    }

    public void exclude(ActionEvent actionEvent) {
        new UsuarioDAO().remover(usuario);
        usuarios = new UsuarioDAO().buscarTodas();
        usuario = new Usuario();
    }
    
    public String validar(ActionEvent actionEvent){
        System.out.println("Nome do : " + usuario.getNomeUsuario());
        String tipo = new UsuarioDAO().validarUsuario(usuario.getNomeUsuario(), usuario.getSenha());
        System.out.println("Valor do Tipo " + tipo);
        return tipo;
    }

    //getters and setters

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List usuarios) {
        this.usuarios = usuarios;
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

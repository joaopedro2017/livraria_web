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
import dao.CrudDAO;
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
public class editoraBean extends crudBean<Editora, EditoraDAO>{
    
    private EditoraDAO editoraDAO;
    
    
    public Editora buscarId(int id){
        return new EditoraDAO().buscarId(id);
    }

    @Override
    public EditoraDAO getDao() {
        if (editoraDAO == null) {
            editoraDAO = new EditoraDAO();
        }
        return editoraDAO;
    }

    @Override
    public Editora novo() {
        return new Editora();
    }

    
}

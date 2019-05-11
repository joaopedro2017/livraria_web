package bean;

import dao.ReservaDAO;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import model.Reserva;

@ManagedBean
@ViewScoped
public class reservaBean extends crudBean<Reserva, ReservaDAO> {

    private ReservaDAO reservaDAO; 
    
    public void cancelar(ActionEvent actionEvent){
        getEntidade().setCancelar("Usuário");
        record(actionEvent);
    }

    @Override
    public ReservaDAO getDao() {
        if (reservaDAO == null) {
            reservaDAO = new ReservaDAO();
        }
        return reservaDAO;
    }

    @Override
    public Reserva novo() {
        return new Reserva();
    }

}

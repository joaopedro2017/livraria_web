package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "reserva")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reserva.findAll", query = "SELECT r FROM Reserva r"),
    @NamedQuery(name = "Reserva.findById", query = "SELECT r FROM Reserva r WHERE r.id = :id"),
    @NamedQuery(name = "Reserva.findByDataReserva", query = "SELECT r FROM Reserva r WHERE r.dataReserva = :dataReserva"),
    @NamedQuery(name = "Reserva.findByCancelar", query = "SELECT r FROM Reserva r WHERE r.cancelar = :cancelar")})
public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "dataReserva")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataReserva;
    @Column(name = "cancelar")
    private String cancelar;
    @JoinColumn(name = "Emprestimo_id", referencedColumnName = "id")
    @ManyToOne
    private Emprestimo emprestimoid;
    @JoinColumn(name = "Exemplar_id", referencedColumnName = "id")
    @ManyToOne
    private Exemplar exemplarid;
    @JoinColumn(name = "Usuario_id", referencedColumnName = "id")
    @ManyToOne
    private Usuario usuarioid;

    public Reserva() {
    }

    public Reserva(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(Date dataReserva) {
        this.dataReserva = dataReserva;
    }

    public String getCancelar() {
        return cancelar;
    }

    public void setCancelar(String cancelar) {
        this.cancelar = cancelar;
    }

    public Emprestimo getEmprestimoid() {
        return emprestimoid;
    }

    public void setEmprestimoid(Emprestimo emprestimoid) {
        this.emprestimoid = emprestimoid;
    }

    public Exemplar getExemplarid() {
        return exemplarid;
    }

    public void setExemplarid(Exemplar exemplarid) {
        this.exemplarid = exemplarid;
    }

    public Usuario getUsuarioid() {
        return usuarioid;
    }

    public void setUsuarioid(Usuario usuarioid) {
        this.usuarioid = usuarioid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reserva)) {
            return false;
        }
        Reserva other = (Reserva) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Reserva[ id=" + id + " ]";
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author John Peter
 */
@Entity
@Table(name = "assunto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Assunto.findAll", query = "SELECT a FROM Assunto a"),
    @NamedQuery(name = "Assunto.findById", query = "SELECT a FROM Assunto a WHERE a.id = :id"),
    @NamedQuery(name = "Assunto.findByAssunto", query = "SELECT a FROM Assunto a WHERE a.assunto = :assunto"),
    @NamedQuery(name = "Assunto.findByDescricaoAssunto", query = "SELECT a FROM Assunto a WHERE a.descricaoAssunto = :descricaoAssunto"),
    @NamedQuery(name = "Assunto.findByNomeAssunto", query = "SELECT a FROM Assunto a WHERE a.nomeAssunto = :nomeAssunto")})
public class Assunto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "assunto")
    private String assunto;
    @Column(name = "descricaoAssunto")
    private String descricaoAssunto;
    @Column(name = "nomeAssunto")
    private String nomeAssunto;
    @OneToMany(mappedBy = "assuntoid")
    private List<Livro> livroList;

    public Assunto() {
        livroList = new ArrayList<Livro>();
    }

    public Assunto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getDescricaoAssunto() {
        return descricaoAssunto;
    }

    public void setDescricaoAssunto(String descricaoAssunto) {
        this.descricaoAssunto = descricaoAssunto;
    }

    public String getNomeAssunto() {
        return nomeAssunto;
    }

    public void setNomeAssunto(String nomeAssunto) {
        this.nomeAssunto = nomeAssunto;
    }

    @XmlTransient
    public List<Livro> getLivroList() {
        return livroList;
    }

    public void setLivroList(List<Livro> livroList) {
        this.livroList = livroList;
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
        if (!(object instanceof Assunto)) {
            return false;
        }
        Assunto other = (Assunto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Assunto[ id=" + id + " ]";
    }
    
}

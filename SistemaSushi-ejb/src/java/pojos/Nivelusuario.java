/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Yisus
 */
@Entity
@Table(name = "NIVELUSUARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Nivelusuario.findAll", query = "SELECT n FROM Nivelusuario n")
    , @NamedQuery(name = "Nivelusuario.findByIdnivelusuario", query = "SELECT n FROM Nivelusuario n WHERE n.idnivelusuario = :idnivelusuario")
    , @NamedQuery(name = "Nivelusuario.findByNombrenivelusuario", query = "SELECT n FROM Nivelusuario n WHERE n.nombrenivelusuario = :nombrenivelusuario")
    , @NamedQuery(name = "Nivelusuario.findByDescripcionnivelusuario", query = "SELECT n FROM Nivelusuario n WHERE n.descripcionnivelusuario = :descripcionnivelusuario")})
public class Nivelusuario implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDNIVELUSUARIO")
    private BigDecimal idnivelusuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "NOMBRENIVELUSUARIO")
    private String nombrenivelusuario;
    @Size(max = 45)
    @Column(name = "DESCRIPCIONNIVELUSUARIO")
    private String descripcionnivelusuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nivelusuarioIdnivelusuario")
    private Collection<Usuario> usuarioCollection;

    public Nivelusuario() {
    }

    public Nivelusuario(BigDecimal idnivelusuario) {
        this.idnivelusuario = idnivelusuario;
    }

    public Nivelusuario(BigDecimal idnivelusuario, String nombrenivelusuario) {
        this.idnivelusuario = idnivelusuario;
        this.nombrenivelusuario = nombrenivelusuario;
    }

    public BigDecimal getIdnivelusuario() {
        return idnivelusuario;
    }

    public void setIdnivelusuario(BigDecimal idnivelusuario) {
        this.idnivelusuario = idnivelusuario;
    }

    public String getNombrenivelusuario() {
        return nombrenivelusuario;
    }

    public void setNombrenivelusuario(String nombrenivelusuario) {
        this.nombrenivelusuario = nombrenivelusuario;
    }

    public String getDescripcionnivelusuario() {
        return descripcionnivelusuario;
    }

    public void setDescripcionnivelusuario(String descripcionnivelusuario) {
        this.descripcionnivelusuario = descripcionnivelusuario;
    }

    @XmlTransient
    public Collection<Usuario> getUsuarioCollection() {
        return usuarioCollection;
    }

    public void setUsuarioCollection(Collection<Usuario> usuarioCollection) {
        this.usuarioCollection = usuarioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idnivelusuario != null ? idnivelusuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Nivelusuario)) {
            return false;
        }
        Nivelusuario other = (Nivelusuario) object;
        if ((this.idnivelusuario == null && other.idnivelusuario != null) || (this.idnivelusuario != null && !this.idnivelusuario.equals(other.idnivelusuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pojos.Nivelusuario[ idnivelusuario=" + idnivelusuario + " ]";
    }
    
}

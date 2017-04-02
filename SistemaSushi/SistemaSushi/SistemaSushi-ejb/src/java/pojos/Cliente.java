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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "CLIENTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c")
    , @NamedQuery(name = "Cliente.findByIdcliente", query = "SELECT c FROM Cliente c WHERE c.idcliente = :idcliente")
    , @NamedQuery(name = "Cliente.findByRutcliente", query = "SELECT c FROM Cliente c WHERE c.rutcliente = :rutcliente")
    , @NamedQuery(name = "Cliente.findByNombrecliente", query = "SELECT c FROM Cliente c WHERE c.nombrecliente = :nombrecliente")
    , @NamedQuery(name = "Cliente.findByApellidopcliente", query = "SELECT c FROM Cliente c WHERE c.apellidopcliente = :apellidopcliente")
    , @NamedQuery(name = "Cliente.findByPasscliente", query = "SELECT c FROM Cliente c WHERE c.passcliente = :passcliente")
    , @NamedQuery(name = "Cliente.findByEmailcliente", query = "SELECT c FROM Cliente c WHERE c.emailcliente = :emailcliente")})
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDCLIENTE")
    private BigDecimal idcliente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "RUTCLIENTE")
    private String rutcliente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "NOMBRECLIENTE")
    private String nombrecliente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "APELLIDOPCLIENTE")
    private String apellidopcliente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "PASSCLIENTE")
    private String passcliente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "EMAILCLIENTE")
    private String emailcliente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clienteIdcliente")
    private Collection<Direccion> direccionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clienteIdcliente")
    private Collection<Pedido> pedidoCollection;
    @JoinColumn(name = "NIVELUSUARIO_IDNIVELUSUARIO", referencedColumnName = "IDNIVELUSUARIO")
    @ManyToOne(optional = false)
    private Nivelusuario nivelusuarioIdnivelusuario;

    public Cliente() {
    }

    public Cliente(BigDecimal idcliente) {
        this.idcliente = idcliente;
    }

    public Cliente(BigDecimal idcliente, String rutcliente, String nombrecliente, String apellidopcliente, String passcliente, String emailcliente) {
        this.idcliente = idcliente;
        this.rutcliente = rutcliente;
        this.nombrecliente = nombrecliente;
        this.apellidopcliente = apellidopcliente;
        this.passcliente = passcliente;
        this.emailcliente = emailcliente;
    }

    public BigDecimal getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(BigDecimal idcliente) {
        this.idcliente = idcliente;
    }

    public String getRutcliente() {
        return rutcliente;
    }

    public void setRutcliente(String rutcliente) {
        this.rutcliente = rutcliente;
    }

    public String getNombrecliente() {
        return nombrecliente;
    }

    public void setNombrecliente(String nombrecliente) {
        this.nombrecliente = nombrecliente;
    }

    public String getApellidopcliente() {
        return apellidopcliente;
    }

    public void setApellidopcliente(String apellidopcliente) {
        this.apellidopcliente = apellidopcliente;
    }

    public String getPasscliente() {
        return passcliente;
    }

    public void setPasscliente(String passcliente) {
        this.passcliente = passcliente;
    }

    public String getEmailcliente() {
        return emailcliente;
    }

    public void setEmailcliente(String emailcliente) {
        this.emailcliente = emailcliente;
    }

    @XmlTransient
    public Collection<Direccion> getDireccionCollection() {
        return direccionCollection;
    }

    public void setDireccionCollection(Collection<Direccion> direccionCollection) {
        this.direccionCollection = direccionCollection;
    }

    @XmlTransient
    public Collection<Pedido> getPedidoCollection() {
        return pedidoCollection;
    }

    public void setPedidoCollection(Collection<Pedido> pedidoCollection) {
        this.pedidoCollection = pedidoCollection;
    }

    public Nivelusuario getNivelusuarioIdnivelusuario() {
        return nivelusuarioIdnivelusuario;
    }

    public void setNivelusuarioIdnivelusuario(Nivelusuario nivelusuarioIdnivelusuario) {
        this.nivelusuarioIdnivelusuario = nivelusuarioIdnivelusuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcliente != null ? idcliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.idcliente == null && other.idcliente != null) || (this.idcliente != null && !this.idcliente.equals(other.idcliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pojos.Cliente[ idcliente=" + idcliente + " ]";
    }
    
}

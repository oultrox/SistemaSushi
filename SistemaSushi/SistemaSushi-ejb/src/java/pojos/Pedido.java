/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Yisus
 */
@Entity
@Table(name = "PEDIDO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pedido.findAll", query = "SELECT p FROM Pedido p")
    , @NamedQuery(name = "Pedido.findByIdpedido", query = "SELECT p FROM Pedido p WHERE p.idpedido = :idpedido")
    , @NamedQuery(name = "Pedido.findByValorpedido", query = "SELECT p FROM Pedido p WHERE p.valorpedido = :valorpedido")
    , @NamedQuery(name = "Pedido.findByFechapedido", query = "SELECT p FROM Pedido p WHERE p.fechapedido = :fechapedido")
    , @NamedQuery(name = "Pedido.findByEstadopedido", query = "SELECT p FROM Pedido p WHERE p.estadopedido = :estadopedido")})
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDPEDIDO")
    private BigDecimal idpedido;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VALORPEDIDO")
    private BigInteger valorpedido;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAPEDIDO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechapedido;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "ESTADOPEDIDO")
    private String estadopedido;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pedidoIdpedido")
    private Collection<Producto> productoCollection;
    @JoinColumn(name = "CLIENTE_IDCLIENTE", referencedColumnName = "IDCLIENTE")
    @ManyToOne(optional = false)
    private Cliente clienteIdcliente;

    public Pedido() {
    }

    public Pedido(BigDecimal idpedido) {
        this.idpedido = idpedido;
    }

    public Pedido(BigDecimal idpedido, BigInteger valorpedido, Date fechapedido, String estadopedido) {
        this.idpedido = idpedido;
        this.valorpedido = valorpedido;
        this.fechapedido = fechapedido;
        this.estadopedido = estadopedido;
    }

    public BigDecimal getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(BigDecimal idpedido) {
        this.idpedido = idpedido;
    }

    public BigInteger getValorpedido() {
        return valorpedido;
    }

    public void setValorpedido(BigInteger valorpedido) {
        this.valorpedido = valorpedido;
    }

    public Date getFechapedido() {
        return fechapedido;
    }

    public void setFechapedido(Date fechapedido) {
        this.fechapedido = fechapedido;
    }

    public String getEstadopedido() {
        return estadopedido;
    }

    public void setEstadopedido(String estadopedido) {
        this.estadopedido = estadopedido;
    }

    @XmlTransient
    public Collection<Producto> getProductoCollection() {
        return productoCollection;
    }

    public void setProductoCollection(Collection<Producto> productoCollection) {
        this.productoCollection = productoCollection;
    }

    public Cliente getClienteIdcliente() {
        return clienteIdcliente;
    }

    public void setClienteIdcliente(Cliente clienteIdcliente) {
        this.clienteIdcliente = clienteIdcliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpedido != null ? idpedido.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pedido)) {
            return false;
        }
        Pedido other = (Pedido) object;
        if ((this.idpedido == null && other.idpedido != null) || (this.idpedido != null && !this.idpedido.equals(other.idpedido))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pojos.Pedido[ idpedido=" + idpedido + " ]";
    }
    
}

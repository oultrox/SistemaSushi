/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Yisus
 */
@Entity
@Table(name = "PRODUCTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p")
    , @NamedQuery(name = "Producto.findByIdproducto", query = "SELECT p FROM Producto p WHERE p.idproducto = :idproducto")
    , @NamedQuery(name = "Producto.findByNombreproducto", query = "SELECT p FROM Producto p WHERE p.nombreproducto = :nombreproducto")
    , @NamedQuery(name = "Producto.findByCantidadproducto", query = "SELECT p FROM Producto p WHERE p.cantidadproducto = :cantidadproducto")
    , @NamedQuery(name = "Producto.findByValorproducto", query = "SELECT p FROM Producto p WHERE p.valorproducto = :valorproducto")})
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDPRODUCTO")
    private BigDecimal idproducto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "NOMBREPRODUCTO")
    private String nombreproducto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CANTIDADPRODUCTO")
    private BigInteger cantidadproducto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VALORPRODUCTO")
    private BigInteger valorproducto;
    @JoinColumn(name = "PEDIDO_IDPEDIDO", referencedColumnName = "IDPEDIDO")
    @ManyToOne(optional = false)
    private Pedido pedidoIdpedido;

    public Producto() {
    }

    public Producto(BigDecimal idproducto) {
        this.idproducto = idproducto;
    }

    public Producto(BigDecimal idproducto, String nombreproducto, BigInteger cantidadproducto, BigInteger valorproducto) {
        this.idproducto = idproducto;
        this.nombreproducto = nombreproducto;
        this.cantidadproducto = cantidadproducto;
        this.valorproducto = valorproducto;
    }

    public BigDecimal getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(BigDecimal idproducto) {
        this.idproducto = idproducto;
    }

    public String getNombreproducto() {
        return nombreproducto;
    }

    public void setNombreproducto(String nombreproducto) {
        this.nombreproducto = nombreproducto;
    }

    public BigInteger getCantidadproducto() {
        return cantidadproducto;
    }

    public void setCantidadproducto(BigInteger cantidadproducto) {
        this.cantidadproducto = cantidadproducto;
    }

    public BigInteger getValorproducto() {
        return valorproducto;
    }

    public void setValorproducto(BigInteger valorproducto) {
        this.valorproducto = valorproducto;
    }

    public Pedido getPedidoIdpedido() {
        return pedidoIdpedido;
    }

    public void setPedidoIdpedido(Pedido pedidoIdpedido) {
        this.pedidoIdpedido = pedidoIdpedido;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idproducto != null ? idproducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.idproducto == null && other.idproducto != null) || (this.idproducto != null && !this.idproducto.equals(other.idproducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pojos.Producto[ idproducto=" + idproducto + " ]";
    }
    
}

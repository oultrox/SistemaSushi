/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
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
 * @author Centro de Trabajo
 */
@Entity
@Table(name = "DIRECCION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Direccion.findAll", query = "SELECT d FROM Direccion d")
    , @NamedQuery(name = "Direccion.findByIddireccion", query = "SELECT d FROM Direccion d WHERE d.iddireccion = :iddireccion")
    , @NamedQuery(name = "Direccion.findByComuna", query = "SELECT d FROM Direccion d WHERE d.comuna = :comuna")
    , @NamedQuery(name = "Direccion.findByProvincia", query = "SELECT d FROM Direccion d WHERE d.provincia = :provincia")
    , @NamedQuery(name = "Direccion.findByRegion", query = "SELECT d FROM Direccion d WHERE d.region = :region")
    , @NamedQuery(name = "Direccion.findByCalle", query = "SELECT d FROM Direccion d WHERE d.calle = :calle")
    , @NamedQuery(name = "Direccion.findByNumero", query = "SELECT d FROM Direccion d WHERE d.numero = :numero")
    , @NamedQuery(name = "Direccion.findByDepto", query = "SELECT d FROM Direccion d WHERE d.depto = :depto")
    , @NamedQuery(name = "Direccion.findByDetalledireccion", query = "SELECT d FROM Direccion d WHERE d.detalledireccion = :detalledireccion")})
public class Direccion implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDDIRECCION")
    private BigDecimal iddireccion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "COMUNA")
    private String comuna;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "PROVINCIA")
    private String provincia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "REGION")
    private String region;
    @Size(max = 45)
    @Column(name = "CALLE")
    private String calle;
    @Size(max = 5)
    @Column(name = "NUMERO")
    private String numero;
    @Size(max = 5)
    @Column(name = "DEPTO")
    private String depto;
    @Size(max = 45)
    @Column(name = "DETALLEDIRECCION")
    private String detalledireccion;
    @JoinColumn(name = "USUARIO_IDUSUARIO", referencedColumnName = "IDUSUARIO")
    @ManyToOne
    private Usuario usuarioIdusuario;
    @OneToMany(mappedBy = "direccionIddireccion")
    private List<Pedido> pedidoList;

    public Direccion() {
    }

    public Direccion(BigDecimal iddireccion) {
        this.iddireccion = iddireccion;
    }

    public Direccion(BigDecimal iddireccion, String comuna, String provincia, String region) {
        this.iddireccion = iddireccion;
        this.comuna = comuna;
        this.provincia = provincia;
        this.region = region;
    }

    public BigDecimal getIddireccion() {
        return iddireccion;
    }

    public void setIddireccion(BigDecimal iddireccion) {
        this.iddireccion = iddireccion;
    }

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDepto() {
        return depto;
    }

    public void setDepto(String depto) {
        this.depto = depto;
    }

    public String getDetalledireccion() {
        return detalledireccion;
    }

    public void setDetalledireccion(String detalledireccion) {
        this.detalledireccion = detalledireccion;
    }

    public Usuario getUsuarioIdusuario() {
        return usuarioIdusuario;
    }

    public void setUsuarioIdusuario(Usuario usuarioIdusuario) {
        this.usuarioIdusuario = usuarioIdusuario;
    }

    @XmlTransient
    public List<Pedido> getPedidoList() {
        return pedidoList;
    }

    public void setPedidoList(List<Pedido> pedidoList) {
        this.pedidoList = pedidoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddireccion != null ? iddireccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Direccion)) {
            return false;
        }
        Direccion other = (Direccion) object;
        if ((this.iddireccion == null && other.iddireccion != null) || (this.iddireccion != null && !this.iddireccion.equals(other.iddireccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pojos.Direccion[ iddireccion=" + iddireccion + " ]";
    }
    
}

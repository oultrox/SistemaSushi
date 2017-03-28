/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojos;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "DIRECCION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Direccion.findAll", query = "SELECT d FROM Direccion d")
    , @NamedQuery(name = "Direccion.findByIddireccion", query = "SELECT d FROM Direccion d WHERE d.iddireccion = :iddireccion")
    , @NamedQuery(name = "Direccion.findByComuna", query = "SELECT d FROM Direccion d WHERE d.comuna = :comuna")
    , @NamedQuery(name = "Direccion.findByProvincia", query = "SELECT d FROM Direccion d WHERE d.provincia = :provincia")
    , @NamedQuery(name = "Direccion.findByRegion", query = "SELECT d FROM Direccion d WHERE d.region = :region")
    , @NamedQuery(name = "Direccion.findByCalledireccion", query = "SELECT d FROM Direccion d WHERE d.calledireccion = :calledireccion")
    , @NamedQuery(name = "Direccion.findByNumerodireccion", query = "SELECT d FROM Direccion d WHERE d.numerodireccion = :numerodireccion")
    , @NamedQuery(name = "Direccion.findByDeptodireccion", query = "SELECT d FROM Direccion d WHERE d.deptodireccion = :deptodireccion")
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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "CALLEDIRECCION")
    private String calledireccion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "NUMERODIRECCION")
    private String numerodireccion;
    @Size(max = 5)
    @Column(name = "DEPTODIRECCION")
    private String deptodireccion;
    @Size(max = 45)
    @Column(name = "DETALLEDIRECCION")
    private String detalledireccion;
    @JoinColumn(name = "CLIENTE_IDCLIENTE", referencedColumnName = "IDCLIENTE")
    @ManyToOne(optional = false)
    private Cliente clienteIdcliente;

    public Direccion() {
    }

    public Direccion(BigDecimal iddireccion) {
        this.iddireccion = iddireccion;
    }

    public Direccion(BigDecimal iddireccion, String comuna, String provincia, String region, String calledireccion, String numerodireccion) {
        this.iddireccion = iddireccion;
        this.comuna = comuna;
        this.provincia = provincia;
        this.region = region;
        this.calledireccion = calledireccion;
        this.numerodireccion = numerodireccion;
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

    public String getCalledireccion() {
        return calledireccion;
    }

    public void setCalledireccion(String calledireccion) {
        this.calledireccion = calledireccion;
    }

    public String getNumerodireccion() {
        return numerodireccion;
    }

    public void setNumerodireccion(String numerodireccion) {
        this.numerodireccion = numerodireccion;
    }

    public String getDeptodireccion() {
        return deptodireccion;
    }

    public void setDeptodireccion(String deptodireccion) {
        this.deptodireccion = deptodireccion;
    }

    public String getDetalledireccion() {
        return detalledireccion;
    }

    public void setDetalledireccion(String detalledireccion) {
        this.detalledireccion = detalledireccion;
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

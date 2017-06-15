/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import pojos.Direccion;
import pojos.Usuario;
import servicios.DireccionFacadeLocal;
import servicios.UsuarioFacadeLocal;

/**
 *
 * @author FUKUSUKE MEDIA GROUP
 */
@Named(value = "direccionBean")
@SessionScoped
public class DireccionBean implements Serializable {

    @EJB
    private DireccionFacadeLocal direccionFacade;

    @EJB
    private UsuarioFacadeLocal usuarioFacade;
    /**
     * Creates a new instance of DireccionBean
     */
    private Direccion direccion;
    private Usuario usuario;

    public DireccionBean() {
        direccion = new Direccion();
    }

    public List<Direccion> getDirecciones() {
        return direccionFacade.findAll();
    }

    public DireccionFacadeLocal getDireccionFacade() {
        return direccionFacade;
    }

    public void setDireccionFacade(DireccionFacadeLocal direccionFacade) {
        this.direccionFacade = direccionFacade;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    private Direccion buscarDireccionxComuna() {
        Direccion d = new Direccion();
        List<Direccion> direcciones = this.obtenerComunas();
        for (Direccion direccione : direcciones) {
            if (direccione.getComuna().equals(this.direccion.getComuna())) {
                d = direccione;
            }
        }
        return d;
    }

    public String crearDireccionCliente() {
        try {

            Direccion dTemplate = this.direccionFacade.find(buscarDireccionxComuna().getIddireccion());

            this.direccion.setIddireccion(BigDecimal.valueOf(1));
            this.direccion.setComuna(dTemplate.getComuna());
            this.direccion.setProvincia(dTemplate.getProvincia());
            this.direccion.setRegion(dTemplate.getRegion());
            this.direccion.setCalle(this.direccion.getCalle());
            this.direccion.setNumero(this.direccion.getNumero());
            this.direccion.setDepto(this.direccion.getDepto());
            this.direccion.setDetalledireccion(this.direccion.getDetalledireccion());
            this.direccion.setUsuarioIdusuario(usuarioFacade.find(obtenerEsteCliente().getIdusuario()));
            this.direccionFacade.create(direccion);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Ingresado!", "¡Dirección Ingresada!."));
            return "addDireccionCliente";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "Vuelva a ingresar los datos."));
            return "addDireccionCliente";
        }
    }

    private Usuario obtenerEsteCliente() {
        FacesContext context = FacesContext.getCurrentInstance();
        Usuario u = (Usuario) context.getExternalContext().getSessionMap().get("user");
        return u;
    }

    public List<Direccion> obtenerDireccionesClientes() {
        List<Direccion> direcciones = this.direccionFacade.findAll();
        List<Direccion> dires = this.direccionFacade.findAll();
        direcciones.clear();
        for (Direccion direccion1 : dires) {
            if (direccion1.getUsuarioIdusuario() != null) {
                if (direccion1.getUsuarioIdusuario().getIdusuario().equals(this.obtenerEsteCliente().getIdusuario())) {
                    direcciones.add(direccion1);
                }
            }

        }
        return direcciones;
    }

    public String crearDireccionAdmin() {
        try {
            this.direccion.setIddireccion(BigDecimal.valueOf(1));
            this.direccion.setComuna(this.direccion.getComuna());
            this.direccion.setProvincia(this.direccion.getProvincia());
            this.direccion.setRegion(this.direccion.getRegion());
            this.direccionFacade.create(direccion);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Ingresado!", "¡Dirección Ingresada!."));
            this.direccion = new Direccion();
            return "mantenedorDireccion";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "Vuelva a ingresar los datos."));
            return "addDireccion";
        }
    }

    private String modificarDireccion() {
        try {
            Direccion dir = direccionFacade.find(direccion.getIddireccion());
            dir.setComuna(direccion.getComuna());
            dir.setProvincia(direccion.getProvincia());
            dir.setRegion(direccion.getRegion());
            dir.setCalle(direccion.getCalle());
            dir.setNumero(direccion.getNumero());
            dir.setDepto(direccion.getDepto());
            dir.setDetalledireccion(direccion.getDetalledireccion());
            dir.setUsuarioIdusuario(usuarioFacade.find(usuario.getIdusuario()));
            direccionFacade.edit(dir);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Nivel de Usuario Modificado", "Nivel Usuario modificado correctamente"));
            return "mantenedorDireccion";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "Vuelva a ingresar los datos."));
            return "ingresarDireccion";
        }
    }

    //Modificamos productos aquí
    public String modificarDireccionAdmin() {
        Direccion dir = direccionFacade.find(this.direccion.getIddireccion());
        dir.setComuna(direccion.getComuna());
        dir.setProvincia(direccion.getProvincia());
        dir.setRegion(direccion.getRegion());
        direccionFacade.edit(dir);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Dirección Modificada", "Dirección modificada correctamente"));
        return "mantenedorProducto";
    }

    public String eliminarDireccion(Direccion d) {
        Direccion dir = direccionFacade.find(d.getIddireccion());
        this.direccionFacade.remove(dir);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Direccion eliminada"));
        return "mantenedorDireccion";
    }

    public List<Direccion> obtenerComunas() {
        List<Direccion> dires = this.direccionFacade.findAll();
        dires.clear();
        for (Direccion val : this.direccionFacade.findAll()) {
            if (val.getUsuarioIdusuario() == null) {
                dires.add(val);
            }
        }
        return dires;
    }
}

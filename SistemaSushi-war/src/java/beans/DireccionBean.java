/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import pojos.Direccion;
import pojos.Usuario;
import servicios.DireccionFacadeLocal;
import servicios.UsuarioFacadeLocal;

/**
 *
 * @author Yisus
 */
@Named(value = "direccionBean")
@RequestScoped
public class DireccionBean {

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

    private String ingresarDireccion() {
        try {
            this.direccion.setComuna(this.direccion.getComuna());
            this.direccion.setProvincia(this.direccion.getProvincia());
            this.direccion.setRegion(this.direccion.getRegion());
            this.direccion.setCalle(this.direccion.getCalle());
            this.direccion.setNumero(this.direccion.getNumero());
            this.direccion.setDepto(this.direccion.getDepto());
            this.direccion.setDetalledireccion(this.direccion.getDetalledireccion());
            this.direccion.setUsuarioIdusuario(this.direccion.getUsuarioIdusuario());
            this.direccionFacade.create(direccion);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Ingresado!", "¡Dirección Ingresada!."));
            return "ingresarDireccion";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "Vuelva a ingresar los datos."));
            return "ingresarDireccion";
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

    private String eliminarDireccion() {
        Direccion dir = direccionFacade.find(direccion.getIddireccion());
        this.direccionFacade.remove(dir);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Nivel Usuario Eliminado"));
        return "mantenedorDireccion";
    }
}

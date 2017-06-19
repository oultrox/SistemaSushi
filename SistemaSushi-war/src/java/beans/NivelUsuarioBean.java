/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import pojos.Nivelusuario;
import servicios.NivelusuarioFacadeLocal;

/**
 *
 * @author Yisus
 */
@Named(value = "nivelUsuarioBean")
@RequestScoped
public class NivelUsuarioBean {

    @EJB
    private NivelusuarioFacadeLocal nivelusuarioFacade;

    /**
     * Creates a new instance of NivelUsuarioBean
     */
    private Nivelusuario nivelUsuario;

    public NivelUsuarioBean() {
        nivelUsuario = new Nivelusuario();
    }

    public List<Nivelusuario> getNivelUsuarios() {
        return nivelusuarioFacade.findAll();
    }

    public List<Nivelusuario> getNivelTrabajadores() {
        List<Nivelusuario> listaTrabajadores = this.nivelusuarioFacade.findAll();
        listaTrabajadores.clear();
        listaTrabajadores.add(this.nivelusuarioFacade.find(BigDecimal.valueOf(1))); // ADMINISTRADOR
        listaTrabajadores.add(this.nivelusuarioFacade.find(BigDecimal.valueOf(3))); // ENCARGADO
        listaTrabajadores.add(this.nivelusuarioFacade.find(BigDecimal.valueOf(5))); // CAJERO
        return listaTrabajadores;
    }

    public Nivelusuario getNivelUsuario() {
        return nivelUsuario;
    }

    public void setNivelUsuario(Nivelusuario nivelUsuario) {
        this.nivelUsuario = nivelUsuario;
    }

    private String ingresarNivelUsuario() {
        try {
            this.nivelUsuario.setIdnivelusuario(BigDecimal.valueOf(1));
            this.nivelUsuario.setNombrenivelusuario(this.nivelUsuario.getNombrenivelusuario());
            this.nivelUsuario.setDescripcionnivelusuario(this.nivelUsuario.getDescripcionnivelusuario());
            this.nivelusuarioFacade.create(nivelUsuario);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Ingresado!", "Nivel Usuario ingresado."));
            return "ingresarNivelUsuario";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "Vuelva a ingresar los datos."));
            return "ingresarNivelUsuario";
        }
    }

    private String modificarNivelUsuario() {
        try {
            Nivelusuario nvl = nivelusuarioFacade.find(nivelUsuario.getIdnivelusuario());
            nvl.setNombrenivelusuario(nivelUsuario.getNombrenivelusuario());
            nvl.setDescripcionnivelusuario(nivelUsuario.getDescripcionnivelusuario());
            nivelusuarioFacade.edit(nvl);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Nivel de Usuario Modificado", "Nivel Usuario modificado correctamente"));
            return "mantenedorNivelUsuario";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "Vuelva a ingresar los datos."));
            return "ingresarNivelUsuario";
        }
    }

    private String eliminarNivelUsuario() {
        Nivelusuario nvl = nivelusuarioFacade.find(nivelUsuario.getIdnivelusuario());
        this.nivelusuarioFacade.remove(nvl);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Nivel Usuario Eliminado"));
        return "mantenedorNivelUsuario";
    }
}

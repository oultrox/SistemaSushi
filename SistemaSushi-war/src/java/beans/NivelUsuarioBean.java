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
import pojos.Nivelusuario;
import pojos.Producto;
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
    }

    public List<Nivelusuario> getNivelUsuarios() {
        return nivelusuarioFacade.findAll();
    }

    public NivelusuarioFacadeLocal getNivelusuarioFacade() {
        return nivelusuarioFacade;
    }

    public void setNivelusuarioFacade(NivelusuarioFacadeLocal nivelusuarioFacade) {
        this.nivelusuarioFacade = nivelusuarioFacade;
    }

    public Nivelusuario getNivelUsuario() {
        return nivelUsuario;
    }

    public void setNivelUsuario(Nivelusuario nivelUsuario) {
        this.nivelUsuario = nivelUsuario;
    }

    private String modificarNivelUsuario() {
        Nivelusuario nvl = nivelusuarioFacade.find(nivelUsuario.getIdnivelusuario());
        nvl.setNombrenivelusuario(nivelUsuario.getNombrenivelusuario());
        nvl.setDescripcionnivelusuario(nivelUsuario.getDescripcionnivelusuario());
        nivelusuarioFacade.edit(nvl);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Nivel de Usuario Modificado", "Nivel Usuario modificado correctamente"));
        return "mantenedorNivelUsuario";
    }

    private String eliminarNivelUsuario() {
        Nivelusuario nvl = nivelusuarioFacade.find(nivelUsuario.getIdnivelusuario());
        this.nivelusuarioFacade.remove(nvl);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Nivel Usuario Eliminado"));
        return "mantenedorNivelUsuario";
    }
}
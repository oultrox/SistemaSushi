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
    private  Nivelusuario nivelUsuario;
    public NivelUsuarioBean() {
    }
    
    public List<Nivelusuario> getNivelUsuarios()
    {
        return nivelusuarioFacade.findAll();
    }
}

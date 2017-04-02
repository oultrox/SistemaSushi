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
import pojos.Direccion;
import servicios.DireccionFacadeLocal;

/**
 *
 * @author Yisus
 */
@Named(value = "direccionBean")
@RequestScoped
public class DireccionBean {

    @EJB
    private DireccionFacadeLocal direccionFacade;

    /**
     * Creates a new instance of DireccionBean
     */
    private Direccion direccion;
    public DireccionBean() {
    }
    
    public List<Direccion> getDirecciones()
    {
        return direccionFacade.findAll();
    }
}

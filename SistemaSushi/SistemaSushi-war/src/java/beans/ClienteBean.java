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
import pojos.Cliente;
import servicios.ClienteFacadeLocal;

/**
 *
 * @author Yisus
 */
@Named(value = "clienteBean")
@RequestScoped

public class ClienteBean {

    @EJB
    private ClienteFacadeLocal clienteFacade;
    /**
     * Creates a new instance of ClienteBean
     */
    //En vez de tener una variabla para cada campo, es preferible tener el objeto
    //en sí con sus getters y setters por defecto.
    private  Cliente cliente;
    
    public ClienteBean() {
       
    }
    
    public List<Cliente> getClientes()
    {
        return clienteFacade.findAll();
    }
    
}

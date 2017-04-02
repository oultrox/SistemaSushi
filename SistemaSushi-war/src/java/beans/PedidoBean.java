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
import pojos.Pedido;
import servicios.PedidoFacadeLocal;

/**
 *
 * @author Yisus
 */
@Named(value = "pedidoBean")
@RequestScoped
public class PedidoBean {

    @EJB
    private PedidoFacadeLocal pedidoFacade;

    
    /**
     * Creates a new instance of PedidoBean
     */
    private Pedido pedido;
    
    public PedidoBean() {
    }
    
    public List<Pedido> getPedidos()
    {
        return pedidoFacade.findAll();
    }
}

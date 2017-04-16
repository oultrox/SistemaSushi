/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import pojos.Pedido;
import pojos.Producto;
import pojos.Usuario;
import servicios.PedidoFacadeLocal;
import servicios.ProductoFacadeLocal;
import servicios.UsuarioFacadeLocal;

/**
 *
 * @author Yisus
 */
@Named(value = "pedidoBean")
@RequestScoped
public class PedidoBean {

    @EJB
    private PedidoFacadeLocal pedidoFacade;
    @EJB
    private ProductoFacadeLocal productoFacade;

    @EJB
    private UsuarioFacadeLocal usuarioFacade;

    /**
     * Creates a new instance of PedidoBean
     */
    private Pedido pedido;
    private Usuario usuario;
    private static ArrayList<Producto> productos =new ArrayList<>();;

    public PedidoBean() 
    {
       pedido = new Pedido();
    }

    public List<Pedido> getPedidos() {
        return pedidoFacade.findAll();
    }
    
    public List<Producto> getProductosCarrito()
    {
       return productos;
    }

    public PedidoFacadeLocal getPedidoFacade() {
        return pedidoFacade;
    }

    public void setPedidoFacade(PedidoFacadeLocal pedidoFacade) {
        this.pedidoFacade = pedidoFacade;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    private String modificarPedido() {
        Pedido ped = pedidoFacade.find(pedido.getIdpedido());
        ped.setValor(pedido.getValor());
        ped.setFecha(pedido.getFecha());
        ped.setEstado(pedido.getEstado());
        ped.setUsuarioIdusuario(usuarioFacade.find(usuario.getIdusuario()));
        pedidoFacade.edit(ped);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Pedido Modificado", "Pedido modificado correctamente"));
        return "mantenedorPedido";
    }

    private String eliminarPedido() {
        Pedido ped = pedidoFacade.find(pedido.getIdpedido());
        this.pedidoFacade.remove(ped);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pedido Eliminado"));
        return "mantenedorPedido";
    }
    private boolean loggedIn;
    //---------------------------------------------------------------------------
    //                          Carrito de compras espero yo.
    //-------------------------------------------------------------------------
    public void anadirCarrito(BigDecimal id)
    {
        RequestContext context = RequestContext.getCurrentInstance();
        try{
            Producto p = productoFacade.find(id);
            p.setInventarioIdinventario(null); //vuelvo nulo para que no se agregue a la bd como producto de inventario.
            System.out.println(p.toString());
            System.out.println(productos.size());
            productos.add(p);
            System.out.println(productos.size());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ingresado!", "Producto Añadido al carrito.")); 
            context.addCallbackParam("view", "promos.xhtml");
        }catch(Exception e)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error!", "El producto no ha podido ser añadido."));
            context.addCallbackParam("view", "promos.xhtml");
        }
                    
    }
    
    

}
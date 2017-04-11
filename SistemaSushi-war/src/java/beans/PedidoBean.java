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
import pojos.Pedido;
import pojos.Usuario;
import servicios.PedidoFacadeLocal;
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
    private UsuarioFacadeLocal usuarioFacade;

    /**
     * Creates a new instance of PedidoBean
     */
    private Pedido pedido;
    private Usuario usuario;

    public PedidoBean() {
    }

    public List<Pedido> getPedidos() {
        return pedidoFacade.findAll();
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
}
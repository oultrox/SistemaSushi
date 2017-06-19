/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;
import pojos.Pedido;
import pojos.Producto;
import pojos.Usuario;
import servicios.PedidoFacadeLocal;
import servicios.ProductoFacadeLocal;
import servicios.UsuarioFacadeLocal;
import java.util.Date;
import pojos.Direccion;

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
    private Direccion direccion;
    private static ArrayList<Producto> productos = new ArrayList<>();

    private boolean delivery;

    Date fechaPeriodoInicio, fechaPeriodoFinal;

    public PedidoBean() {
        pedido = new Pedido();
        delivery = false;
    }

    public Date getFechaPeriodoInicio() {
        return fechaPeriodoInicio;
    }

    public void setFechaPeriodoInicio(Date fechaPeriodoInicio) {
        this.fechaPeriodoInicio = fechaPeriodoInicio;
    }

    public Date getFechaPeriodoFinal() {
        return fechaPeriodoFinal;
    }

    public void setFechaPeriodoFinal(Date fechaPeriodoFinal) {
        this.fechaPeriodoFinal = fechaPeriodoFinal;
    }

    public boolean isDelivery() {
        return delivery;
    }

    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }

    public List<Pedido> getPedidos() {
        return pedidoFacade.findAll();
    }

    public List<Producto> getProductosCarrito() {
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

    public List<Pedido> pedidosHoy() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy");
        LocalDate localDate = LocalDate.now();
        List<Pedido> pd = this.pedidoFacade.findAll();
        pd.clear();
        for (Pedido pdido : this.pedidoFacade.findAll()) {
            if (pdido.getFecha().after(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                    || pdido.getFecha().equals(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))) {
                pd.add(pdido);
            }
        }
        return pd;
    }

    public String aprobarPago(Pedido pedido) {
        try {
            pedido.setEstado("PAGADO");
            this.pedidoFacade.edit(pedido);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Pedido enviado a despacho exitosamente!!!",
                    "Pedido N" + pedido.getIdpedido() + " Pagado"));

            return "aprobarVentas";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "ERROR",
                    "Error"));
            return "aprobarVentas";
        }

    }

    public int calcularTotalPeriodo() {
        int totalPeriodo = 0;
        for (Pedido reporteVenta : this.getReporteVentas()) {
            totalPeriodo += reporteVenta.getValor().intValue();
        }
        return totalPeriodo;

    }

    public String generarReporte() {

        return "reporteVentas";

    }

    public List<Pedido> getReporteVentas() {
        List<Pedido> reporteVentas = this.pedidoFacade.findAll();
        reporteVentas.clear();
        for (Pedido estePedido : this.getPedidosDespachados()) {
            if ((estePedido.getFecha().after(fechaPeriodoInicio))
                    || (estePedido.getFecha().equals(fechaPeriodoInicio))) {
                if ((estePedido.getFecha().before(fechaPeriodoFinal))
                        || (estePedido.getFecha().equals(fechaPeriodoFinal))) {
                    reporteVentas.add(estePedido);
                }

            }
        }
        return reporteVentas;
    }

    public List<Pedido> getPedidosDespachados() {
        List<Pedido> pedidosDespachado = this.pedidoFacade.findAll();
        pedidosDespachado.clear();

        for (Pedido val : this.pedidoFacade.findAll()) {
            if (val.getEstado().equalsIgnoreCase("DESPACHADO")) {
                pedidosDespachado.add(val);
            }

        }

        return pedidosDespachado;
    }

    public String modificarPedido() {
        Pedido ped = pedidoFacade.find(pedido.getIdpedido());
        ped.setValor(pedido.getValor());
        ped.setFecha(pedido.getFecha());
        ped.setEstado(pedido.getEstado());
        ped.setUsuarioIdusuario(usuarioFacade.find(usuario.getIdusuario()));
        pedidoFacade.edit(ped);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Pedido Modificado", "Pedido modificado correctamente"));
        return "mantenedorPedido";
    }

    public String eliminarPedido() {
        Pedido ped = pedidoFacade.find(pedido.getIdpedido());
        this.pedidoFacade.remove(ped);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pedido Eliminado"));
        return "mantenedorPedido";
    }
    private boolean loggedIn;

    //---------------------------------------------------------------------------
    //                          Carrito de compras espero yo.
    //-------------------------------------------------------------------------
    public void anadirCarrito(BigDecimal id) {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            Producto p = productoFacade.find(id);
            p.setCantidad(BigInteger.ONE); // seteo la cantidad en 1 siempre para el objeto nuevo a añadir al carrito.
            p.setInventarioIdinventario(null); //vuelvo nulo para que no se agregue a la bd como producto de inventario.

            boolean encontrado = false;
            for (Producto px : productos) {
                if (px.getIdproducto() == p.getIdproducto()) {
                    encontrado = true;
                    int suma = px.getCantidad().intValue();
                    suma++;
                    px.setCantidad(BigInteger.valueOf(suma));
                }
            }
            if (!encontrado) {
                productos.add(p);
            }

            System.out.println(productos.size());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ingresado!", "Producto Añadido al carrito."));
            context.addCallbackParam("view", "promos.xhtml");
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error!", "El producto no ha podido ser añadido."));
            context.addCallbackParam("view", "promos.xhtml");
        }

    }

    public void eliminarProductoCarrito(BigDecimal id) {
        ArrayList<Producto> pLista = new ArrayList<>();
        for (Producto p : productos) {
            if (p.getIdproducto() == id) {
                pLista.add(p);
            }
        }

        productos.removeAll(pLista);
    }

    public void limpiarCarrito() throws IOException {
        productos.removeAll(productos);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());

    }

    public long getTotalCarrito() {
        long total = 0;
        for (Producto px : productos) {
            total += px.getValor().doubleValue() * px.getCantidad().doubleValue();
        }
        return total;
    }

    public String verificarDireccion() {
        if (delivery) {
            return "seleccionarDireccion";
        } else {
            return "confirmarPedido";
        }

    }

    public String generarPedido(Direccion d) {
        try {
            pedido.setDetalle("Compras");
            pedido.setIdpedido(BigDecimal.ZERO);
            //qué fastidio lo de big integer, me hizo convertir el total en long...
            pedido.setValor(BigInteger.valueOf(getTotalCarrito()));

            Date date = new Date();
            pedido.setFecha(date);
            pedido.setEstado("EN PROCESO DE PAGO");

            //aun no implementado
            if (d != null) {
                pedido.setDireccionIddireccion(d);
            }

            pedido.setUsuarioIdusuario(asignarUsuario());

            this.pedidoFacade.create(pedido);
            return "confirmarCompra";
        } catch (Exception e) {
            return null;
        }

    }

    private Usuario asignarUsuario() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Usuario u = (Usuario) context.getExternalContext().getSessionMap().get("user");
            return u;
        } catch (Exception e) {
            return null;
        }
    }
}

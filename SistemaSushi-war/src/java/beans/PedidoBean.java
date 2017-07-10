/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.sun.xml.ws.policy.privateutil.PolicyUtils;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
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
import javax.enterprise.context.SessionScoped;
import pojos.Direccion;

/**
 *
 * @author Fukusuke media group
 */
@Named(value = "pedidoBean")
@SessionScoped
public class PedidoBean implements Serializable {

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

    public List<Pedido> pedidosPorPagar() {
        List<Pedido> pedidosPorPagar = this.pedidoFacade.findAll();
        pedidosPorPagar.clear();

        for (Pedido val : pedidosHoy()) {
            if (val.getEstado().equalsIgnoreCase("EN PROCESO DE PAGO")) {
                pedidosPorPagar.add(val);
            }
        }

        return pedidosPorPagar;
    }

    public List<Pedido> pedidosCliente(Usuario user) {
        List<Pedido> pedidosCliente = this.pedidoFacade.findAll();
        pedidosCliente.clear();

        for (Pedido pe : this.pedidoFacade.findAll()) {
            if (pe.getUsuarioIdusuario().equals(user)) {

                pedidosCliente.add(pe);

            }
        }

        return pedidosCliente;

    }

    public List<Pedido> pedidosClienteEnTransito(Usuario user) {
        List<Pedido> pedidosCliente = this.pedidoFacade.findAll();
        pedidosCliente.clear();

        for (Pedido pe : this.pedidoFacade.findAll()) {
            if (pe.getUsuarioIdusuario().equals(user)) {
                if (!pe.getEstado().equalsIgnoreCase("DESPACHADO")) {
                    if (!pe.getEstado().equalsIgnoreCase("CANCELADO")) {
                        if (!pe.getEstado().equalsIgnoreCase("DEPOSITADO")) {
                            pedidosCliente.add(pe);
                        }

                    }

                }

            }
        }

        return pedidosCliente;

    }

    public List<Pedido> pedidosClienteDespachado(Usuario user) {
        List<Pedido> pedidosCliente = this.pedidoFacade.findAll();
        pedidosCliente.clear();

        for (Pedido pe : this.pedidoFacade.findAll()) {
            if (pe.getUsuarioIdusuario().equals(user)) {
                if (pe.getEstado().equalsIgnoreCase("DESPACHADO")) {
                    pedidosCliente.add(pe);
                }

            }
        }

        return pedidosCliente;

    }

    public List<Pedido> pedidosDeliveryCancelados() {
        List<Pedido> pedidosCliente = this.pedidoFacade.findAll();
        pedidosCliente.clear();

        for (Pedido pe : this.pedidoFacade.findAll()) {
            if (pe.getEstado().equalsIgnoreCase("CANCELADO")) {
                if (!(pe.getDireccionIddireccion() == null)) {
                    pedidosCliente.add(pe);
                }

            }
        }

        return pedidosCliente;

    }

    public List<Pedido> pedidosParaDevolucion() {
        List<Pedido> pedidosCliente = this.pedidoFacade.findAll();
        pedidosCliente.clear();

        for (Pedido pe : this.pedidoFacade.findAll()) {
            if (pe.getEstado().equalsIgnoreCase("DEPOSITAR")) {
                if (!(pe.getDireccionIddireccion() == null)) {
                    pedidosCliente.add(pe);
                }

            }
        }

        return pedidosCliente;

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

    public String aprobarDevolucion(Pedido pedido) {
        try {
            this.pedido = this.pedidoFacade.find(pedido.getIdpedido());
            pedido.setEstado("DEPOSITAR");
            this.pedidoFacade.edit(pedido);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Pedido aprobado exitosamente!!!",
                    "Pedido N" + pedido.getIdpedido() + " aprobado para la devolución del dinero"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "ERROR",
                    "Error"));
        }
        return null;
    }

    public String cancelarPedido(Pedido pedido) {
        try {
            this.pedido = this.pedidoFacade.find(pedido.getIdpedido());
            pedido.setEstado("CANCELADO");
            this.pedidoFacade.edit(pedido);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Pedido cancelado exitosamente!!!",
                    "Pedido N" + pedido.getIdpedido() + " Cancelado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "ERROR",
                    "Error"));
        }
        return null;
    }

    public String cancelarPedidoDelivery(Pedido pedido) {
        try {
            this.pedido = this.pedidoFacade.find(pedido.getIdpedido());
            pedido.setEstado("CANCELADO");
            pedido.setDireccionIddireccion(null);
            this.pedidoFacade.edit(pedido);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Pedido cancelado exitosamente!!!",
                    "Pedido N" + pedido.getIdpedido() + " Cancelado"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "ERROR",
                    "Error"));
        }
        return null;
    }

    public String aprobarPago(Pedido pedido) {
        try {
            pedido.setEstado("EN DESPACHO");
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
            pedido.setDireccionIddireccion(null);
            if (d != null) {
                pedido.setDireccionIddireccion(d);
            }

            pedido.setUsuarioIdusuario(asignarUsuario());

            this.pedidoFacade.create(pedido);

            if (d == null) {
                FacesContext context = FacesContext.getCurrentInstance();
                context.getExternalContext().redirect("transaccionExitosa.xhtml");
            }

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

    public void preProcessPDFReporteVentas(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        //String logo = externalContext.getRealPath("") + File.separator + "resources" + File.separator + "demo" + File.separator + "images" + File.separator + "prime_logo.png";
        //pdf.add(Image.getInstance(logo));

        String totales = "TOTAL VENTAS PERIODO: $ " + this.calcularTotalPeriodo() + " pesos";
        pdf.add(new Paragraph(totales));

        String inicio = "INICIO PERIODO: " + this.getFechaPeriodoInicio();
        pdf.add(new Paragraph(inicio));

        String fin = "FIN PERIODO: " + this.getFechaPeriodoFinal();
        pdf.add(new Paragraph(fin));

        String salto = " ";
        pdf.add(new Paragraph(salto));

    }

    public void preProcessPDFBoleta(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

        String totales = "TOTAL : $ " + this.getTotalCarrito() + " pesos";
        pdf.add(new Paragraph(totales));

        String salto = " ";
        pdf.add(new Paragraph(salto));

    }

}

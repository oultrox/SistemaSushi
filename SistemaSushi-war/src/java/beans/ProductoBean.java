package beans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.UploadedFile;
import pojos.Pedido;
import pojos.Producto;
import servicios.InventarioFacadeLocal;
import servicios.PedidoFacadeLocal;
import servicios.ProductoFacadeLocal;

/**
 *
 * @author Fukusuke group
 */
@Named(value = "productoBean")
@ManagedBean
@SessionScoped
public class ProductoBean implements Serializable {

    @EJB
    private InventarioFacadeLocal inventarioFacade;

    @EJB
    private ProductoFacadeLocal productoFacade;

    @EJB
    private PedidoFacadeLocal pedidoFacade;

    /**
     * Creates a new instance of ProductoBean
     */
    private Producto producto;
    private UploadedFile file;
    private String destination = "images/producto/";
    private Pedido pedido;
    private ArrayList<Producto> productosCarrito;
    private int cantidadP;
    private int valorP;

    public ProductoBean() {
        producto = new Producto();
        productosCarrito = new ArrayList<>();
        pedido = new Pedido();
    }

    public ProductoFacadeLocal getProductoFacade() {
        return productoFacade;
    }

    public void setProductoFacade(ProductoFacadeLocal productoFacade) {
        this.productoFacade = productoFacade;
    }

    public int getCantidadP() {
        return cantidadP;
    }

    public void setCantidadP(int cantidadP) {
        this.cantidadP = cantidadP;
    }

    public int getValorP() {
        return valorP;
    }

    public void setValorP(int valorP) {
        this.valorP = valorP;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public List<Producto> getProductos() {
        return productoFacade.findAll();
    }

    public List<Producto> getProductosTipo() {
        List<Producto> productosTipo = productoFacade.findAll();
        productosTipo.clear();
        for (Producto val : getProductos()) {
            if (val.getInventarioIdinventario() == null) {
                productosTipo.add(val);
            }
        }
        return productosTipo;
    }

    //consigo los procutos que no tienen ningun inventario asociado!
    public List<Producto> getProductosInventariables() {
        List<Producto> productos = productoFacade.findAll();
        List<Producto> productosInventario = productoFacade.findAll();
        productosInventario.removeAll(productosInventario);
        for (Producto p : productos) {
            if (p.getInventarioIdinventario() == null) {
                productosInventario.add(p);
            }
        }
        return productosInventario;
    }

    //consigo los productos que se suponen deben salir online al estar anidados
    // a un inventario online.
    public List<Producto> getProductosInventarios() {
        List<Producto> productos = productoFacade.findAll();
        List<Producto> productosInventario = productoFacade.findAll();
        productosInventario.removeAll(productosInventario);
        for (Producto p : productos) {
            if (p.getInventarioIdinventario() != null) {
                productosInventario.add(p);
            }
        }
        return productosInventario;
    }

    public int getcantidadProductos() {
        return getProductosInventarios().size();
    }

    //ingresamos un producto al sistema en general. sin inventario asignado.
    public String ingresarProducto() {
        try {
            BigInteger cantidad = BigInteger.valueOf(this.cantidadP);
            BigInteger valor = BigInteger.valueOf(this.valorP);
            if (file != null) {
                upload();
            }
            this.producto.setIdproducto(BigDecimal.valueOf(1));
            this.producto.setNombre(this.producto.getNombre());
            this.producto.setCantidad(cantidad);
            this.producto.setValor(valor);
            this.producto.setInventarioIdinventario(null);

            this.productoFacade.create(producto);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ingresado!", "Producto " + this.producto.getNombre() + " ingresado."));
            return "registroProducto";
            //} else {
            //    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "Archivo debe ser subido"));
            //    return "registroProducto";

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "Vuelva a ingresar los datos."));
            return "registroProducto";
        }
    }

    //Modificamos productos aquí
    public String modificarProducto() {
        Producto pro = productoFacade.find(this.producto.getIdproducto());
        pro.setNombre(producto.getNombre());
        pro.setCantidad(producto.getCantidad());
        pro.setValor(producto.getValor());
        productoFacade.edit(pro);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Producto Modificado", "Producto modificado correctamente"));
        return "mantenedorProducto";
    }

    //y aquí los eliminamos en base al id seleccionado por el front-end.
    public String eliminarProducto(Producto p) {
        Producto pro = productoFacade.find(p.getIdproducto());
        this.productoFacade.remove(pro);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Producto Eliminado"));
        return "mantenedorProducto";
    }

    //--------------------------------------------------------------------------
    //                          IDEA PARA SUBIR EL ARCHIVO A LA CARPETA
    //                          OTRA IDEA SERÍA BLOBL EN LA BASE DE DATOS
    //--------------------------------------------------------------------------
    public void upload() {
        if (file != null) {
            try {
                copyFile(file.getFileName(), file.getInputstream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void copyFile(String fileName, InputStream in) {
        InputStream inputStr = null;
        try {
            inputStr = file.getInputstream();
        } catch (IOException e) {
            //log error
        }

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String directory = externalContext.getInitParameter("uploadDirectory");
        //String directoryB = externalContext.getInitParameter("uploadDirectoryB");
        //String filename = FilenameUtils.getName(file.getFileName());
        String extension = FilenameUtils.getExtension(file.getFileName());
        String filename = this.producto.getNombre() + "." + extension;
        filename = filename.toLowerCase();
        File destFile = new File(directory, filename);
        // File destFileB = new File(directoryB, filename);

        //use org.apache.commons.io.FileUtils to copy the File
        try {
            FileUtils.copyInputStreamToFile(inputStr, destFile);
            //   FileUtils.copyInputStreamToFile(inputStr, destFileB);
        } catch (IOException e) {
            //log error
        }
        FacesMessage msg = new FacesMessage(file.getFileName() + " subido correctamente.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    //Metodo para comprobar si existe este producto en el inventario
    private Boolean enInventario(BigDecimal id) {
        Boolean bandera = false;
        Producto ped = productoFacade.find(id);
        for (Producto pInventario : getProductosInventarios()) {
            if (pInventario.getNombre().equals(ped.getNombre())) {
                bandera = true;
                break;
            }
        }
        return bandera;
    }

    public void anadirInventarioProducto(BigDecimal id) {
        try {
            //llamamos al producto seleccionado
            Producto ped = productoFacade.find(id);

            //Comprobamos que un no exista un producto en el inventario con el mismo nombre
            if (this.enInventario(id)) {
                FacesMessage msg = new FacesMessage("Error! producto con el mismo nombre ya agregado.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                Producto pro = new Producto();
                pro.setIdproducto(BigDecimal.valueOf(1));
                pro.setNombre(ped.getNombre());
                pro.setCantidad(ped.getCantidad());
                pro.setValor(ped.getValor());

                //buscamos el primer item de nuestra bd de inventarios, ya que usaremos
                //solo una tabla de inventario para el sistema. o por lo menos así
                //es lo actual, dispuesto a cambios.
                //se asigna el producto al inventario para que al fin salga en la tienda online!
                pro.setInventarioIdinventario(inventarioFacade.findAll().get(0));
                this.productoFacade.create(pro);
                FacesMessage msg = new FacesMessage("Exitoso! producto agregado al inventario online.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } catch (Exception e) {
            FacesMessage msg = new FacesMessage("Error! producto no pudo ser"
                    + " agregado. ¿No hay un inventario disponible?");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    //funcion para sacar los productos del stock online del inventario.
    public void sacarInventarioProducto(BigDecimal id) {
        try {
            Producto ped = productoFacade.find(id);
            this.productoFacade.remove(ped);
            FacesMessage msg = new FacesMessage("Exitoso! producto sacado del inventario online.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage("Error! producto no pudo ser"
                    + " elimiado. Vuelva a intentarlo");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;
import pojos.Inventario;
import pojos.Pedido;
import pojos.Producto;
import servicios.InventarioFacadeLocal;
import servicios.PedidoFacadeLocal;
import servicios.ProductoFacadeLocal;

/**
 *
 * @author Yisus
 */
@Named(value = "productoBean")
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
    
    //consigo los procutos que no tienen ningun inventario asociado!
    public List<Producto> getProductosInventariables() 
    {
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
            //if (file != null) {
            //    upload();
            this.producto.setIdproducto(BigDecimal.valueOf(1));
            //Faltaba esto(?). les consultare hoy de todos modos (Rodrigo).
            this.producto.setNombre(this.producto.getNombre());
            this.producto.setCantidad(cantidad);
            this.producto.setValor(valor);

            this.producto.setImagen("imagentest");
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
    public String eliminarProducto(Producto producto) {
        Producto pro = productoFacade.find(producto.getIdproducto());
        this.productoFacade.remove(pro);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Producto Eliminado"));
        return "mantenedorProducto";
    }

    //--------------------------------------------------------------------------
    //                          IDEA PARA SUBIR EL ARCHIVO A LA CARPETA
    //                          OTRA IDEA SERÍA BLOBL EN LA BASE DE DATOS
    //--------------------------------------------------------------------------
    public void upload() {
        System.out.println("uploading");
        if (file != null) {
            System.out.println("the file is" + file);
            FacesMessage msg = new FacesMessage("Succesful" + file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            try {
                copyFile(file.getFileName(), file.getInputstream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("upload finished");
    }

    public void copyFile(String fileName, InputStream in) {
        try {

            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream(new File(destination + fileName));
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            in.close();
            out.flush();
            out.close();

            System.out.println("New file created!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
   public void anadirInventarioProducto(BigDecimal id)
   {
       try
       {
            //llamamos al producto seleccionado
            Producto ped = productoFacade.find(id);

            //buscamos el primer item de nuestra bd de inventarios, ya que usaremos
            //solo una tabla de inventario para el sistema. o por lo menos así
            //es lo actual, dispuesto a cambios.
            List<Inventario> inv = inventarioFacade.findAll();
            //se asigna el producto al inventario para que al fin salga en la tienda online!
            ped.setInventarioIdinventario(inv.get(0));
            productoFacade.edit(ped);
            FacesMessage msg = new FacesMessage("Exitoso! producto agregado al inventario online.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
       }catch(Exception e)
       {
            FacesMessage msg = new FacesMessage("Error! producto no pudo ser"
                    + " agregado. ¿No hay un inventario disponible?");
            FacesContext.getCurrentInstance().addMessage(null, msg);
       }
   }
   
   //funcion para sacar los productos del stock online del inventario.
   public void sacarInventarioProducto(BigDecimal id)
   {
       try
       {
        Producto ped = productoFacade.find(id);
        ped.setInventarioIdinventario(null);
        productoFacade.edit(ped);
        FacesMessage msg = new FacesMessage("Exitoso! producto sacado del inventario online.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
       }catch(Exception e)
       {
            FacesMessage msg = new FacesMessage("Error! producto no pudo ser"
                    + " agregado. ¿No hay un inventario disponible?");
            FacesContext.getCurrentInstance().addMessage(null, msg);
       }
   }

}

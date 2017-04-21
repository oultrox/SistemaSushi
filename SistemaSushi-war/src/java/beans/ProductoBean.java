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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;
import org.primefaces.event.FileUploadEvent;
import pojos.Pedido;
import pojos.Producto;
import servicios.PedidoFacadeLocal;
import servicios.ProductoFacadeLocal;

/**
 *
 * @author Yisus
 */
@Named(value = "productoBean")
@RequestScoped
@ManagedBean
public class ProductoBean {

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

    public ProductoBean() {
        producto = new Producto();
        productosCarrito = new ArrayList<>();
        pedido = new Pedido();
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
    
    public List<Producto> getProductosInventarios() 
    {
        List<Producto> productos = productoFacade.findAll();
        List<Producto> productosInventario = productoFacade.findAll();
        productosInventario.removeAll(productosInventario);
        for (Producto p : productos) {
            if (p.getInventarioIdinventario()!=null) 
            {
                productosInventario.add(p);
            }
        }
        return productosInventario;   
    }

    public int getcantidadProductos() {
        return getProductosInventarios().size();
    }

    public String ingresarProducto() {
        try {
            if (file != null) {
                upload();
                this.producto.setIdproducto(BigDecimal.valueOf(1));
                //Faltaba esto(?). les consultare hoy de todos modos (Rodrigo).
                this.producto.setNombre(this.producto.getNombre());
                this.producto.setCantidad(this.producto.getCantidad());
                this.producto.setValor(this.producto.getValor());
                //------------------------------------------------------------
                this.producto.setImagen(this.producto.getImagen());
                this.productoFacade.create(producto);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ingresado!", "Producto ingresado."));
                return "registroProducto";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "Archivo debe ser subido"));
                return "registroProducto";
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "Vuelva a ingresar los datos."));
            return "registroProducto";
        }
    }

    private String modificarProducto() {
        Producto pro = productoFacade.find(producto.getIdproducto());
        pro.setNombre(producto.getNombre());
        pro.setCantidad(producto.getCantidad());
        pro.setValor(producto.getValor());
        pro.setImagen(producto.getImagen());
        pro.setPedidoIdpedido(pedidoFacade.find(pedido.getIdpedido()));
        productoFacade.edit(pro);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Producto Modificado", "Producto modificado correctamente"));
        return "mantenedorProducto";
    }

    private String eliminarProducto() {
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
   
}

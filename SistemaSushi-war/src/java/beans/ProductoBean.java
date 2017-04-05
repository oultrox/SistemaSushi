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
import pojos.Producto;
import servicios.ProductoFacadeLocal;

/**
 *
 * @author Yisus
 */
@Named(value = "productoBean")
@RequestScoped
public class ProductoBean {

    @EJB
    private ProductoFacadeLocal productoFacade;

    
    /**
     * Creates a new instance of ProductoBean
     */
    private Producto producto;
    
    public ProductoBean() {
    }
    
    public List<Producto> getProductos()
    {
        return productoFacade.findAll();
    }
    
}
package pojos;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pojos.Inventario;
import pojos.Pedido;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-06-19T07:24:13")
@StaticMetamodel(Producto.class)
public class Producto_ { 

    public static volatile SingularAttribute<Producto, Inventario> inventarioIdinventario;
    public static volatile SingularAttribute<Producto, BigInteger> valor;
    public static volatile SingularAttribute<Producto, BigInteger> cantidad;
    public static volatile SingularAttribute<Producto, BigDecimal> idproducto;
    public static volatile SingularAttribute<Producto, String> nombre;
    public static volatile SingularAttribute<Producto, Pedido> pedidoIdpedido;

}
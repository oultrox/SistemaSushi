package pojos;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pojos.Pedido;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-03-28T02:49:48")
@StaticMetamodel(Producto.class)
public class Producto_ { 

    public static volatile SingularAttribute<Producto, String> nombreproducto;
    public static volatile SingularAttribute<Producto, BigInteger> cantidadproducto;
    public static volatile SingularAttribute<Producto, BigInteger> valorproducto;
    public static volatile SingularAttribute<Producto, BigDecimal> idproducto;
    public static volatile SingularAttribute<Producto, Pedido> pedidoIdpedido;

}
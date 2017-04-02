package pojos;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pojos.Cliente;
import pojos.Producto;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-03-30T22:31:55")
@StaticMetamodel(Pedido.class)
public class Pedido_ { 

    public static volatile SingularAttribute<Pedido, Date> fechapedido;
    public static volatile SingularAttribute<Pedido, Cliente> clienteIdcliente;
    public static volatile SingularAttribute<Pedido, String> estadopedido;
    public static volatile SingularAttribute<Pedido, BigDecimal> idpedido;
    public static volatile SingularAttribute<Pedido, BigInteger> valorpedido;
    public static volatile CollectionAttribute<Pedido, Producto> productoCollection;

}
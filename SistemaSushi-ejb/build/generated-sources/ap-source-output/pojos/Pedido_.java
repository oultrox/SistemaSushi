package pojos;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pojos.Producto;
import pojos.Usuario;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-04-02T21:22:14")
@StaticMetamodel(Pedido.class)
public class Pedido_ { 

    public static volatile SingularAttribute<Pedido, Date> fecha;
    public static volatile SingularAttribute<Pedido, String> estado;
    public static volatile SingularAttribute<Pedido, BigDecimal> idpedido;
    public static volatile SingularAttribute<Pedido, BigInteger> valor;
    public static volatile SingularAttribute<Pedido, Usuario> usuarioIdusuario;
    public static volatile CollectionAttribute<Pedido, Producto> productoCollection;

}
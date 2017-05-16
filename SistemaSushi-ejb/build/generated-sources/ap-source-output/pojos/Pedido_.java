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

<<<<<<< HEAD
@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-05-16T19:48:32")
=======
@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-05-14T22:46:49")
>>>>>>> master
@StaticMetamodel(Pedido.class)
public class Pedido_ { 

    public static volatile SingularAttribute<Pedido, Date> fecha;
    public static volatile SingularAttribute<Pedido, String> estado;
    public static volatile SingularAttribute<Pedido, BigDecimal> idpedido;
    public static volatile SingularAttribute<Pedido, BigInteger> valor;
    public static volatile SingularAttribute<Pedido, Usuario> usuarioIdusuario;
    public static volatile CollectionAttribute<Pedido, Producto> productoCollection;

}
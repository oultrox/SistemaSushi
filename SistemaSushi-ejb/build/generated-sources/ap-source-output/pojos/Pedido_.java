package pojos;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pojos.Direccion;
import pojos.Producto;
import pojos.Usuario;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-07-10T18:36:37")
@StaticMetamodel(Pedido.class)
public class Pedido_ { 

    public static volatile SingularAttribute<Pedido, Date> fecha;
    public static volatile SingularAttribute<Pedido, Direccion> direccionIddireccion;
    public static volatile SingularAttribute<Pedido, String> estado;
    public static volatile ListAttribute<Pedido, Producto> productoList;
    public static volatile SingularAttribute<Pedido, BigDecimal> idpedido;
    public static volatile SingularAttribute<Pedido, BigInteger> valor;
    public static volatile SingularAttribute<Pedido, Usuario> usuarioIdusuario;
    public static volatile SingularAttribute<Pedido, String> detalle;

}
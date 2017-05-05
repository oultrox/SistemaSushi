package pojos;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pojos.Direccion;
import pojos.Nivelusuario;
import pojos.Pedido;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-05-05T20:23:14")
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile SingularAttribute<Usuario, String> rut;
    public static volatile SingularAttribute<Usuario, String> pass;
    public static volatile SingularAttribute<Usuario, String> activado;
    public static volatile SingularAttribute<Usuario, String> apellidopaterno;
    public static volatile CollectionAttribute<Usuario, Direccion> direccionCollection;
    public static volatile CollectionAttribute<Usuario, Pedido> pedidoCollection;
    public static volatile SingularAttribute<Usuario, Nivelusuario> nivelusuarioIdnivelusuario;
    public static volatile SingularAttribute<Usuario, String> nombre;
    public static volatile SingularAttribute<Usuario, String> email;
    public static volatile SingularAttribute<Usuario, BigDecimal> idusuario;

}
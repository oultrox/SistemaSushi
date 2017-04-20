package pojos;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pojos.Direccion;
import pojos.Nivelusuario;
import pojos.Pedido;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-04-20T15:18:18")
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile SingularAttribute<Usuario, String> rut;
    public static volatile SingularAttribute<Usuario, String> pass;
    public static volatile ListAttribute<Usuario, Pedido> pedidoList;
    public static volatile SingularAttribute<Usuario, String> apellidopaterno;
    public static volatile ListAttribute<Usuario, Direccion> direccionList;
    public static volatile SingularAttribute<Usuario, Nivelusuario> nivelusuarioIdnivelusuario;
    public static volatile SingularAttribute<Usuario, String> nombre;
    public static volatile SingularAttribute<Usuario, String> email;
    public static volatile SingularAttribute<Usuario, BigDecimal> idusuario;

}
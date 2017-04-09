package pojos;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pojos.Usuario;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-04-09T19:11:19")
@StaticMetamodel(Direccion.class)
public class Direccion_ { 

    public static volatile SingularAttribute<Direccion, BigDecimal> iddireccion;
    public static volatile SingularAttribute<Direccion, String> numero;
    public static volatile SingularAttribute<Direccion, String> detalledireccion;
    public static volatile SingularAttribute<Direccion, String> calle;
    public static volatile SingularAttribute<Direccion, String> comuna;
    public static volatile SingularAttribute<Direccion, Usuario> usuarioIdusuario;
    public static volatile SingularAttribute<Direccion, String> depto;
    public static volatile SingularAttribute<Direccion, String> provincia;
    public static volatile SingularAttribute<Direccion, String> region;

}
package pojos;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pojos.Cliente;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-03-28T02:49:48")
@StaticMetamodel(Direccion.class)
public class Direccion_ { 

    public static volatile SingularAttribute<Direccion, BigDecimal> iddireccion;
    public static volatile SingularAttribute<Direccion, String> calledireccion;
    public static volatile SingularAttribute<Direccion, Cliente> clienteIdcliente;
    public static volatile SingularAttribute<Direccion, String> detalledireccion;
    public static volatile SingularAttribute<Direccion, String> comuna;
    public static volatile SingularAttribute<Direccion, String> numerodireccion;
    public static volatile SingularAttribute<Direccion, String> deptodireccion;
    public static volatile SingularAttribute<Direccion, String> provincia;
    public static volatile SingularAttribute<Direccion, String> region;

}
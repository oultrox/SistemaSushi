package pojos;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pojos.Direccion;
import pojos.Nivelusuario;
import pojos.Pedido;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-03-30T00:03:50")
@StaticMetamodel(Cliente.class)
public class Cliente_ { 

    public static volatile SingularAttribute<Cliente, String> rutcliente;
    public static volatile SingularAttribute<Cliente, String> emailcliente;
    public static volatile SingularAttribute<Cliente, String> passcliente;
    public static volatile CollectionAttribute<Cliente, Direccion> direccionCollection;
    public static volatile CollectionAttribute<Cliente, Pedido> pedidoCollection;
    public static volatile SingularAttribute<Cliente, Nivelusuario> nivelusuarioIdnivelusuario;
    public static volatile SingularAttribute<Cliente, BigDecimal> idcliente;
    public static volatile SingularAttribute<Cliente, String> nombrecliente;
    public static volatile SingularAttribute<Cliente, String> apellidopcliente;

}
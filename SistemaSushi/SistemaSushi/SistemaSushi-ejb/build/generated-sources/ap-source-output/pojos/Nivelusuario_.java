package pojos;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pojos.Cliente;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-03-30T22:31:55")
@StaticMetamodel(Nivelusuario.class)
public class Nivelusuario_ { 

    public static volatile SingularAttribute<Nivelusuario, BigDecimal> idnivelusuario;
    public static volatile SingularAttribute<Nivelusuario, String> nombrenivelusuario;
    public static volatile SingularAttribute<Nivelusuario, String> descripcionnivelusuario;
    public static volatile CollectionAttribute<Nivelusuario, Cliente> clienteCollection;

}
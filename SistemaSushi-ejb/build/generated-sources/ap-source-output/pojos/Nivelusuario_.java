package pojos;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pojos.Usuario;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-05-25T13:45:38")
@StaticMetamodel(Nivelusuario.class)
public class Nivelusuario_ { 

    public static volatile SingularAttribute<Nivelusuario, BigDecimal> idnivelusuario;
    public static volatile SingularAttribute<Nivelusuario, String> nombrenivelusuario;
    public static volatile ListAttribute<Nivelusuario, Usuario> usuarioList;
    public static volatile SingularAttribute<Nivelusuario, String> descripcionnivelusuario;

}
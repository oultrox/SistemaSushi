package beans;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.apache.commons.codec.digest.DigestUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import pojos.Usuario;
import servicios.UsuarioFacadeLocal;
import servicios.NivelusuarioFacadeLocal;

@Named(value = "usuarioBean")
@SessionScoped
public class UsuarioBean implements Serializable {

    @EJB
    private NivelusuarioFacadeLocal nivelusuarioFacade;

    @EJB
    private UsuarioFacadeLocal usuarioFacade;

    //En vez de tener una variabla para cada campo, es preferible tener el objeto
    //en sí con sus getters y setters por defecto.
    private Usuario usuario;
    boolean loggedIn = false;
    //Para el Login
    private String ingresoEmail;
    private String ingresoClave;
    private Usuario userLogueado;

    public UsuarioBean() {
        usuario = new Usuario();
    }

    public List<Usuario> getClientes() {
        return usuarioFacade.findAll();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario Usuario) {
        this.usuario = Usuario;
    }

    public String getIngresoEmail() {
        return ingresoEmail;
    }

    public void setIngresoEmail(String ingresoEmail) {
        this.ingresoEmail = ingresoEmail;
    }

    public String getIngresoClave() {
        return ingresoClave;
    }

    public void setIngresoClave(String ingresoClave) {
        this.ingresoClave = ingresoClave;
    }

    public Usuario getUserLogueado() {
        return userLogueado;
    }

    public void setUserLogueado(Usuario userLogueado) {
        this.userLogueado = userLogueado;
    }

    //Progreso de el registro - WIP PROGRESO
    public String signUp() {
        try {
            if (validarRut(usuario.getRut()))
            {
                if (existeEmail() || existeRut()) 
                {
                    limpiarCliente(usuario);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "Usuario ya existente en el sistema."));
                    return "registroUsuario";
                } else 
                {
                    //Nivel por defecto.
                    this.usuario.setNivelusuarioIdnivelusuario(nivelusuarioFacade.find(BigDecimal.valueOf(2)));
                    //Encriptación
                    this.usuario.setPass(DigestUtils.md5Hex(this.usuario.getPass()));
                    //Creacion
                    this.usuario.setIdusuario(BigDecimal.valueOf(1));
                    this.usuarioFacade.create(usuario);
                    limpiarCliente(usuario);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Usuario creado exitosamente!", "Ingrese con su rut y clave"));
                    return "registroUsuario";
                }
            } else
            {
                limpiarCliente(usuario);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "Rut invalido"));
                return "registroUsuario";
            }                  
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "Vuelva a ingresar los datos."));
            return "registroUsuario";
        }
    }

    private void limpiarCliente(Usuario usuario) {
        usuario.setNombre("");
        usuario.setRut("");
        usuario.setEmail("");
        usuario.setPass("");

    }

    private boolean existeRut() {
        List<Usuario> usuarios = this.usuarioFacade.findAll();
        for (Usuario usuario1 : usuarios) {
            if (usuario1.getRut().equals(usuario.getRut())) {
                return true;
            }
        }
        return false;
    }

    private boolean existeEmail() {
        List<Usuario> usuarios = this.usuarioFacade.findAll();
        for (Usuario usuario1 : usuarios) {
            if (usuario1.getEmail().equals(usuario.getEmail())) {
                return true;
            }
        }
        return false;
    }

    //Login - WIP PROGRESO
    public void login(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        this.ingresoClave = DigestUtils.md5Hex(this.ingresoClave);
        Usuario user = verificarUser();
        if (user != null) {

            int nivelUser = user.getNivelusuarioIdnivelusuario().getIdnivelusuario().intValue();
            loggedIn = true;
            this.userLogueado = user;
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", user);
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido(a) ", user.getNombre() + " ");
            FacesContext.getCurrentInstance().addMessage(null, message);
            context.addCallbackParam("loggedIn", loggedIn);
            switch (nivelUser) {
                case 1:
                    context.addCallbackParam("view", "inicioAdmin.xhtml");
                    break;
                case 2:
                    context.addCallbackParam("view", "inicioCliente.xhtml");
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                default:
                    throw new AssertionError();
            }

        } else {
            loggedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "RUT o Clave no validas");
            FacesContext.getCurrentInstance().addMessage(null, message);
            context.addCallbackParam("view", "loginUsuario.xhtml");
        }

    }

    public String logOut() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        loggedIn = false;
        return "index";
    }

    private Usuario verificarUser() {
        Usuario user = null;
        List<Usuario> usuarios = this.usuarioFacade.findAll();
        for (Usuario usuario1 : usuarios) {
            if (usuario1.getEmail().equals(ingresoEmail) && usuario1.getPass().equals(ingresoClave)) {
                user = usuario1;
                break;
            }
        }
        return user;
    }

    public void verificarNivelUsuarioAdmin() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Usuario u = (Usuario) context.getExternalContext().getSessionMap().get("user");
            if (u == null) {
                context.getExternalContext().redirect("index.xhtml");

            } else {
                int nivelUser = u.getNivelusuarioIdnivelusuario().getIdnivelusuario().intValue();
                if (nivelUser != 1) {
                    context.getExternalContext().redirect("index.xhtml");
                }
            }

        } catch (Exception e) {
            //log
        }
    }

    public void verificarNivelUsuarioCliente() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Usuario u = (Usuario) context.getExternalContext().getSessionMap().get("user");
            if (u == null) {
                context.getExternalContext().redirect("index.xhtml");

            } else {
                int nivelUser = u.getNivelusuarioIdnivelusuario().getIdnivelusuario().intValue();
                if (nivelUser != 2) {
                    context.getExternalContext().redirect("index.xhtml");
                }
            }
        } catch (Exception e) {

        }
    }
    
    public static boolean validarRut(String rut) 
    {
        boolean validacion = false;
        try 
        {
            rut =  rut.toUpperCase();
            rut = rut.replace(".", "");
            rut = rut.replace("-", "");
            int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));

            char dv = rut.charAt(rut.length() - 1);

            int m = 0, s = 1;
            for (; rutAux != 0; rutAux /= 10) 
            {
                s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
            }
            if (dv == (char) (s != 0 ? s + 47 : 75)) 
            {
                validacion = true;
            }

        }catch (java.lang.NumberFormatException e) 
        {
        } catch (Exception e) 
        {
        }
        return validacion;
    }
    
    public void formatear(){
        String rut = usuario.getRut();
        int cont=0;
        String format;
        if(rut.length() == 0)
        {
 
        }else{
            rut = rut.replace(".", "");
            rut = rut.replace("-", "");
            format = "-"+rut.substring(rut.length()-1);
            for(int i = rut.length()-2;i>=0;i--){
                format = rut.substring(i, i+1)+format;
                cont++;
                if(cont == 3 && i != 0){
                    format = "."+format;
                    cont = 0;
                }
            }
            usuario.setRut(format);
            
        }
    }

    
}
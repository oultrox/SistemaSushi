package beans;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.apache.commons.codec.digest.DigestUtils;
import org.primefaces.context.RequestContext;
import pojos.Nivelusuario;
import pojos.Usuario;
import servicios.UsuarioFacadeLocal;
import servicios.NivelusuarioFacadeLocal;

//MAIL JAVA
import java.util.Properties;
import java.util.Random;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
    private Nivelusuario nivelUsuario;
    boolean loggedIn = false;
    //Para el Login
    private String ingresoEmail;
    private String ingresoClave;
    private Usuario userLogueado;

    public UsuarioBean() {
        super();
        usuario = new Usuario();
        nivelUsuario = new Nivelusuario();
    }

    public List<Usuario> getUsuarios() {
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
                if (existeEmail() || existeRut()) {
                    limpiarCliente(usuario);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "Usuario ya existente en el sistema."));
                    return "registroUsuario";
                } 
                else 
                {
                    //Generacion de key
                    this.usuario.setActivado(this.getCadenaAlfanumAleatoria(15));
                    //Validación de mail en donde también envio la key de activado
                    //además de que separé para poder distinguir de quién es el error.
                    if (validarEmail(this.usuario.getEmail())) 
                    {
                      
                        //Nivel por defecto.
                        this.usuario.setNivelusuarioIdnivelusuario(nivelusuarioFacade.find(BigDecimal.valueOf(2)));
                        //Encriptación
                        this.usuario.setPass(DigestUtils.md5Hex(this.usuario.getPass()));
                        //Creacion
                        this.usuario.setIdusuario(BigDecimal.valueOf(1));
                        //Faltaba esto(?), de todos modos les consultare hoy. (Rodrigo)
                        this.usuario.setRut(this.usuario.getRut());
                        this.usuario.setNombre(this.usuario.getNombre());
                        this.usuario.setEmail(this.usuario.getEmail());

                        // -------------------------------------------------
                        this.usuarioFacade.create(usuario);
                        limpiarCliente(usuario);
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Usuario creado exitosamente!", "Ingrese con su rut y clave"));
                        return "loginUsuario";  
                    }else
                    {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "Correo no enviado. "));
                        return "registroUsuario";
                    }
                }
            } 
            else 
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

    private String modificarUsuario() throws MessagingException {
        Usuario us = usuarioFacade.find(usuario.getIdusuario());
        if (validarRut(this.usuario.getRut()) && validarEmail(this.usuario.getEmail())) {
            us.setRut(usuario.getRut());
            us.setNombre(usuario.getNombre());
            us.setApellidopaterno(usuario.getApellidopaterno());
            us.setEmail(usuario.getEmail());
            us.setPass(DigestUtils.md5Hex(usuario.getPass()));
            us.setNivelusuarioIdnivelusuario(nivelusuarioFacade.find(nivelUsuario.getIdnivelusuario()));
            usuarioFacade.edit(us);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario Modificado", "Usuario modificado correctamente"));
            return "mantenedorUsuario";
        } else {
            limpiarCliente(usuario);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "Rut invalido o Email Invalido"));
            return "mantenedorUsuario";
        }
    }

    private String eliminarUsuario() {
        Usuario us = usuarioFacade.find(usuario.getIdusuario());
        this.usuarioFacade.remove(us);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuario Eliminado"));
        return "mantenedorUsuario";
    }

    private void limpiarCliente(Usuario usuario) {
        usuario.setNombre("");
        usuario.setRut("");
        usuario.setEmail("");
        usuario.setPass("");
        usuario.setActivado("");
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
        if (verificarUserActivado()) 
        {
            if (user != null) 
            {
                int nivelUser = user.getNivelusuarioIdnivelusuario().getIdnivelusuario().intValue();
                loggedIn = true;
                this.userLogueado = user;
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", user);
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido(a) ", user.getNombre() + " ");
                FacesContext.getCurrentInstance().addMessage(null, message);
                context.addCallbackParam("loggedIn", loggedIn);
                switch (nivelUser) 
                {
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
            } 
            else {
            loggedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "RUT o Clave no validas");
            FacesContext.getCurrentInstance().addMessage(null, message);
            context.addCallbackParam("view", "loginUsuario.xhtml");
            }
        }
        else
        {
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "¡Debes activar tu cuenta a través de tu correo!");
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
    
        private boolean verificarUserActivado() {
        Usuario user = null;
        List<Usuario> usuarios = this.usuarioFacade.findAll();
        for (Usuario usuario1 : usuarios) {
            if (usuario1.getEmail().equals(ingresoEmail) && usuario1.getPass().equals(ingresoClave) && usuario1.getActivado().equals("activo")) {
                return true;
            }
        }
        return false;
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

    public static boolean validarRut(String rut) {
        boolean validacion = false;
        try {
            rut = rut.toUpperCase();
            rut = rut.replace(".", "");
            rut = rut.replace("-", "");
            int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));

            char dv = rut.charAt(rut.length() - 1);

            int m = 0, s = 1;
            for (; rutAux != 0; rutAux /= 10) {
                s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
            }
            if (dv == (char) (s != 0 ? s + 47 : 75)) {
                validacion = true;
            }

        } catch (java.lang.NumberFormatException e) {
        } catch (Exception e) {
        }
        return validacion;
    }

    public void formatear() {
        String rut = usuario.getRut();
        int cont = 0;
        String format;
        if (rut.length() == 0) {

        } else {
            rut = rut.replace(".", "");
            rut = rut.replace("-", "");
            format = "-" + rut.substring(rut.length() - 1);
            for (int i = rut.length() - 2; i >= 0; i--) {
                format = rut.substring(i, i + 1) + format;
                cont++;
                if (cont == 3 && i != 0) {
                    format = "." + format;
                    cont = 0;
                }
            }
            usuario.setRut(format);
        }
    }

    //Validador de Email usando la libreria de Javax.Mail
    public boolean validarEmail(String email) {
        final String username = "portafolioSushi@gmail.com";
        final String password = "duoc2017";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("Sushi"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject("Confirmacion cuenta creada SistemaSushi");
            message.setText("Estimado " + this.usuario.getNombre() + " " + this.usuario.getApellidopaterno()
                    + "\nGracias por ingresar al sistema de compras de Sushi a domicilio"
                    + "\n\n"
                    + "CODIGO DE ACTIVACION: " + this.usuario.getActivado() 
                    + "\n <a href='http://localhost:8081/SistemaSushi-war/faces/activacionCuenta.xhtml'>Clickea aquí para activar</a>"
                    + "SUS DATOS SON:"
                    + "\n Rut :" + this.usuario.getRut()
                    + "\n Correo: " + this.usuario.getEmail()
                    + "\n Pass: " + this.usuario.getPass()
                    + "\n\n"
            );

            Transport.send(message);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mensaje enviado exitosamente!!!"));
            return true;
        } catch (MessagingException e) {
            return false;
        }
    }
    
    //-----------------------------------------------------------------------
    //                          Activación de Email por Link
    //-----------------------------------------------------------------------
    
    //Cadena que genera el código que irá en el atributo de 'Activación' 
    //hasta que se active accediendo por el correo en donde se le envía.
    public String getCadenaAlfanumAleatoria(int longitud) {
		String cadenaAleatoria = "";
		long milis = new java.util.GregorianCalendar().getTimeInMillis();
		Random r = new Random(milis);
		int i = 0;
		while (i < longitud) {
			char c = (char) r.nextInt(255);
			// System.out.println("char:"+c);
			if ((c >= '0' && c <= 9) || (c >= 'A' && c <= 'Z')) {
				cadenaAleatoria += c;
				i++;
			}
		}
		return cadenaAleatoria;
    }
    
    //Función simple que activa la cuenta tomando el valor del input text
    //y comparandolo con todos en la base de datos.
    public String activarCuenta()
    {
        List<Usuario> usuarios = this.usuarioFacade.findAll();
        for (Usuario usuario1 : usuarios) {
            if ( usuario1.getActivado().equals(usuario.getActivado()) && !usuario1.getActivado().equals("activo")) 
            {
                usuario1.setActivado("activado");
                this.usuarioFacade.edit(usuario1);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cuenta activada exitosamente!!!"));
            
                return "LoginUsuario";
            }
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Código incorrecto"));
        return "activacionCuenta";
    }
}

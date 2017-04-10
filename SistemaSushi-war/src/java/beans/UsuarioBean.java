package beans;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
import org.primefaces.context.RequestContext;
import pojos.Nivelusuario;
import pojos.Usuario;
import servicios.UsuarioFacadeLocal;
import servicios.NivelusuarioFacadeLocal;

@Named(value = "usuarioBean")
@SessionScoped
public class UsuarioBean extends HttpServlet implements Serializable {

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
            if (validarRut(usuario.getRut()) && validarEmail(this.usuario.getEmail())) {
                if (existeEmail() || existeRut()) {
                    limpiarCliente(usuario);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "Usuario ya existente en el sistema."));
                    return "registroUsuario";
                } else {
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
            } else {
                limpiarCliente(usuario);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "Rut invalido o Email Invalido"));
                return "registroUsuario";
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR", "Vuelva a ingresar los datos."));
            return "registroUsuario";
        }
    }

    private String modificarUsuario() {
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
    public static boolean validarEmail(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    //-------------------------------------------------------------------------
    //----------------Proceso de envio de correo de confirmacion---------------
    //-------------------------------------------------------------------------
    //proceso DE ENVIO DE CORREO - TRABAJO EN PROGRESO
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

    //Aun no tengo idea de como llamar este void lol. seguramente tiene que ver
    //con el hecho de que fue sacado de un servlet.
    protected void service(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ENTRA2");

        //Hay que conseguir los datos del usuario de UsuarioBean
        String nombre = this.usuario.getNombre();
        String contra = this.usuario.getPass();
        String email = this.usuario.getEmail();

        String aleatoria = getCadenaAlfanumAleatoria(8);
        HttpSession session1 = request.getSession();

        session1.setAttribute("aleatoria", aleatoria);

        try {
            // Propiedades de la conexion
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.user", "gcorreageek@gmail.com");
            props.setProperty("mail.smtp.auth", "true");

            // Preparamos la sesion
            Session session = Session.getDefaultInstance(props);

            // Construimos el mensaje
            MimeMessage message = new MimeMessage(session);
            // la persona que tiene que verificar
            message.setFrom(new InternetAddress("gcorreageek@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.addHeader("Disposition-Notification-To", "gcorreacaja@hotmail.com");
            message.setSubject("Correo de verificacion, porfavor no responder");
            message.setText(
                    " Este es un correo de verificacion \n"
                    + "Gracias por registrarse a SushiPower.COM \n"
                    + "Porfavor haga click en el siguiente enlace\n"
                    + "para seguir con la verificacion de sus datos \n"
                    + "  <a href='http://localhost:8080/confirmacionCorreo/ActivacionCuenta?usuario=" + nombre + "&aleatorio=" + aleatoria
                    + "'>Enlace</a>  ", "ISO-8859-1", "html");

            // Lo enviamos.
            System.out.println("URL:" + "http://localhost:8080/confirmacionCorreo/ActivacionCuenta?usuario=" + nombre + "&aleatorio=" + aleatoria);
            Transport t = session.getTransport("smtp");
            t.connect("gcorreageek@gmail.com", "5526296cpC");
            t.sendMessage(message, message.getAllRecipients());

            // Cierre.
            t.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("Res", "Porfavor revise su corre\n " + "Senor:" + nombre);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/respuesta.jsp");
        rd.forward(request, response);
    }

    //-------------------------------------------------------------------------
    //                          ayura
    //-------------------------------------------------------------------------
    //Activacion por link
    protected void activacionCuenta(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {
            String usu = request.getParameter("usuario");
            String ale = request.getParameter("aleatorio");
            System.out.println("Request:" + usu);
            System.out.println("Request:" + ale);
            // System.out.println("Verificacion de los datos\n" +
            // "SELECT * FROM TB_USUARIO WHERE USU='usuario' AND ALE='aleatorio'");
            //Verificas con la bd!
            String usuario = (String) request.getSession().getAttribute("usuario");
            String aleatorio = (String) request.getSession().getAttribute("aleatoria");
            System.out.println("Session:" + usuario);
            System.out.println("Session:" + aleatorio);

            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            try {
                String cliente = request.getRemoteAddr();
                out.println("");
                out.println("");
                out.println("");
                out.println("");
                if (usu.equals(usuario)) {
                    if (ale.equals(aleatorio)) {

                        out.println("<h3>Bienvenido Usuario:" + usu + "</h3>");
                        out.println("<b>Gracias por verificar su Usuario</b>");
                        out.println("");
                        out.println("");
                        System.out
                                .println("El Usuario a confirmado su Alta Nueva!");
                    } else {
                        out.println("<h3>ERROR!</h3>");
                        out.println("<b>Lo sentimos no es el numero de registro</b>");
                        out.println("");
                        out.println("");
                        System.out
                                .println("Lo sentimos no es el numero de registro");
                    }
                } else {
                    out.println("<h3>ERROR!</h3>");
                    out.println("<b>No existe usuario</b>");
                    out.println("");
                    out.println("");
                    System.out.println("No existe usuario!");
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

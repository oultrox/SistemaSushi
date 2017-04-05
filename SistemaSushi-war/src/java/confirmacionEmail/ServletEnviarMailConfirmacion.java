package confirmacionEmail;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import javax.ejb.EJB;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pojos.Usuario;
import servicios.UsuarioFacadeLocal;

/**
 * Servlet implementation class ServletEnviarMailConfirmacion
 */
public class ServletEnviarMailConfirmacion extends HttpServlet {
    @EJB
    private UsuarioFacadeLocal usuarioFacade;
    
    private static final long serialVersionUID = 1L;
    private Usuario usuario; 
	 
 
	public ServletEnviarMailConfirmacion() {
		System.out.println("ENTRA"); 
                usuario = new Usuario();
	}

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

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ENTRA2");
                
                //Hay que conseguir los datos del usuario de UsuarioBean
		String nombre = request.getParameter("nombre");
		String usuario = request.getParameter("usuario");
		String contra = request.getParameter("contra");
		String email = request.getParameter("mail");
                
		String aleatoria = getCadenaAlfanumAleatoria(8);
		HttpSession session1 = request.getSession();
		
		session1.setAttribute("aleatoria", aleatoria);
		session1.setAttribute("usuario", usuario);

		//Se guarda en bd!
				
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
			// la persona k tiene k verificar
			message.setFrom(new InternetAddress("gcorreageek@gmail.com"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress( email));
			message.addHeader("Disposition-Notification-To", "gcorreacaja@hotmail.com");
			message.setSubject("Correo de verificacion, porfavor no responder");
			message.setText(
					" Este es un correo de verificacion \n"
							+ "Gracias por escribirse a B2MINING.COM \n"
							+ "Porfavor haga click en el siguiente enlace\n"
							+ "para seguir con la verificacion de sus datos \n"
							+ "  <a href='http://localhost:8080/confirmacionCorreo/ActivacionCuenta?usuario=" + usuario + "&aleatorio=" + aleatoria
							+ "'>Enlace</a>  ", "ISO-8859-1", "html");

			// Lo enviamos.
			
			System.out.println("URL:"+"http://localhost:8080/confirmacionCorreo/ActivacionCuenta?usuario=" + usuario + "&aleatorio=" + aleatoria);
			Transport t = session.getTransport("smtp");
			t.connect("gcorreageek@gmail.com", "5526296cpC");
			t.sendMessage(message, message.getAllRecipients());

			// Cierre.
			t.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("Res", "Porfavor revise su corre\n " + "Se�or:" + nombre); 
		RequestDispatcher rd = getServletContext().getRequestDispatcher( "/respuesta.jsp");
		rd.forward(request, response);

	}

}
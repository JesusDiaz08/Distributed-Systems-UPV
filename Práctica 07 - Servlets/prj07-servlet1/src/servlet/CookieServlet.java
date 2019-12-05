package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;	// Cookie
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CookieServlet
 */
@WebServlet("/CookieServlet")
public class CookieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CookieServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* Modificar el metodo doGet para que el servlet defina un nuevo cookie, cuyo nombre y valor se soliciten en el formulario, y lo envia al cliente web.
		El servlet respondera con una pagina HTML (dinámica) que visualiza el nuevo cookie */
		String name  = request.getParameter("Nombre");
		String value = request.getParameter("Valor");
		String title = "Servlet1";
		String msg   = "Nueva Cookie";

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("<head><title>" + title + "</title></head>");
		out.println("<body>");
		out.println("<h1>" + msg + "</h1>");
		
		if( name != "" && value != ""){
			Cookie getBook = new Cookie(name, value); // Creacion de cookie
			getBook.setMaxAge(60 * 60 * 24); // Duracion de sesion y vida de cookie
			response.addCookie(getBook);
			out.println("<br>Cookie["+name+"]="+value+"</br>");
		}else{
			out.println("<br>El Nombre o el Valor de la cookie no puede ser nulo</br>");
		}
		out.println("</body></html>");
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**/
		response.setContentType("text/html");
		String title = "Servlet1";
		Cookie[] cookies = request.getCookies();

		PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("<head><title>" + title + "</title></head>");
		out.println("<body>");
		out.println("<h1>Lista de Cookies:</h1>");
 	    if(cookies == null) {
		    out.println("<br>" +"Aun no hay cookies creadas");
 	    }else {
 	    	 for(int i=0; i< cookies.length;i++) {
 			    	out.println("<br>Cookie["+cookies[i].getName()+"]="+cookies[i].getValue());
 			    }
 	    }
 	    out.println("</body></html>");
 	    out.close();
	}

}

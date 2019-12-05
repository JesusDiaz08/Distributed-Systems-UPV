package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Servlet1
 */
@WebServlet("/Servlet1")
public class Servlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet1() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");

		String msg = "El servlet ha recibido GET. Esta es la respuesta";
		String title = "Servlet1";

		PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("<head><title>" + title + "</title></head>");
		out.println("<body>");
		out.println("<h1>" + msg + "</h1>");
		out.println("</body></html>");
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		response.setContentType("text/html");

		String msg = "El servlet ha recibido POST. Esta es la respuesta";
		String title = "Servlet1";

		PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("<head><title>" + title + "</title></head>");
		out.println("<body>");
		out.println("<h1>" + msg + "</h1>");
		out.println("</body></html>");
		out.close();
	}

}

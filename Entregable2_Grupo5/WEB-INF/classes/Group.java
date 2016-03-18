import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;


public class Group extends HttpServlet {

	

    public void init(ServletConfig config) throws ServletException {
	super.init(config);
	System.out.println("Iniciando...");
    }
    public void destroy() {
    System.out.println("No hay nada que hacer...");
    }  
	
    public void doGet (HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
  
		HttpSession session = request.getSession(true);
		PrintWriter out = resp.getWriter();	
	
	
	out.println("<html>");
	out.println("<body>");
	
	
	

	out.println("<form action=\"Graph\" method=\"GET\">");
	out.println("Insert your group name: <input type=\"text\" name=\"Group\" ><br>");
	
	out.println("<input type=\"submit\" value=\"Submit form\">");
	out.println("</form>");

	out.println("</body>");
	out.println("</html>");
	}
}
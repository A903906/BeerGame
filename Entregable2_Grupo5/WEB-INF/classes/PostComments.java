import java.io.*;
import java.net.URL;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;

@SuppressWarnings("serial")
public class PostComments extends HttpServlet {
     Connection connection;

	public void init(ServletConfig config) throws ServletException {
			super.init(config);
			try {
				Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				String url="jdbc:odbc:BeerGame";
				connection=DriverManager.getConnection(url); 
			} catch(Exception e) {
				e.printStackTrace();
			}
		}	 

 
  public void doGet (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    HttpSession session = req.getSession(false);
	String username = (String)session.getAttribute("username");

	String sql = "INSERT INTO Comments ( user_id, comment) VALUES (";	

 
        sql +=  "'" + username + "'";
        sql +=  ", '" + req.getParameter("comment") + "')";
        System.out.println("Insert sql: " + sql);
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Resulset: " + sql + " Exception: " + e);
        }
	
	 res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();
		//PrintWriter toClient = res.getWriter();
        
        toClient.println("<!DOCTYPE HTML>");
        toClient.println("<HTML>");
		toClient.println("<HEAD>");
		toClient.println("<TITLE>Your comment</TITLE>");
		toClient.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
		toClient.println("<link href=\"beerIcon.jpg\" rel=\"shortcut icon\">");
		toClient.println("<meta charset=\"utf-8\"></meta>");
		toClient.println("<meta content=\"IE=edge\" http-equiv=\"X-UA-Compatible\"></meta>");
		toClient.println("<meta content=\"width=device-width, initial-scale=1\" name=\"viewport\"></meta>");
		
		toClient.println("<link rel=\"stylesheet\" href=\"http://getbootstrap.com/dist/css/bootstrap.min.css\"></link>");
		toClient.println("<link rel=\"stylesheet\" href=\"http://getbootstrap.com/assets/css/ie10-viewport-bug-workaround.css\"></link>");
		toClient.println("<link rel=\"stylesheet\" href=\"beerDesign.css\">");
		
		toClient.println("</HEAD>");
		toClient.println("<BODY>");
		toClient.println("<nav class=\"navbar navbar-default beer\">");
		toClient.println(" <div class=\"container\">");
		toClient.println("<div class=\"navbar-header\">");
		toClient.println("<button type=\"button\" class=\"navbar-toggle collapsed\" data-toggle=\"collapse\" data-target=\"#navbar\" aria-expanded=\"false\" aria-controls=\"navbar\">");
		toClient.println("<span class=\"sr-only\">Toggle navigation</span>");
		toClient.println("<span class=\"icon-bar\"></span>");
		toClient.println("<span class=\"icon-bar\"></span>");
		toClient.println("<span class=\"icon-bar\"></span>");
		toClient.println(" </button>");
		toClient.println(" <a class=\"navbar-brand\" href=\"#\">Beer Game</a>");
		toClient.println("</div>");
		toClient.println("<div id=\"navbar\" class=\"collapse navbar-collapse\">");
		toClient.println("<ul class=\"nav navbar-nav\">");
		toClient.println("<li class=\"highlight\"><a href=\"menuYara.html\">Home</a></li>");
		toClient.println("<li><a href=\"loginYara.html\">Log in</a></li>");
		toClient.println("<li><a href=\"MakeComment.html\"> Make a comment</a></li>");
		toClient.println("<li class=\"dropdown\">");
		toClient.println("<a href=\"ShowCommentsDataBase\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\">Forum<span class=\"caret\"></span></a>");
		toClient.println("<ul class=\"dropdown-menu\">");
		toClient.println("<li><a href=\"#\">Action</a></li>");
		toClient.println("<li><a href=\"#\">Another action</a></li>");
		toClient.println(" <li><a href=\"#\">Something else here</a></li>");
		toClient.println("<li role=\"separator\" class=\"divider\"></li>");
		toClient.println("<li class=\"dropdown-header\">Nav header</li>");
		toClient.println("<li><a href=\"#\">Separated link</a></li>");
		toClient.println("<li><a href=\"#\">One more separated link</a></li>");
		toClient.println("</ul>");
		toClient.println("</li>");
		toClient.println(" </ul>");
		toClient.println("</div><!--/.nav-collapse -->");
		toClient.println("</div>");
		toClient.println("</nav>");
		
		toClient.println("<div class=\"beer\">");
		
		toClient.println("<B><FONT class=\"center\" size=+2>Your comment: </FONT></B>");
		toClient.println("<P><FONT size=+1><B>User: </B>" + username + "</FONT>");
		toClient.println("<BR><FONT size=+1><B>Comment: </B>" + req.getParameter("comment") + "</FONT>");
		toClient.println("</BODY>");
		toClient.println("<BR><a href=\"menuYara.html\">Ir al menu</a>");
		toClient.println("</div>");
		toClient.println("</HTML>");
		toClient.close();

  } 
    
 
}

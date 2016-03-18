import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class UpdateInventory extends HttpServlet {
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
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();
        toClient.println("<!DOCTYPE HTML>");
		toClient.println("<html class=\"full\">");
        toClient.println("<HEAD>");
		toClient.println("<TITLE> The Beer Game </TITLE>");
		
		toClient.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
		toClient.println("<link href=\"beerIcon.jpg\" rel=\"shortcut icon\">");
		toClient.println("<meta charset=\"utf-8\"></meta>");
		toClient.println("<meta content=\"IE=edge\" http-equiv=\"X-UA-Compatible\"></meta>");
		toClient.println("<meta content=\"width=device-width, initial-scale=1\" name=\"viewport\"></meta>");
		
		toClient.println("<link rel=\"stylesheet\" href=\"http://getbootstrap.com/dist/css/bootstrap.min.css\"></link>");
		toClient.println("<link rel=\"stylesheet\" href=\"http://getbootstrap.com/assets/css/ie10-viewport-bug-workaround.css\"></link>");
		toClient.println("<link rel=\"stylesheet\" href=\"beerDesign.css\">");
        toClient.println("<link href=\"http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css\" rel=\"stylesheet\">");
        toClient.println("<script src=\"http://code.jquery.com/jquery-1.10.2.js\"></script>");
        toClient.println("<script src=\"http://code.jquery.com/ui/1.10.4/jquery-ui.js\"></script>");
		
		toClient.println("</head>");
        toClient.println("<body>");
		
		toClient.println("<nav class=\"navbar navbar-default beer\">");
			  toClient.println("<div class=\"container\">");
				toClient.println("<div class=\"navbar-header\">");
				  toClient.println("<button type=\"button\" class=\"navbar-toggle collapsed\" data-toggle=\"collapse\" data-target=\"#navbar\" aria-expanded=\"false\" aria-controls=\"navbar\">");
					toClient.println("<span class=\"sr-only\">Toggle navigation</span>");
					toClient.println("<span class=\"icon-bar\"></span>");
					toClient.println("<span class=\"icon-bar\"></span>");
					toClient.println("<span class=\"icon-bar\"></span>");
				  toClient.println("</button>");
				  toClient.println("<a class=\"navbar-brand\" href=\"index.html\">Beer Game</a>");
				toClient.println("</div>");
				toClient.println("<div id=\"navbar\" class=\"collapse navbar-collapse\">");
				  toClient.println("<ul class=\"nav navbar-nav\">");
					toClient.println("<li ><a href=\"benef\">Benefits</a></li>");
					toClient.println("</li>");
					toClient.println("<li><a href=\"election.html\">Information</a></li>");
					toClient.println("<li><a href=\"ShowCommentsDataBase\">Forum</a></li>");
					toClient.println("<li class=\"redondeo\"><a href=\"NextDay\" >Next Day</a></li>");
				  toClient.println("</ul>");
				toClient.println("</div><!--/.nav-collapse -->");
			  toClient.println("</div>");
			toClient.println("</nav>");
		
		toClient.println("<div class=\"beer\">");
		
		toClient.println("<TR>"); 		
		toClient.println("<TD>");
        toClient.println("<img src=\"http://vignette1.wikia.nocookie.net/battlenations/images/c/c3/ResourceDepot_2_icon.png/revision/latest?cb=20121015035402\" style=\"margin-left: 575px;\"></img>");
		toClient.println("</TD>");
		toClient.println( "</TR>");
		
		String sql = "SELECT Inventory FROM Game WHERE  User_id='Alex' AND Day=(SELECT MAX(day)FROM Game)" ;
		String Inventory = req.getParameter("Inventory");
		System.out.println(sql);
		
		toClient.println("<div class=\"CSSTableGenerator\">");
		toClient.println("<TABLE>");
		
		toClient.println("<tr>");
		toClient.println("<td>Almacen actualizado</td>");
		toClient.println("</tr>");
		
		try {
            Statement statement=connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
			while (result.next()) {
				
                String InventoryStr = result.getString("Inventory");
				toClient.println("<tr>");
                toClient.println("<td style=\"width: 1700px;\">" + InventoryStr + "</td>");
                toClient.println("</tr>");
			}
        }catch (SQLException ex) {
			ex.printStackTrace();
            System.out.println("Resulset: " + sql + " Exception: " + ex);
        }
		
		toClient.println("</TABLE>");
		toClient.println("</div>");
		
		toClient.println("</div>");
		
        toClient.println("</body>");
        toClient.println("</html>");
        toClient.close();
    }
}
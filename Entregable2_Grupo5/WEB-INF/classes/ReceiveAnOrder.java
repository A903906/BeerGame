import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class ReceiveAnOrder extends HttpServlet {
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
	
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
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
		
        toClient.println("<script>");
        toClient.println("$(function() {");
        toClient.println("$('.draggable-4').draggable({ revert: true });");
        toClient.println("$('.droppable-7').droppable({");
        toClient.println("hoverClass: 'active',");
        toClient.println("drop: function(e, ui) {");
        toClient.println("$(this).html(ui.draggable.remove().html());");
        toClient.println("$(this).droppable('destroy');");
        toClient.println("$( this )");
        toClient.println(".addClass( \"ui-state-highlight\" )");
        toClient.println(".find( \"p\" )");
        toClient.println(".html( \"Inventario actualizado\" );");
		toClient.println("document.getElementById(\"myBtn\").disabled = false;");
        toClient.println("}");
        toClient.println("});");
        toClient.println("});");
        toClient.println("</script>");
		
		toClient.println("<div class=\"beer\"  style=\"height: 286px;\">");
		
		toClient.println("<div class=\"CSSTableGenerator\">");
		toClient.println("<TABLE>");
		
		toClient.println("<tr>");
		toClient.println("<td>Almacen</td>");
		
		
		
		toClient.println("<td>Actualizacion</td>");
		toClient.println("</tr>");
		
		toClient.println("<td class=\"draggable-4\" ondrop=\"drop(event)\" style=\"position: relative;left: 50px;top: 35px;padding-right: 0px;border-right-width: 0px;\" ></td>");
        toClient.println("<td class=\"droppable-7\" style=\"margin-left: 100px; margin-right: 0px;\" >Inventario</td>");
		
		toClient.println("<td>");
		toClient.println("<form method=\"get\" action=\"UpdateInventory\">");
		toClient.println("<input id=\"myBtn\"  type=\"submit\" value=\"Actualizar Inventario\" disabled >");
        toClient.println("</form>");
		toClient.println("</td>");
		
		toClient.println("</TABLE>");
		toClient.println("</div>");
		
		toClient.println("<div class=\"CSSTableGenerator\">");
		toClient.println("<TABLE>");
		
		toClient.println("<tr>");
		toClient.println("<td>Pedido recibido</td>");
		
		toClient.println("<td>Inventario</td>");
		toClient.println("</tr>");
		
		String sql = "Select Inventory FROM Game WHERE  User_id='Alex' AND Day=(select MAX(Day) FROM Game WHERE Day<(select MAX (Day) FROM Game) )";
		String sql2 = "Select OrdersReceived FROM Game WHERE  User_id='Alex' AND Day=(select MAX(Day) FROM Game WHERE Day<(select MAX (Day) FROM Game) )";
        String inventory = req.getParameter("Inventory");
		String OrdersReceived = req.getParameter("OrdersReceived");
		
		System.out.println(sql);
		System.out.println(sql2);
		
		try {
            Statement statement2=connection.createStatement();
            ResultSet result2 = statement2.executeQuery(sql2);
            while(result2.next()) {
                String OrdersReceivedStr = result2.getString("OrdersReceived");
				toClient.println("<td>" + OrdersReceivedStr + "</td>");
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Resulset: " + sql2 + " Exception: " + e);
        }
		
		 try {
            Statement statement=connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next()) {
                String inventoryStr = result.getString("inventory");
				toClient.println("<td>" + inventoryStr + "</td>");
				toClient.println("</tr>");
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Resulset: " + sql + " Exception: " + e);
        }
		
		
		
		toClient.println("</TABLE>");
		toClient.println("</div>");
		
		toClient.println("</div>");
		
        toClient.println("</body>");
        toClient.println("</html>");
        toClient.close();
    }
}
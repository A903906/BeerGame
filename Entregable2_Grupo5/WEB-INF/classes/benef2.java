import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class benef2 extends HttpServlet {
		Connection connection;
		
        static private int costeinventario;
		static private int costepedido;
		static private int costeenviado;
		static private int costs;
		static private int earning;
		static private int profit;
		
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String url="jdbc:odbc:BeerGame";
            connection=DriverManager.getConnection(url); 
				connection.setAutoCommit(false);
        } catch(Exception e) {
            e.printStackTrace();
        }
		System.out.println("Iniciando...");
	}
	public void destroy() {
    System.out.println("No hay nada que hacer...");
	} 
	
	public void doGet(HttpServletRequest req, HttpServletResponse response)throws ServletException, IOException {
	
	
		HttpSession session = req.getSession(true);
		
		String strRun= req.getParameter("Run");
		int intRun = Integer.parseInt(strRun);
		session.setAttribute("Run", intRun);   //intRun
		
		String strName= req.getParameter("Name");
		session.setAttribute("Name", strName);   //strName
		
		String strPName= req.getParameter("NId");
		session.setAttribute("NId", strPName);   //strPName
		
		
		
		//intPedido=nº uds pedidas
		
		int strpedido=0;
		String sqlpedido = "Select Ordered FROM Game WHERE Day="+intRun+" AND Group_Id= '"+strName+"' AND User_id = '"+strPName+"' ";
		try {
		Statement statement=connection.createStatement();
		System.out.println(sqlpedido);
		ResultSet result = statement.executeQuery(sqlpedido);
		while(result.next()) {
			strpedido=result.getInt("Ordered");
			System.out.println("resultado: "+strpedido);
			
	
		}
		} catch(SQLException e) {
		e.printStackTrace();
		} 
		//int intpedido = Integer.parseInt(strpedido);
		session.setAttribute("Ordered", strpedido);
		
		System.out.println("resultado2: ");
		//intInventario=Nº uds en el inventario
		String strInventario=null;
		String sqlinventario = "Select Inventory FROM Game WHERE Day="+intRun+" AND Group_Id= '"+strName+"' AND User_id = '"+strPName+"'";
			
		System.out.println("sqlinventario: " +sqlinventario);
		String Inventory= req.getParameter("Inventory");
		try {
		Statement statement=connection.createStatement();
		ResultSet result = statement.executeQuery(sqlinventario);
		while(result.next()) {
			strInventario=result.getString("Inventory");
		}
		} catch(SQLException e) {
		e.printStackTrace();
		}
		int intInventario = Integer.parseInt(strInventario);
		session.setAttribute("Inventory", intInventario);
		
		System.out.println("resultado3: ");
		//intenviado=Nº uds enviado
		String strEnviado=null;
		String sqlenviado = "Select OrdersServed FROM Game WHERE Day="+intRun+" AND Group_Id= '"+strName+"' AND User_id = '"+strPName+"'";
		String OrdersServed= req.getParameter("OrdersServed");
		try {
		Statement statement=connection.createStatement();
		ResultSet result = statement.executeQuery(sqlenviado);
		while(result.next()) {
			strEnviado=result.getString("OrdersServed");
		}
		} catch(SQLException e) {
		e.printStackTrace();
		}
		int intEnviado = Integer.parseInt(strEnviado);
		session.setAttribute("OrdersServed", intEnviado);
		
		System.out.println("resultado4: ");
		//intPrecioCompra= preciocompra por unidad
		String strPrecioCompra=null;
		String sqlPrecioCompra = "Select BeerPrice FROM Groups WHERE GroupName='"+strName+"'";
		String BeerPrice= req.getParameter("BeerPrice");
		try {
		Statement statement=connection.createStatement();
		ResultSet result = statement.executeQuery(sqlPrecioCompra);
		while(result.next()) {
			strPrecioCompra=result.getString("BeerPrice");
		}
		} catch(SQLException e) {
		e.printStackTrace();
		}
		int intPrecioCompra = Integer.parseInt(strPrecioCompra);
		session.setAttribute("BeerPrice", intPrecioCompra);
		
		System.out.println("resultado5: ");
		//intPrecioVenta= precioventa por unidad
		String strPrecioVenta=null;
		String sqlPrecioVenta = "Select SellPrice FROM Groups WHERE GroupName='"+strName+"'";
		String SellPrice= req.getParameter("SellPrice");
		try {
		Statement statement=connection.createStatement();
		ResultSet result = statement.executeQuery(sqlPrecioVenta);
		while(result.next()) {
			strPrecioVenta=result.getString("SellPrice");
		}
		} catch(SQLException e) {
		e.printStackTrace();
		}
		int intPrecioVenta = Integer.parseInt(strPrecioVenta);
		session.setAttribute("SellPrice", intPrecioVenta);
		
		System.out.println("resultado6: ");
		//intCosteInventario= costeinventario por unidad
		String strCosteInventario=null;
		String sqlCosteInventario = "Select InventoryCost FROM Groups WHERE GroupName='"+strName+"'";
		String InventoryCost= req.getParameter("InventoryCost");
		try {
		Statement statement=connection.createStatement();
		ResultSet result = statement.executeQuery(sqlCosteInventario);
		while(result.next()) {
			
			strCosteInventario=result.getString("InventoryCost");
		}
		} catch(SQLException e) {
		e.printStackTrace();
		}
		int intCosteInventario = Integer.parseInt(strCosteInventario);
		session.setAttribute("InventoryCost", intCosteInventario);
		
	
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.println("<html>");
		out.println("<head>");
		
		out.println("<meta charset=\"utf-8\">");
		out.println("<title>jQuery UI Dialog - Animation</title>");
		out.println("<link rel=\"stylesheet\" href=\"http://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css\">");
		out.println("<script src=\"http://code.jquery.com/jquery-1.10.2.js\"></script>");
		out.println("<script src=\"http://code.jquery.com/ui/1.11.4/jquery-ui.js\"></script>");
		out.println("<link rel=\"stylesheet\" href=\"/resources/demos/style.css\">");
		out.println("<script>");
		out.println("$(function() {");
		out.println("$( \"#dialog\" ).dialog({");
		out.println("autoOpen: false,");
		out.println("show: {");
		out.println("effect: \"blind\",");
		out.println("duration: 1000");
		out.println("},");
		out.println("hide: {");
		out.println("effect: \"explode\",");
		out.println("duration: 1000");
		out.println(" }");
		out.println("});");
	 
		out.println("$( \"#opener\" ).click(function() {");
		out.println("$( \"#dialog\" ).dialog( \"open\" );");
		out.println("});");
		out.println("});");
		out.println("</script>");
		
		out.println("<title>Counts</title>");
		out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
		out.println("<link href=\"beerIcon.jpg\" rel=\"shortcut icon\">");
		out.println("<meta charset=\"utf-8\"></meta>");
		out.println("<meta content=\"IE=edge\" http-equiv=\"X-UA-Compatible\"></meta>");
		out.println("<meta content=\"width=device-width, initial-scale=1\" name=\"viewport\"></meta>");
		
		out.println("<link rel=\"stylesheet\" href=\"http://getbootstrap.com/dist/css/bootstrap.min.css\"></link>");
		out.println("<link rel=\"stylesheet\" href=\"http://getbootstrap.com/assets/css/ie10-viewport-bug-workaround.css\"></link>");
		out.println("<link rel=\"stylesheet\" href=\"beerDesign.css\">");
		out.println("</head>");
		out.println("<body bgcolor=\"#FFFFF0\" text=\"#FFCC00\">");
		out.println("<nav class=\"navbar navbar-default beer\">");
		out.println(" <div class=\"container\">");
		out.println("<div class=\"navbar-header\">");
		out.println("<button type=\"button\" class=\"navbar-toggle collapsed\" data-toggle=\"collapse\" data-target=\"#navbar\" aria-expanded=\"false\" aria-controls=\"navbar\">");
		out.println("<span class=\"sr-only\">Toggle navigation</span>");
		out.println("<span class=\"icon-bar\"></span>");
		out.println("<span class=\"icon-bar\"></span>");
		out.println("<span class=\"icon-bar\"></span>");
		out.println(" </button>");
		out.println(" <a class=\"navbar-brand\" href=\"#\">Beer Game</a>");
		out.println("</div>");
		out.println("<div id=\"navbar\" class=\"collapse navbar-collapse\">");
		out.println("<ul class=\"nav navbar-nav\">");
		out.println("<li class=\"highlight\"><a href=\"#\">Home</a></li>");
		out.println("<li><a href=\"#about\">About</a></li>");
		out.println("<li><a href=\"#contact\">Contact</a></li>");
		out.println("<li class=\"dropdown\">");
		out.println("<a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\">Options <span class=\"caret\"></span></a>");
		out.println("<ul class=\"dropdown-menu\">");
		out.println("<li><a href=\"#\">Action</a></li>");
		out.println("<li><a href=\"#\">Another action</a></li>");
		out.println(" <li><a href=\"#\">Something else here</a></li>");
		out.println("<li role=\"separator\" class=\"divider\"></li>");
		out.println("<li class=\"dropdown-header\">Nav header</li>");
		out.println("<li><a href=\"#\">Separated link</a></li>");
		out.println("<li><a href=\"#\">One more separated link</a></li>");
		out.println("</ul>");
		out.println("</li>");
		out.println(" </ul>");
		out.println("</div><!--/.nav-collapse -->");
		out.println("</div>");
		out.println("</nav>");
		
		out.println("<div class=\"beer\">");
		
		out.println("<p align=\"center\"><font size=\"6\"><b>COUNTS</b></font></p>");
		
		costeinventario=intInventario*intCosteInventario ;
		costepedido=strpedido*intPrecioCompra ;
		costeenviado=intEnviado*intPrecioVenta ;
		
		costs= costeinventario+costepedido;
		earning= costeenviado;
		profit=earning-costs;
		
		out.println("<p align=\"center\"><font size=\"3\"><FONT COLOR=\"black\"><b>Earnings  </b>"   +   earning + "</font><FONT COLOR=\"black\"><b>&euro;</b></p>");
		out.println("<p align=\"center\"><font size=\"3\"><FONT COLOR=\"black\"><b>Costs  </b>"   +   costs + "</font><FONT COLOR=\"black\"><b>&euro;</b></p>");
		out.println("<p align=\"center\"><font size=\"3\"><FONT COLOR=\"black\"><b>__________________________________________________________</b></font><FONT COLOR=\"black\"></p>");
		out.println("<p align=\"center\"><font size=\"3\"><FONT COLOR=\"black\"><b>Profits  </b>"   +   profit + "</font><FONT COLOR=\"black\"><b>&euro;</b></p>");
		
		
		out.println("<form>");
		out.println("<div align=\"center\">");
		out.println("<p></p>");
		out.println("<p></p>");
		
		

		out.println("<p></p>");
		out.println("<p></p>");
		out.println("<input type=\"button\" value=\"VOLVER\" name=\"Back2\" onclick=\"history.back()\" />");
		out.println("</div>");
		out.println("</form>");
		out.println("</div>");
		out.println("<div id=\"dialog\" title=\"Basic dialog\">");
		
		double cervezas= Math.ceil(profit/intPrecioCompra);
		if (profit>0){
		out.println("<p>The optimun order would be "+cervezas+" more than you asked for </p>");
		}
		if (profit<0){
		out.println("<p>The optimun order would be "+cervezas+"- beers less than you asked for </p>");
		}
		if (profit==0){
		out.println("<p>The optimun order was the one you made</p>");
		}
		out.println("</div>");
		out.println("<p align=\"center\">");
		out.println("<button id=\"opener\">Optimum Order</button>");
		out.println("</body>");
		out.println("</html>");
           
		
	}
}
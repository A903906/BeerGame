import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;


public class Graph extends HttpServlet {

	Connection connection;

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
	
    public void doGet (HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
  
		HttpSession session = request.getSession(true);
		PrintWriter out = resp.getWriter();
		
		String strGroup = request.getParameter("Group");
		session.setAttribute("Group", strGroup);
	
		out.println("<HTML>");
		out.println("<head>");
		out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
		out.println("<link href=\"beerIcon.jpg\" rel=\"shortcut icon\">");
		out.println("<meta charset=\"utf-8\"></meta>");
		out.println("<meta content=\"IE=edge\" http-equiv=\"X-UA-Compatible\"></meta>");
		out.println("<meta content=\"width=device-width, initial-scale=1\" name=\"viewport\"></meta>");
		
		out.println("<link rel=\"stylesheet\" href=\"http://getbootstrap.com/dist/css/bootstrap.min.css\"></link>");
		out.println("<link rel=\"stylesheet\" href=\"http://getbootstrap.com/assets/css/ie10-viewport-bug-workaround.css\"></link>");
		out.println("<link rel=\"stylesheet\" href=\"beerDesign.css\">");
		
		String strday=null;
		String sqlday = "Select NumberDays FROM Groups WHERE GroupName='"+strGroup+"'";

		try {
		Statement statement=connection.createStatement();
		ResultSet result = statement.executeQuery(sqlday);
		while(result.next()) {
			strday=result.getString("NumberDays");
	
		}
		} catch(SQLException e) {
		e.printStackTrace();
		}

		int intday = Integer.parseInt(strday);
		
	

		out.println("<script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>");
		out.println("<script type=\"text/javascript\">");
		  
		out.println("google.charts.load('current', {'packages':['line']})");
		out.println("google.charts.setOnLoadCallback(drawChart)");

		out.println(" function drawChart() { ");
		
		out.println("var data = new google.visualization.DataTable()");
		out.println("data.addColumn('number', 'DAY')");
		out.println("data.addColumn('number', 'Distributor')");
		out.println("data.addColumn('number', 'Retailer')");
		out.println("data.addColumn('number', 'Factory')");
		out.println("data.addColumn('number', 'Wholesaler')");
		
	for (int i=1; i<=intday; i++){;
		
		String strwholesaler=null;
		String sqlwholesaler = "Select Ordered FROM Game WHERE Group_id='"+strGroup+"' AND Day="+i+" AND User_id IN (SELECT User_id FROM UsersRoles WHERE Role_id='Wholesaler' AND Group_id='"+strGroup+"')";
		
		try {
			Statement statement=connection.createStatement();
			ResultSet result = statement.executeQuery(sqlwholesaler);
			while(result.next()) {;
			strwholesaler=result.getString("Ordered");
	
		}
		} catch(SQLException e) {
		e.printStackTrace();
		}

		int intwholesaler = Integer.parseInt(strwholesaler);



		String strfactory=null;
		String sqlfactory = "Select Ordered FROM Game WHERE Group_id='"+strGroup+"' AND Day="+i+" AND User_id IN (SELECT User_id FROM UsersRoles WHERE Role_id='factory' AND Group_id='"+strGroup+"')";
		String Factory=request.getParameter("Factory");
		try {
			Statement statement=connection.createStatement();
			ResultSet result = statement.executeQuery(sqlfactory);
			while(result.next()) {
			strfactory=result.getString("Ordered");
	
		}
		} catch(SQLException e) {
		e.printStackTrace();
		}
		
		int intfactory = Integer.parseInt(strfactory);



		String strretailer=null;
		String sqlretailer = "Select Ordered FROM Game WHERE Group_id='"+strGroup+"' AND Day="+i+" AND User_id IN (SELECT User_id FROM UsersRoles WHERE Role_id='retailer' AND Group_id='"+strGroup+"')";

		try {
			Statement statement=connection.createStatement();
			ResultSet result = statement.executeQuery(sqlretailer);
			while(result.next()) {
			strretailer=result.getString("Ordered");
	
		}
		} catch(SQLException e) {;
		e.printStackTrace();
		}

		int intretailer = Integer.parseInt(strretailer);



		String strdistributor=null;
		String sqldistributor = "Select Ordered FROM Game WHERE Group_id='"+strGroup+"' AND Day="+i+" AND User_id IN (SELECT User_id FROM UsersRoles WHERE Role_id='distributor' AND Group_id='"+strGroup+"')";

		try {
			Statement statement=connection.createStatement();
			ResultSet result = statement.executeQuery(sqldistributor);
			while(result.next()) {
			strdistributor=result.getString("Ordered");
	
		}
		} catch(SQLException e) {
		e.printStackTrace();
		}

		int intdistributor = Integer.parseInt(strdistributor);
	
					
		out.println("data.addRows([["+i+",  "+intdistributor+", "+intretailer+", "+intfactory+", "+intwholesaler+"],])");
		
	}
		
		out.println("var options = {");
		out.println("chart: {");
		out.println("title: 'Orders during the game',");
		out.println("subtitle: 'in number of beers'");
		out.println("},");
		out.println("width: 900,");
		out.println("height: 500");
		out.println("}");
		out.println("var chart = new google.charts.Line(document.getElementById('linechart_material'))");
		out.println("chart.draw(data, google.charts.Line.convertOptions(options))");
		out.println("}");
		
		out.println("</script>");
		
		
		out.println("</head>");
		out.println("<body>");
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
		
		out.println("<div id=\"linechart_material\" style=\"width: 1024px; margin: auto;  height: 500px\"></div>");
		
		out.println("</div>");
		out.println("</body>");
	  
		out.println("</html>");

	}
}
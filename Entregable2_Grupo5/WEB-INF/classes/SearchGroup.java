import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class SearchGroup extends HttpServlet {
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

  public void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter toClient = resp.getWriter();
	
	toClient.println("<HTML>");
	toClient.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
	toClient.println("<link href=\"beerIcon.jpg\" rel=\"shortcut icon\">");
    toClient.println("<title>Beer Game</title>");
	toClient.println("<meta charset=\"utf-8\"></meta>");
	toClient.println("<meta content=\"IE=edge\" http-equiv=\"X-UA-Compatible\"></meta>");
	toClient.println("<meta content=\"width=device-width, initial-scale=1\" name=\"viewport\"></meta>");
	toClient.println("<meta content=\"\" name=\"description\"></meta>");
	toClient.println("<meta content=\"\" name=\"author\"></meta>");
	toClient.println("<link rel=\"stylesheet\" href=\"http://getbootstrap.com/dist/css/bootstrap.min.css\"></link>");
	toClient.println("<link rel=\"stylesheet\" href=\"http://getbootstrap.com/assets/css/ie10-viewport-bug-workaround.css\"></link>");	
	toClient.println("<link rel=\"stylesheet\" href=\"beerDesign1.css\">");
	toClient.println("<HEAD>");
	toClient.println("<TITLE>Search Group</TITLE>");
	toClient.println("</HEAD>");
	toClient.println("<BODY>");
	toClient.println("<script>");
	toClient.println("function RadioButton(){");
		toClient.println("document.getElementById(\"select\").disabled = false;");
	toClient.println("}");
        toClient.println("</script>");
	toClient.println("<nav class=\"navbar navbar-default beer\">");
			  toClient.println("<div class=\"container\">");
				toClient.println("<div class=\"navbar-header\">");
				  toClient.println("<button type=\"button\" class=\"navbar-toggle collapsed\" data-toggle=\"collapse\" data-target=\"#navbar\" aria-expanded=\"false\" aria-controls=\"navbar\">");
					toClient.println("<span class=\"sr-only\">Toggle navigation</span>");
					toClient.println("<span class=\"icon-bar\"></span>");
					toClient.println("<span class=\"icon-bar\"></span>");
					toClient.println("<span class=\"icon-bar\"></span>");
				  toClient.println("</button>");
				  toClient.println("<a class=\"navbar-brand\">Beer Game</a>");
				toClient.println("</div>");
				toClient.println("<div id=\"navbar\" class=\"collapse navbar-collapse\">");
				  toClient.println("<ul class=\"nav navbar-nav\">");
					toClient.println("<li class=\"dropdown\">");
					toClient.println("<li><a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\">Home<span class=\"caret\"></span></a>");
					toClient.println("<ul class=\"dropdown-menu\">");
					toClient.println("<li><a href=\"Election.html\">First Page</a></li>");
					toClient.println("<li><a href=\"#\">Log Off</a></li>");
					toClient.println("</ul>");
					toClient.println("</li>");
					toClient.println("</li>");					toClient.println("<li class=\"dropdown\">");
					toClient.println("<li><a class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\">Groups Information<span class=\"caret\"></span></a>");
					toClient.println("<ul class=\"dropdown-menu\">");
					toClient.println("<li><a href=\"RegisterGroup.html\">Register Your Group</a></li>");
					toClient.println("<li><a href=\"AllGroups\">See All Groups</a></li>");
					toClient.println("</ul>");
					toClient.println("</li>");
					toClient.println("</li>");
					toClient.println("<li><a href=\"SearchGroup.html\">Search Group</a></li>");
					toClient.println("<li><a href=\"ChangeGroupInformation.html\" >Change Group Information</a></li>");
				  toClient.println("</ul>");
				toClient.println("</div><!--/.nav-collapse -->");
			  toClient.println("</div>");
			toClient.println("</nav>");
			toClient.println("<div class=\"beer\">");
			toClient.println("<H1 ALIGN=\"center\">YOUR SEARCH GROUP:</H1>");

	boolean sessionBool = false;
        HttpSession session = req.getSession(false);
        if (session != null) {
            sessionBool = true;
        }
	
        if (sessionBool) {
            String strSearchGroup = (String)session.getAttribute("SearchName");
            if (strSearchGroup != null) {
                toClient.println("<h2>User: " + strSearchGroup + "</h2>");
            }
            toClient.print("<form action=\"EleccionRol\" method=GET>");
        }
	
	toClient.println("<div class=\"CSSTableGenerator\">");
	toClient.println("<FORM action=\"EleccionRol\" method=\"GET\">");
	toClient.println("<TABLE style=\" width: 300px; height: 200px; margin-left: 650px;\">");
	toClient.println("<TR>");
	toClient.println("<TD>");
	toClient.println("Check");
	toClient.println("</TD>");
	toClient.println("<TD>");
	toClient.println("GroupName");
	toClient.println("</TD>");
	toClient.println("<TD>");
	toClient.println("Creator Name");
	toClient.println("</TD>");
	toClient.println("<TD>");
	toClient.println("Number of Days");
	toClient.println("</TD>");
	toClient.println("</TR>");	

   String sql = "SELECT GroupName, CreatorName, NumberDays FROM Groups WHERE GroupName=";
		sql +=  "'" + req.getParameter("SearchName") + "'";
        String GroupName = req.getParameter("GroupName");
        if (GroupName != null) {
            sql += " WHERE GroupName = '" + GroupName + "'";
        }
		System.out.println(sql);
	try {
        Statement statement=connection.createStatement();
        ResultSet result = statement.executeQuery(sql);	
		while (result.next()) {
			toClient.println("<tr>");
			String codeStr = result.getString("GroupName");
			/*if (sessionBool){
				toClient.println("<td><input type=\"radio\" name=\"Group" +
                        "\" value=\"" + codeStr + "\" onclick=\"RadioButton()\"></td>");
			}*/
			toClient.println("<td><input type=\"radio\" onclick=\"RadioButton()\"></td>");
                toClient.println("<td>" + codeStr + "</td>");
				toClient.println("<td>" + result.getString("CreatorName") + "</td>");
                toClient.println("<td>" + result.getString("NumberDays") + "</td>");
                toClient.println("</tr>");
		}	
	}catch (SQLException ex) {
		ex.printStackTrace();
		System.out.println("Resulset: " + sql + " Exception: " + ex);
    }
	toClient.println("</TABLE>");
	toClient.println("</div>");
	toClient.println("<p ALIGN=\"center\"><INPUT TYPE=\"submit\" VALUE=\"SELECT\" ID=\"select\" disabled></p>");
	toClient.println("</FORM>");
    toClient.println("<footer class=\"footer\">");
     toClient.println("<div class=\"container\">");
       toClient.println(" <p class=\"text-muted\">Impressum FAQ Contacts</p>");
    toClient.println("</div>");
	toClient.println("</footer>");
	toClient.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js\"></script>");
	toClient.println("<script>");
    toClient.println("window.jQuery || document.write('<script src=\"../.…\"");
	toClient.println("</script>");
	toClient.println("<script src=\"http://getbootstrap.com/dist/js/bootstrap.min.js\"></script>");
	toClient.println("<script src=\"http://getbootstrap.com/assets/js/ie10-viewport-bug-workaround.js\"></script>");
    toClient.println("</BODY>");
    toClient.println("</HTML>");

    toClient.close();  
}
}
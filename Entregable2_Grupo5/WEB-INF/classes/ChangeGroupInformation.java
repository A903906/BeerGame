import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class ChangeGroupInformation extends HttpServlet {
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
	toClient.println("<TITLE>Your Group</TITLE>");
	toClient.println("</HEAD>");
	toClient.println("<BODY>");
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
					toClient.println("</li>");
					toClient.println("<li class=\"dropdown\">");
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
			toClient.println("<H1 ALIGN=\"center\">YOUR GROUP INFORMATION</H1>");

	boolean sessionBool = false;
        HttpSession session = req.getSession(false);
        if (session != null) {
            sessionBool = true;
        }
	
        if (sessionBool) {
            String strSearchGroup = (String)session.getAttribute("SearchName");
			String strSearchPassword = (String)session.getAttribute("SearchPassword");
            if (strSearchGroup != null) {
                toClient.println("<h2>User: " + strSearchGroup + "</h2>");
            }
        }
	toClient.println("<div class=\"CSSTableGenerator\">");
	toClient.println("<TABLE style=\" width: 400px; height: 100px; margin-left: 610px;\">");
	toClient.println("<TR>");
	toClient.println("<TD>");
	toClient.println("GroupName");
	toClient.println("</TD>");
	toClient.println("<TD>");
	toClient.println("Creator Name");
	toClient.println("</TD>");
	toClient.println("<TD>");
	toClient.println("Group Password");
	toClient.println("</TD>");
	toClient.println("<TD>");
	toClient.println("Number of Days");
	toClient.println("</TD>");
	toClient.println("</TR>");	

   String sql = "SELECT GroupName, CreatorName, GroupPassword, NumberDays FROM Groups WHERE GroupName=";
		sql +=  "'" + req.getParameter("SearchName") + "' AND ";
		sql +=  "GroupPassword='" + req.getParameter("SearchPassword") + "'";
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
                        "\" value=\"" + codeStr + "\"></td>");
			}*/
                toClient.println("<td>" + codeStr + "</td>");
				toClient.println("<td>" + result.getString("CreatorName") + "</td>");
				toClient.println("<td>" + result.getString("GroupPassword") + "</td>");
                toClient.println("<td>" + result.getString("NumberDays") + "</td>");
                toClient.println("</tr>");
				toClient.println("</TABLE>");
				toClient.println("</div>");
				toClient.println("<FORM ACTION=\"NewGroupInformation\" METHOD=\"get\">");
				toClient.println("<TABLE style=\"margin-left: 725px;\">");
				toClient.println("<TR>");
				toClient.println("<TD><br>");
				toClient.println("</TD>");
				toClient.println("</TR>");
                toClient.println("<TR>");
                toClient.println("<TD ALIGN=\"center\" VALIGN=\"up\"><B> Group Name </B></TD>");
				toClient.println("</TR>");
				toClient.println("<TR>");
				toClient.println("<TD>");
                toClient.println("<p ALIGN=\"center\"><input type=\"text\" name=\"OldGroupName\"></input></p>");
				toClient.println("</TD>");
				toClient.println("</TR>");
				toClient.println("<TR>");
				toClient.println("<TD><br>");
				toClient.println("</TD>");
				toClient.println("</TR>");
				toClient.println("<TR>");
                toClient.println("<TD ALIGN=\"center\" VALIGN=\"up\"><B> Group New Name </B></TD>");
				toClient.println("</TR>");
				toClient.println("<TR>");
				toClient.println("<TD>");
				toClient.println("<p ALIGN=\"center\"><input type=\"text\" name=\"NewGroupName\"></input></p>");
				toClient.println("</TD>");
                toClient.println("</TR>");
				toClient.println("<TR>");
				toClient.println("<TD><br>");
				toClient.println("</TD>");
				toClient.println("</TR>");
                toClient.println("<TR>");
                toClient.println("<TD ALIGN=\"center\" VALIGN=\"up\"><B>Group Password</B></TD>");
				toClient.println("</TR>");
				toClient.println("<TR>");
				toClient.println("<TD>");
                toClient.println("<p ALIGN=\"center\"><input type=\"password\" name=\"OldGroupPassword\"></input></p>");
				toClient.println("</TD>");
				toClient.println("</TR>");
				toClient.println("<TR>");
				toClient.println("<TD><br>");
				toClient.println("</TD>");
				toClient.println("</TR>");
                toClient.println("<TR>");
                toClient.println("<TD ALIGN=\"center\" VALIGN=\"up\"><B>New Group Password</B></TD>");
				toClient.println("</TR>");
				toClient.println("<TR>");
				toClient.println("<TD>");
                toClient.println("<p ALIGN=\"center\"><input type=\"password\" name=\"NewGroupPassword\"></input></p>");
                toClient.println("</TD>");
				toClient.println("</TR>");
				toClient.println("<TR>");
				toClient.println("<TD><br>");
				toClient.println("</TD>");
				toClient.println("</TR>");
                toClient.println("<TR>");
                toClient.println("<TD ALIGN=\"center\" VALIGN=\"up\"><B>Change the Number of Days </B></TD>");
				toClient.println("</TR>");
				toClient.println("<TR>");
				toClient.println("<TD>");
                toClient.println("<p ALIGN=\"center\"><input type=\"text\" name=\"NewDays\"></input></p>");
				toClient.println("</TD>");
                toClient.println("</TR>");
				toClient.println("<TR>");
				toClient.println("<TD><br>");
				toClient.println("</TD>");
				toClient.println("</TR>");
				toClient.println("</TABLE>");
				toClient.println("<p align=\"center\"><input type=\"submit\" VALUE=\"Change\"></p>");
				toClient.println("</FORM>");

		}	
	}catch (SQLException ex) {
		ex.printStackTrace();
		System.out.println("Resulset: " + sql + " Exception: " + ex);
    }
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
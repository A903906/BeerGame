import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class RegisterGroups extends HttpServlet {
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

        String GroupName = req.getParameter("GroupName");
        String CreatorUserName = req.getParameter("CreatorUserName");
        String GroupPassword = req.getParameter("GroupPassword");
        String NumberDays = req.getParameter("NumberDays");

        String sqlSelect = "SELECT GroupName, CreatorName, GroupPassword, NumberDays FROM Groups WHERE GroupName = '" +
            GroupName + "'";
        System.out.println("Select sql: " + sqlSelect);
        String sql = "INSERT INTO Groups (GroupName, CreatorName, GroupPassword, NumberDays ) VALUES (";
        sql +=  "'" + GroupName + "'";
        sql +=  ", '" + CreatorUserName + "'";
        sql +=  ", '" + GroupPassword + "'";
        sql +=  ", '" + NumberDays + "')";
        System.out.println("Insert sql: " + sql);

        try{
            Statement statementSelect=connection.createStatement();
            ResultSet result = statementSelect.executeQuery(sqlSelect);
            boolean exist = false;
            while(result.next()) {
                exist = true;
                output(res, "User already exist");
                //System.out.println(result.getString("NumberDays") + " " + result.getString("GroupPassword"));
            }
            if (!exist) {
                Statement statementInsert=connection.createStatement();
                int num = statementInsert.executeUpdate(sql);
                output(res, "Inserted " + num + " register");
            }

        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Exception in register: " + e);
        }
    }

    public void output(HttpServletResponse res, String message) throws IOException {
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();
        toClient.println("<!DOCTYPE HTML>");
        toClient.println("<html>");
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
        toClient.println("<head><title>Register</title></head>");
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
			toClient.println("<H1 ALIGN=\"center\">ALL YOUR GROUPS ARE:</H1>");
        toClient.println("<h2>Register</h2>");
        toClient.println(message);
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
        toClient.println("</body>");
        toClient.println("</html>");
        toClient.close();
    }
}

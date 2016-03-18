import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class NewGroupInformation extends HttpServlet {
    Connection connection;
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String url="jdbc:odbc:BeerGame";
            connection=DriverManager.getConnection(url); 
        } catch(Exception e) {
            System.out.println("Problem creating connection");
            e.printStackTrace();
        }
    }

    public void destroy () {
        super.destroy();
        System.out.print("Closing connection ...");
        try {
            connection.close();
            System.out.println("Connection closed");
        } catch(SQLException ex){
            System.out.println("Problem closing connection");
            System.out.println(ex.getMessage());
        }
    }

    public void doGet (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html");
		PrintWriter toClient = res.getWriter();
		
       
		String OldName = req.getParameter("OldGroupName");
		String OldPassword = req.getParameter("OldGroupPassword");
        String NewName = req.getParameter("NewGroupName");
        String NewPassword = req.getParameter("NewGroupPassword");
		String NewDays = req.getParameter("NewDays");

        if(OldPassword==null) {
            System.out.println("Problem reading old password from request");
            return;
        }
        if(NewName==null) {
          System.out.println("Problem reading new password from request");
          return;
        }
        Statement stmt = null;
        try {
            stmt=connection.createStatement();
            ResultSet rs = null;
            String sql = "SELECT GroupName, GroupPassword FROM Groups WHERE GroupName = '" + OldName + "' AND GroupPassword= '" + OldPassword + "'";
            System.out.println(sql);
            rs = stmt.executeQuery(sql);

            if (rs.next() ) {
                String GroupName = rs.getString("GroupName");
				String GroupPassword = rs.getString("GroupPassword");
                System.out.println("password: " + OldName);
				 System.out.println("password: " + OldPassword);
                if (!GroupName.equals(OldName) && !GroupPassword.equals(OldPassword)) {
                    Error_login(req, res);
                    return;
                } else {
                    Statement stmt2 = null;
                    stmt2=connection.createStatement();
                    String sqlUpdate = "UPDATE Groups SET GroupName='" + NewName + "' , GroupPassword='" + NewPassword + "' , NumberDays='" + NewDays + "' WHERE GroupName='" + OldName + "' AND GroupPassword='" + OldPassword + "'";
                    System.out.println(sqlUpdate);
                    int registers = stmt2.executeUpdate(sqlUpdate);
					 res.setContentType("text/html");
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
						toClient.println("<h2>Update</h2>");
						toClient.println("Updated " + registers + " registers.");
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
                    return;
                }
            } else {
                //System.out.println("No registers for this id: ");
				 res.setContentType("text/html");
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
						toClient.println("<h2>Update</h2>");
						toClient.println("The Information Is Incorrect");
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
        } catch (SQLException sql) {
            System.out.println("Error creating Statement");
            System.out.println(sql.getMessage());
            return;
        } finally {      
            if(stmt!=null) {
                try {
                    stmt.close();
                } catch(SQLException e) {
                    System.out.println("Error closing Statement");
                    System.out.println(e.getMessage());
                    return;
                }
            }
        } 
    } 

    public void Error_login(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/html");
        PrintWriter out=null;
        try {
            out=resp.getWriter();
        } catch (IOException io) {
            System.out.println("Error opening PrintWriter");
        }
        out.println("<!DOCTYPE HTML>");
        out.println("<html>");
		out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
	out.println("<link href=\"beerIcon.jpg\" rel=\"shortcut icon\">");
   out.println("<title>Beer Game</title>");
	out.println("<meta charset=\"utf-8\"></meta>");
	out.println("<meta content=\"IE=edge\" http-equiv=\"X-UA-Compatible\"></meta>");
	out.println("<meta content=\"width=device-width, initial-scale=1\" name=\"viewport\"></meta>");
	out.println("<meta content=\"\" name=\"description\"></meta>");
	out.println("<meta content=\"\" name=\"author\"></meta>");
	out.println("<link rel=\"stylesheet\" href=\"http://getbootstrap.com/dist/css/bootstrap.min.css\"></link>");
	out.println("<link rel=\"stylesheet\" href=\"http://getbootstrap.com/assets/css/ie10-viewport-bug-workaround.css\"></link>");	
	out.println("<link rel=\"stylesheet\" href=\"beerDesign1.css\">");
        out.println("<head>");
        out.println("<title>Error in change password</title>");
        out.println("</head>");
        out.println("<body>");
		out.println("<nav class=\"navbar navbar-default beer\">");
			  out.println("<div class=\"container\">");
				out.println("<div class=\"navbar-header\">");
				  out.println("<button type=\"button\" class=\"navbar-toggle collapsed\" data-toggle=\"collapse\" data-target=\"#navbar\" aria-expanded=\"false\" aria-controls=\"navbar\">");
					out.println("<span class=\"sr-only\">Toggle navigation</span>");
					out.println("<span class=\"icon-bar\"></span>");
					out.println("<span class=\"icon-bar\"></span>");
					out.println("<span class=\"icon-bar\"></span>");
				  out.println("</button>");
				  out.println("<a class=\"navbar-brand\">Beer Game</a>");
				out.println("</div>");
				out.println("<div id=\"navbar\" class=\"collapse navbar-collapse\">");
				  out.println("<ul class=\"nav navbar-nav\">");
					 out.println("<li class=\"dropdown\">");
					 out.println("<li><a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\">Home<span class=\"caret\"></span></a>");
					 out.println("<ul class=\"dropdown-menu\">");
					 out.println("<li><a href=\"Election.html\">First Page</a></li>");
					 out.println("<li><a href=\"#\">Log Off</a></li>");
					 out.println("</ul>");
					 out.println("</li>");
					 out.println("</li>");
					out.println("<li class=\"dropdown\">");
					out.println("<li><a class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\">Groups Information<span class=\"caret\"></span></a>");
					out.println("<ul class=\"dropdown-menu\">");
					out.println("<li><a href=\"RegisterGroup.html\">Register Your Group</a></li>");
					out.println("<li><a href=\"AllGroups\">See All Groups</a></li>");
					out.println("</ul>");
					out.println("</li>");
					out.println("</li>");
					out.println("<li><a href=\"SearchGroup.html\">Search Group</a></li>");
					out.println("<li><a href=\"ChangeGroupInformation.html\" >Change Group Information</a></li>");
				  out.println("</ul>");
				out.println("</div><!--/.nav-collapse -->");
			  out.println("</div>");
			out.println("</nav>");
        out.println("<BR>");
        out.println("<H2 align=\"center\">Old password incorrect</H2>");
		out.println("<footer class=\"footer\">");
     out.println("<div class=\"container\">");
       out.println(" <p class=\"text-muted\">Impressum FAQ Contacts</p>");
    out.println("</div>");
	out.println("</footer>");
	out.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js\"></script>");
	out.println("<script>");
    out.println("window.jQuery || document.write('<script src=\"../.…\"");
	out.println("</script>");
	out.println("<script src=\"http://getbootstrap.com/dist/js/bootstrap.min.js\"></script>");
	out.println("<script src=\"http://getbootstrap.com/assets/js/ie10-viewport-bug-workaround.js\"></script>");
        out.println("</body>");
        out.println("</html>");
        out.flush();
        out.close();
    }

}

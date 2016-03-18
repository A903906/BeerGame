import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class ShowCommentsDataBase extends HttpServlet {
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
        String usernameStr = req.getParameter("user");
        String id = null;
        //if (usernameStr == null) {
          //  HttpSession session = req.getSession(false);
            //if (session != null) {
              //  id = (String)session.getAttribute("id");
                //usernameStr = id;
           // }
            //if (session == null || id == null) {
              //  res.getWriter().println("<h1> You must register to see opinions </h1>" +
                //  "<a href='index.html'>Home</a>");
                //return;
            //}
        //}

        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();
        toClient.println("<!DOCTYPE HTML>");
        toClient.println("<html class=\"full\">");
        toClient.println("<HEAD>");
		toClient.println("<TITLE> Forum </TITLE>");
		
		toClient.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
		toClient.println("<link href=\"beerIcon.jpg\" rel=\"shortcut icon\">");
		toClient.println("<meta charset=\"utf-8\"></meta>");
		toClient.println("<meta content=\"IE=edge\" http-equiv=\"X-UA-Compatible\"></meta>");
		toClient.println("<meta content=\"width=device-width, initial-scale=1\" name=\"viewport\"></meta>");
		
		toClient.println("<link rel=\"stylesheet\" href=\"http://getbootstrap.com/dist/css/bootstrap.min.css\"></link>");
		toClient.println("<link rel=\"stylesheet\" href=\"http://getbootstrap.com/assets/css/ie10-viewport-bug-workaround.css\"></link>");
		toClient.println("<link rel=\"stylesheet\" href=\"beerDesign.css\">");
		toClient.println("<script type=\"text/javascript\" src=\"BeerGameYara.js\">");
		
		//toClient.println("alert (\"We can not control what you write in the forum so please be polite and do not offend other people.\")");
		toClient.println("</script>");
		toClient.println("</HEAD>");
		toClient.println("<BODY onload=\"javascript:alert()\">");
	
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
		toClient.println("<li><a href=\"loginYara.html\">Log In</a></li>");
		toClient.println("<li><a href=\"MakeComment.html\">Make a comment</a></li>");
		toClient.println("<li class=\"dropdown\">");
		toClient.println("<a href=\"ShowCommentsDataBase\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\">Forum<span class=\"caret\"></span></a>");
		toClient.println("<ul class=\"dropdown-menu\">");
		toClient.println("<li><a href=\"ShowCommentsDataBase\">Show all the comments</a></li>");
		toClient.println("<li><a href=\"MakeComment.html\">Make a comment</a></li>");
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
      
    
		toClient.println("<B><FONT class=\"center\" size=+2 align=\"center\" >What have you learnt with this game? </FONT></B>");
		toClient.println("<BR></BR>");
		toClient.println("<BR></BR>");
		
        toClient.println("<table class=\"forumtable\" cellspacing=\"1\" cellpadding=\"1\" align=\"center\" width=\"80%\" border=\"1\">");
		toClient.println("<tr>");
		toClient.println("<th>Username</th>");
		toClient.println("<th>Comment</th>");
		toClient.println("<th>Do you like this comment?</th>");
		toClient.println("</tr>");

        String sql = "SELECT  Comment_id, user_id, Comment FROM Comments";
        System.out.println(sql);
        try {
            Statement statement=connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
			
            while(result.next()) {
                toClient.println("<tr>");            
                String UserStr = result.getString("user_id");
                String commentStr = result.getString("Comment");
				String commentIDStr = result.getString("Comment_id");
                toClient.println("<td class=\"forum\">" + UserStr + "</td>");
                toClient.println("<td class=\"forum\">" + commentStr + "</td>");
				toClient.println("<td class=\"forum\">");
				toClient.println("<input class=\"myButton\" type=\"button\" id=\"like\" value=\"Like\" onclick=\"javascript: contador()\" />");
				toClient.println("</td>");
                toClient.println("</tr>");
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Resulset: " + sql + " Exception: " + e);
        }
        toClient.println("</table>");
		toClient.println("<BR></BR>");
		toClient.println("<BR></BR>");
		toClient.println("</div>");
		
        toClient.println("</body>");
        toClient.println("</html>");
        toClient.close();
		
    }

      
}

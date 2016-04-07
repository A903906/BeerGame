import java.io.*;
import java.net.URL;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;

@SuppressWarnings("serial")
public class PageFrameNew extends HttpServlet {
	
  public void beginFrame (HttpServletRequest req, HttpServletResponse res,Connection connection, String pageName) throws ServletException, IOException {

    HttpSession session = req.getSession(false);
	String username = (String)session.getAttribute("username");
	
	res.setContentType("text/html");
		PrintWriter out = res.getWriter();
	
	if(username == null || connection==null) startFrameWithoutConnection(out);
	else{
		
	/*Head*/
	out.println("<html>");
	out.println("<head>");
	out.println("	<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
	out.println("	<link href=\"Img/beerIcon.jpg\" rel=\"shortcut icon\">");
	out.println("	<title>Beer Game - "+pageName+"</title>");
	out.println("	<meta charset=\"utf-8\"></meta>");
	out.println("	<meta content=\"IE=edge\" http-equiv=\"X-UA-Compatible\"></meta>");
	out.println("	<meta content=\"width=device-width, initial-scale=1\" name=\"viewport\"></meta>");
//	out.println("	<meta content=\"\" name=\"description\"></meta>");
//	out.println("	<meta content=\"\" name=\"author\"></meta>");
	out.println("	<link rel=\"stylesheet\" href=\"http://getbootstrap.com/dist/css/bootstrap.min.css\"></link>");
	out.println("	<link rel=\"stylesheet\" href=\"http://getbootstrap.com/assets/css/ie10-viewport-bug-workaround.css\"></link>");
	out.println("	<link rel=\"stylesheet\" href=\"beerDesignNew.css\">");
	out.println("	<link rel=\"stylesheet\" href=\"//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css\">");
	out.println("	<script>");
	out.println("	function helpLabel(cont)");
	out.println("	{");
	out.println("		document.getElementById(\"HelpLabel\").innerHTML = \"Help: \"+cont;");
	out.println("	}");
	out.println("	</script>");
	out.println("</head>");
	
	out.println("<body class=\"pull_top sticky-header\"  onmouseover=\"helpLabel('...')\">");
	/*Menu*/
	/*Log-In Log-Out and Help Bar*/
	out.println("<div class=\"navbar navbar-inverse navbar-fixed-top navbar-main beer-up\" role=\"navigate\">");
	out.println("	<div class=\"container\">");
	out.println("		<div class=\"pull-left col-xs-8\">");
	out.println("			  <p id=\"HelpLabel\" onmousemove=\"helpLabel('Move your cursor over the element you have questions about.')\">Help: ...</p>");
	out.println("		</div>");
	out.println("		<div class=\"pull-right col-xs-4\">");
	out.println("		<ul class=\"nav navbar navbar-nav navbar-right user-nav\">");
	out.println("			<li id=\"signup-menu\">");
	out.println("				<a class=\"highlight\" href=\"logout\" onmousemove=\"helpLabel('Log Out')\">Log Out</a>");
	out.println("			</li>");
	out.println("		</ul>");
	out.println("	</div>");
	out.println("</div>");
	
	/*Navigation Menu - Collapsing*/
	out.println("	<div class=\"container\">");
	out.println("		<div class=\"navbar-header\">");
	out.println("		  <button type=\"button\" class=\"navbar-toggle collapsed\" data-toggle=\"collapse\" data-target=\"#navbar\" aria-expanded=\"false\" aria-controls=\"navbar\" onmousemove=\"helpLabel('Use button to see all options.')\">");
	out.println("			<span class=\"sr-only\">Toggle navigation</span>");
	out.println("			<span class=\"icon-bar\"></span>");
	out.println("			<span class=\"icon-bar\"></span>");
	out.println("			<span class=\"icon-bar\"></span>");
	out.println("		  </button>");
	out.println("		  <a class=\"navbar-brand\" href=\"index.html\"  onmousemove=\"helpLabel('Get back to the Homepage')\">Beer Game</a>");
	out.println("		</div>");
	out.println("		<div id=\"navbar\" class=\"collapse navbar-collapse pull-right\">");
	out.println("		  <ul class=\"nav navbar-nav\">");
	out.println("			<li class=\"dropdown\">");
	out.println("				<li><a class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\" onmousemove=\"helpLabel('My Teams')\">Teams<span class=\"caret\"></span></a>");
	out.println("				<ul class=\"dropdown-menu\">");
	out.println("					<li class=\"dropdown-header\" onmousemove=\"helpLabel('My Teams')\">My Teams</li>");
	
	/*List all Teams which you are part of*/
	String sql = "SELECT group_id FROM UsersRoles WHERE user_id = '" + username + "'";
	System.out.println(sql);
	try {
		Statement statement = connection.createStatement();
		ResultSet result    = statement.executeQuery(sql);
		while (result.next()) {
			String group_id = result.getString("group_id");
			out.println("					<li><a href=\"play.html?group_id="+group_id+"\" onmousemove=\"helpLabel('View simulation &quot;"+group_id+"&quot;')\">"+group_id+"</a></li>");
		}				
	} catch(SQLException e) {
		e.printStackTrace();
		System.out.println("Resulset: " + sql + " Exception: " + e);
	}
	
	out.println("					<li role=\"separator\" class=\"divider\"></li>");
	out.println("					<li class=\"dropdown-header\">New Team</li>");
	out.println("					<li><a href=\"allGroups.html\" onmousemove=\"helpLabel('Join another team')\">Join</a></li>");
	out.println("					<li><a href=\"registerGroup.html\" onmousemove=\"helpLabel('Create another team')\">Create</a></li>");
	out.println("				</ul>");
	out.println("				</li>");
	out.println("			</li>");
	out.println("			<li>");
	out.println("				<a href=\"forum.html\" onmousemove=\"helpLabel('Use the Forum to share your thoughts about the finished games.')\">Forum</a>");
	out.println("			</li>");
	out.println("			<li>");
	out.println("				<a href=\"help.html\" onmousemove=\"helpLabel('Click &quot;Help&quot; if you did not get the purpose of the Simulation or do not know what to do.')\">Help</a>");
	out.println("			</li>");
	out.println("			<li class=\"dropdown\">");
	out.println("				<li><a class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\"  onmousemove=\"helpLabel('Settings')\"><span class=\"glyphicon glyphicon-cog\" aria-hidden=\"true\"></span><span class=\"caret\"></span></a>");
	out.println("				<ul class=\"dropdown-menu\">");
	
	/*List all Teams where you are the creator*/
	sql = "SELECT GroupName FROM Groups WHERE CreatorName = '" + username + "'";
	System.out.println(sql);
	out.println("					<li><a href=\"changeUserSettings.html\" onmousemove=\"helpLabel('Change settings and information of your account')\">My Account</a></li>");
	try {
		Statement statement = connection.createStatement();
		ResultSet result    = statement.executeQuery(sql);
		
		boolean firstTime=true;
		while (result.next()) {//A new Row of Game Data?
			if(firstTime)
			{
				out.println("					<li role=\"separator\" class=\"divider\"></li>");
				out.println("					<li class=\"dropdown-header\">Teams</li>");
				firstTime=false;
			}
			String group_id = result.getString("GroupName");
			out.println("<li><a href=\"changeGroupSettings.html?group_id="+group_id+"\" onmousemove=\"helpLabel('Change settings of team &quot;"+group_id+"&quot;')\">"+group_id+"</a></li>");
		}				
	} catch(SQLException e) {
		e.printStackTrace();
		System.out.println("Resulset: " + sql + " Exception: " + e);
	}
	
	out.println("				</ul>");
	out.println("				</li>");
	out.println("			</li>");
	
	out.println("		  </ul>");
	out.println("		</div>");
	out.println("	</div>");
	out.println("</div>");
	out.println("<div class=\"main-content\">");
	}
  } 
  
  public void startFrameWithoutConnection(PrintWriter out){
	out.println("<html>");
	out.println("<head>");
	out.println("	<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
	out.println("	<link href=\"img/beerIcon.jpg\" rel=\"shortcut icon\">");
	out.println("	<title>Beer Game</title>");
	out.println("	<meta charset=\"utf-8\"></meta>");
	out.println("	<meta content=\"IE=edge\" http-equiv=\"X-UA-Compatible\"></meta>");
	out.println("	<meta content=\"width=device-width, initial-scale=1\" name=\"viewport\"></meta>");
	out.println("	<meta content=\"\" name=\"description\"></meta>");
	out.println("	<meta content=\"\" name=\"author\"></meta>");
	out.println("	<link rel=\"stylesheet\" href=\"http://getbootstrap.com/dist/css/bootstrap.min.css\"></link>");
	out.println("	<link rel=\"stylesheet\" href=\"http://getbootstrap.com/assets/css/ie10-viewport-bug-workaround.css\"></link>	");
	out.println("	<link rel=\"stylesheet\" href=\"beerDesign.css\">");
	out.println("	<link rel=\"stylesheet\" href=\"//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css\">");
	out.println("	<script>");
	out.println("	function helpLabel(cont)");
	out.println("	{");
	out.println("		document.getElementById(\"HelpLabel\").innerHTML = \"Help: \"+cont;");
	out.println("	}");
	out.println("	</script>");
	out.println("</head>");
	
	out.println("<body class=\"pull_top sticky-header\" onmouseover=\"helpLabel('...')\">");
	out.println("<div class=\"navbar navbar-inverse navbar-fixed-top navbar-main beer-up\" role=\"navigate\">");
	out.println("	<div class=\"container\">");
	out.println("		<div class=\"pull-left col-xs-8\">");
	out.println("			  <p id=\"HelpLabel\" onmousemove=\"helpLabel('Move your cursor over the element you have questions about.')\">Help: ...</p>");
	out.println("		</div>");
	out.println("		<div class=\"pull-right col-xs-4\">");
	out.println("		<ul class=\"nav navbar navbar-nav navbar-right user-nav\">");
	out.println("			<li id=\"signup-menu\">");
	out.println("				<a class=\"highlight\" href=\"signup.html\" onmousemove=\"helpLabel('Click  &quot;Sign In&quot; to log yourself in.')\">Sign Up</a>");
	out.println("			</li>");
	out.println("			<li id=\"signin-menu\">");
	out.println("				<a href=\"login.html\" onmousemove=\"helpLabel('Click  &quot;Sign In&quot; to register yourself.')\">Sign In</a>");
	out.println("			</li>");
	out.println("		</ul>");
	out.println("		</div>");
	out.println("	</div>");
	out.println("	<div class=\"container\">");
	out.println("		<div class=\"navbar-header\">");
	out.println("		  <button type=\"button\" class=\"navbar-toggle collapsed\" data-toggle=\"collapse\" data-target=\"#navbar\" aria-expanded=\"false\" aria-controls=\"navbar\">");
	out.println("			<span class=\"sr-only\">Toggle navigation</span>");
	out.println("			<span class=\"icon-bar\"></span>");
	out.println("			<span class=\"icon-bar\"></span>");
	out.println("			<span class=\"icon-bar\"></span>");
	out.println("		  </button>");
	out.println("		  <a class=\"navbar-brand\" href=\"index.html\" onmousemove=\"helpLabel('Get back to the Startpage.')\">Beer Game</a>");
	out.println("		</div>");
	out.println("		<div id=\"navbar\" class=\"collapse navbar-collapse pull-right\">");
	out.println("		  <ul class=\"nav navbar-nav\">");
	out.println("			<li>");
	out.println("			<a href=\"help.html\" onmousemove=\"helpLabel('Click  &quot;Help&quot; if you did not get the purpose of the Simulation or do not know what to do.')\">Help</a>");
	out.println("			</li>");
	out.println("		  </ul>");
	out.println("		</div>");
	out.println("	</div>");
	out.println("</div>");
	out.println("<div class=\"main-content\">");
  }
  
  public void endFrame(PrintWriter out){
	out.println("</div>");
	out.println("<footer class=\"footer\">");
	out.println("	<div class=\"container\">");
	out.println("		<div class=\"row sections\">");
	out.println("			<div class=\"col-sm-6\">");
	out.println("				Beer Game <br>");
	out.println("				<p class=\"text-muted\">");
	out.println("				<a href=\"help.html\" class=\"text-muted\">Getting started</a><br>");
	out.println("				<a href=\"https://en.wikipedia.org/wiki/Beer_distribution_game\" class=\"text-muted\">Referrals</a>");
	out.println("				</p>");
	out.println("			</div>");
	out.println("			<div class=\"col-sm-6\">");
	out.println("				Support <br>");
	out.println("				<p class=\"text-muted\">");
	out.println("				<a href=\"help.html\" class=\"text-muted\">Help & FAQs</a><br>");
	out.println("				</p>");
	out.println("			</div>");
	out.println("		</div>");
	out.println("	</div>");
	out.println("</footer>");
	
	out.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js\"></script>");
	out.println("<script src=\"http://getbootstrap.com/dist/js/bootstrap.min.js\"></script>");
	out.println("<script src=\"http://getbootstrap.com/assets/js/ie10-viewport-bug-workaround.js\"></script>");
	out.println("</body>");
	out.println("</html>");
  }
}

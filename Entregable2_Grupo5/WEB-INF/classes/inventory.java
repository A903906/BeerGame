import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class inventory extends HttpServlet {

	String[] roleList = new String[]{"Factory", "Distributor", "Wholesaler", "Retailer"};
    Connection connection;
    PageFrame pFrame = new PageFrame();
	
	
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String url="jdbc:odbc:BeerGame";
            connection=DriverManager.getConnection(url); 
            System.out.println("Connection Sucessfull");
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Connection Failed");
        }
    }
	
    public void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*Everytime*/
        resp.setContentType("text/html");
        PrintWriter out = null;
        try {
            out=resp.getWriter();
        } catch (IOException io) {
            System.out.println("Error opening PrintWriter");
        }

        String username = req.getParameter("nickname");
        String password = req.getParameter("password");
        String group_id = req.getParameter("groupname");

        if(username==null) {
            System.out.println("Problem reading username from request");
			Error_inventory(out);
            return;
        }
        if(password==null) {
          System.out.println("Problem reading password from request");
		  Error_inventory(out);
          return;
        }
        if(group_id==null) {
          System.out.println("Problem reading group name from request");
		  Error_inventory(out);
          return;
        }
        
		/*Look for database*/
		String sqlLogin = "SELECT username FROM Users WHERE username = '" + username +
            "' AND Password = '" + password + "'";
		String sqlRole = "SELECT role_id FROM UsersRoles WHERE user_id = '" + username +
            "' AND group_id = '" + group_id + "'";
		
		if( connection == null)
		{
          System.out.println("Problem with Connection");
		}
		try{
            Statement statement  = connection.createStatement();
            ResultSet result     = statement.executeQuery(sqlLogin);
            ResultSet resultRole = statement.executeQuery(sqlRole);
			if (resultRole.next()) {
                HttpSession session = req.getSession(true);
                session.setAttribute("username", username);
                String role_id = resultRole.getString("role_id");
				ShowInventory(out, session, role_id, group_id, req, resp);//
            }
		} catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Resulset: " + sqlLogin + " Und " + sqlRole + " Exception: " + e);
			Error_inventory(out);
        }
    } 

    public void ShowInventory(PrintWriter out, HttpSession session, String role_id, String group_id, HttpServletRequest req, HttpServletResponse resp) {
		String username = "" + session.getAttribute("username");
		
		try{pFrame.beginFrame(req,resp,connection,"Inventory");}
		catch(Exception e) {
            e.printStackTrace();
            System.out.println("Exception: " + e);
        }
		
		out.println("<div class=\"beer container\" align=\"center\">");
		out.println("<h1>Statistics</h1>");
		out.println("Here you see your past actions and orders enlisted.");
		out.println("<table class=\"inventory-table CSSTableGenerator\">");
		out.println("<tr>");
		out.println("<td>Day</td><td>Stock</td><td>Incoming</td><td>Outgoing</td><td>Order</td>");
		out.println("</tr>");
		
		/*Get Data from Database*/
		String sql = "SELECT day, inventory, ordersreceived, ordersserved, ordered FROM Game WHERE user_id = '" + username +
            "' AND group_id = '" + group_id + "'";
        System.out.println(sql);
		try {
            Statement statement = connection.createStatement();
            ResultSet result    = statement.executeQuery(sql);
			
            while (result.next()) {//A new Row of Game Data?
                int day 	 = result.getInt("day");
                int stock 	 = result.getInt("inventory");
                int incoming = result.getInt("ordersreceived");
                int outgoing = result.getInt("ordersserved");
                int order 	 = result.getInt("ordered");
				out.println("<tr><td>"+day+"</td><td>"+stock+"</td><td>"+incoming+"</td><td>"+outgoing+"</td><td>"+order+"</td></tr>");
            }				
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Resulset: " + sql + " Exception: " + e);
        }
		
		out.println("</table>");
		out.println("<br>");
		out.println("	<input type=\"button\" value=\"Back\" name=\"Back2\" onclick=\"history.back()\" class=\"newbtn\">");
		out.println("</div>");
		
		try{pFrame.endFrame(req,resp);}
		catch(Exception e) {
            e.printStackTrace();
            System.out.println("Exception: " + e);
        }
	
        out.flush();
        out.close();
    }
	
	/*No valid Role	or other Error?*/
    public void Error_inventory(PrintWriter out) {
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Error</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<BR>");
        out.println("<H2 align=\"center\">Error while checking inventory</H2>");
        out.println("</body>");
        out.println("</html>");
        out.flush();
        out.close();
    }

}

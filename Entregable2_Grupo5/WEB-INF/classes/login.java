import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class login extends HttpServlet {
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
        PrintWriter out=null;
        try {
            out=resp.getWriter();
        } catch (IOException io) {
            System.out.println("Error opening PrintWriter");
        }

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if(username==null) {
            System.out.println("Problem reading username from request");
            return;
        }
        if(password==null) {
          System.out.println("Problem reading password from request");
          return;
        }
        
        String sql = "SELECT firstname, surname FROM Users WHERE firstname = '" + username +
            "' AND password = '" + password + "'";
        System.out.println(sql);
        try {
            Statement statement=connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            if (result.next()) {
                HttpSession session = req.getSession(true);
                session.setAttribute("username", result.getString("firstname"));
                session.setAttribute("surname", result.getString("surname"));
                Welcome(out, session);
            } else {
                Error_login(out);
            } 
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Resulset: " + sql + " Exception: " + e);
        }
    } 

    public void Welcome(PrintWriter out, HttpSession session) {
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Welcome</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<BR>");
        out.println("<H2 align=\"center\">Home page of " + session.getAttribute("username") +
          " " + session.getAttribute("surname") + "</H2>");

		out.println("<BR><a href=\"menu.html\">Menu</a>");
        out.println("</body>");
        out.println("</html>");
        out.flush();
        out.close();
    }
    public void Error_login(PrintWriter out) {
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Error in login</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<BR>");
        out.println("<H2 align=\"center\">Username or password incorrect</H2>");
        out.println("</body>");
        out.println("</html>");
        out.flush();
        out.close();
    }
}

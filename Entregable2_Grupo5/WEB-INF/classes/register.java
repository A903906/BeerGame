import java.sql.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class register extends HttpServlet {
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

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String surname = req.getParameter("surname");
        String firstname = req.getParameter("firstname");

        String sqlSelect = "SELECT ID, username, password, surname, firstname FROM Users WHERE username = '" +
            username + "'";
        System.out.println("Select sql: " + sqlSelect);
        String sql = "INSERT INTO Users (Username, password, surname, firstname) VALUES (";
        sql +=  "'" + username + "'";
        sql +=  ", '" + password + "'";
        sql +=  ", '" + surname + "'";
        sql +=  ", '" + firstname + "')";
        System.out.println("Insert sql: " + sql);

        try{
            Statement statementSelect=connection.createStatement();
            ResultSet result = statementSelect.executeQuery(sqlSelect);
            boolean exist = false;
            while(result.next()) {
                exist = true;
                output(res, "User already exist");
                System.out.println(result.getString("firstname") + " " + result.getString("surname"));
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
        toClient.println("<head><title>Register</title></head>");
        toClient.println("<body>");
        toClient.println("<h2>Register</h2>");
		toClient.println(message);
        toClient.println("</body>");
        toClient.println("</html>");
        toClient.close();
    }
}

package Gym;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String usrname = request.getParameter("user_name");
        String pwd = request.getParameter("password");
        
        // Replace with your actual database details
        // Replace with your actual database details
String url = "jdbc:oracle:thin:@localhost:1521:XE";

        
        try {
            // Load the Oracle JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            try (Connection con = DriverManager.getConnection(url, "system", "admin")) {
                String query = "SELECT password FROM ADMIN WHERE name = ?";
                try (PreparedStatement pstmt = con.prepareStatement(query)) {
                    pstmt.setString(1, usrname);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            String storedPassword = rs.getString("password");
                            if (pwd.equals(storedPassword)) {
                                response.sendRedirect("EntryRecord.jsp");
                            } else {
                                out.println("Invalid password");
                            }
                        } else {
                            out.println("User not found");
                        }
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                out.println("An error occurred");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            out.println("Database driver not found");
        }
    }

    @Override
    public String getServletInfo() {
        return "Login Servlet";
    }
}

package Gym;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Date;

public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            
            String usrname = request.getParameter("name");
            String memid = request.getParameter("memid");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            String address = request.getParameter("address");
            int age = Integer.parseInt(request.getParameter("age"));
            double height = Double.parseDouble(request.getParameter("height"));
            double weight = Double.parseDouble(request.getParameter("weight"));
  
            // Register the Oracle JDBC driver
            Driver myDriver = new oracle.jdbc.driver.OracleDriver();
            try {
                DriverManager.registerDriver(myDriver);
            } catch (SQLException ex) {
                Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            // Replace with your actual database details
            String url ="jdbc:oracle:thin:@localhost:1521:XE";
            
            try (Connection con = DriverManager.getConnection(url, "system", "admin")) {
                String query = "INSERT INTO MEMBER(id,name,age,height,weight,dateofjoin,phone,email,address) values(?,?,?,?,?,?,?,?,?)";
                try (PreparedStatement pstmt = con.prepareStatement(query)) {
                    pstmt.setString(1, memid);
                    pstmt.setString(2, usrname);
                    pstmt.setInt(3, age);
                    pstmt.setDouble(4, height);
                    pstmt.setDouble(5, weight);
                    
                    // Set current date
                    Date currentDate = new Date(System.currentTimeMillis());
                    pstmt.setDate(6, currentDate);
                    
                    pstmt.setString(7, phone);
                    pstmt.setString(8, email);
                    pstmt.setString(9, address);
                    
                    int rowsInserted = pstmt.executeUpdate();
                    if (rowsInserted > 0) {
                        response.sendRedirect("RegSuccess.html");
                    } else {
                        out.println("Registration failed");
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
                out.println("An error occurred");
            }
        } catch (IOException | NumberFormatException ex) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Register Servlet";
    }
}

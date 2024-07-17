/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Gym;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

@WebServlet("/InsertWorkoutServlet")
public class InsertWorkoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String memberId =request.getParameter("memberId");
        String workoutId = request.getParameter("workoutId");

       boolean isInserted = insertWorkoutDataIntoDatabase(memberId, workoutId);
if (isInserted) {
    response.sendRedirect("Profile.jsp");
} else {
    response.getWriter().write("Entry failed");
    }

 
    }
    
    private boolean insertWorkoutDataIntoDatabase(String memberId, String workoutId) {
    boolean isSuccess = false;
    try {
         Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@localhost:1521:XE";
            String dbUser = "system";
            String dbPass = "admin";

        try (Connection con = DriverManager.getConnection(url, dbUser, dbPass)) {
            String query = "INSERT INTO user_session (user_id, workout_id, date_wk, status) VALUES (?, ?, ?, 'LIVE')";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, memberId);
                pstmt.setString(2, workoutId);
                java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
                pstmt.setDate(3, currentDate);
                int rowsInserted = pstmt.executeUpdate();
                isSuccess = rowsInserted > 0;
            }
        } catch (SQLException ex) {
            // Handle database errors
            ex.printStackTrace();
        }
    } catch (ClassNotFoundException ex) {
        // Handle driver class not found exception
        ex.printStackTrace();
    }
    return isSuccess;
}
}

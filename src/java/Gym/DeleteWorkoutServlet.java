package Gym;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/DeleteWorkoutServlet")
public class DeleteWorkoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String workoutId = request.getParameter("WorkoutId");
        String memberId = request.getParameter("memberId");

        try (java.sql.Connection con = java.sql.DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "admin")) {
            String query = "DELETE user_session WHERE user_id = ? AND workout_id = ? AND status='LIVE'";
            try (java.sql.PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, memberId);
                pstmt.setString(2, workoutId);
                pstmt.executeUpdate();
              
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Redirect back to the Profile.jsp page
        response.sendRedirect("Profile.jsp");
    }
}

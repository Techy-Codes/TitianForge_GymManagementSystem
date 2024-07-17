package Gym;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AddWorkoutServlet")
public class AddWorkoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String memberIdString = request.getParameter("memberId");
        String workoutIdString = request.getParameter("workoutId");

        if (memberIdString != null && !memberIdString.isEmpty() &&
            workoutIdString != null && !workoutIdString.isEmpty()) {
            int memberId = Integer.parseInt(memberIdString);
            int workoutId = Integer.parseInt(workoutIdString);

            // Insert the workout data into the user_session table
            insertWorkoutDataIntoDatabase(memberId, workoutId);

            // Send a success response
            response.getWriter().write("Workout data inserted successfully.");
        } else {
            // Send an error response
            response.getWriter().write("Invalid member ID or workout ID.");
        }
    }

    private void insertWorkoutDataIntoDatabase(int memberId, int workoutId) {
        // Replace with your actual database insertion code
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@localhost:1521:XE";
            String dbUser = "system";
            String dbPass = "admin";

            try (Connection con = DriverManager.getConnection(url, dbUser, dbPass)) {
                String query = "INSERT INTO user_session (user_id, workout_id) VALUES (?, ?)";
                try (PreparedStatement pstmt = con.prepareStatement(query)) {
                    pstmt.setInt(1, memberId);
                    pstmt.setInt(2, workoutId);
                    pstmt.executeUpdate();
                }
            } catch (SQLException ex) {
                // Handle database errors
                ex.printStackTrace();
            }
        } catch (ClassNotFoundException ex) {
            // Handle driver class not found exception
            ex.printStackTrace();
        }
    }
}

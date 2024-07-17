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

@WebServlet("/RemoveWorkoutServlet")
public class RemoveWorkoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String workoutIdString = request.getParameter("workoutId");

        if (workoutIdString != null && !workoutIdString.isEmpty()) {
            int workoutId = Integer.parseInt(workoutIdString);

            // Remove the workout data from the user_session table
            removeWorkoutDataFromDatabase(workoutId);

            // Send a success response
            response.getWriter().write("Workout data removed successfully.");
        } else {
            // Send an error response
            response.getWriter().write("Invalid workout ID.");
        }
    }

    private void removeWorkoutDataFromDatabase(int workoutId) {
        // Replace with your actual database deletion code
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@localhost:1521:XE";
            String dbUser = "system";
            String dbPass = "admin";

            try (Connection con = DriverManager.getConnection(url, dbUser, dbPass)) {
                String query = "DELETE FROM user_session WHERE workout_id = ?";
                try (PreparedStatement pstmt = con.prepareStatement(query)) {
                    pstmt.setInt(1, workoutId);
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

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

@WebServlet("/EntryRecordServlet")
public class EntryRecordServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String memid = request.getParameter("mem_id");

        if (action != null) {
            if (action.equals("sign_in")) {
                try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "admin")) {
                    String query = "INSERT INTO ATTENDANCE (id, name, attendance_date, in_time) VALUES (?, ?, ?, ?)";

                    try (PreparedStatement pstmt = con.prepareStatement(query)) {
                        pstmt.setString(1, memid);

                        String nameQuery = "SELECT name FROM member WHERE id = ?";
                        try (PreparedStatement pstmtName = con.prepareStatement(nameQuery)) {
                            pstmtName.setString(1, memid);
                            try (ResultSet rs = pstmtName.executeQuery()) {
                                if (rs.next()) {
                                    String usrname = rs.getString("name");
                                    pstmt.setString(2, usrname);
                                } else {
                                    response.getWriter().write("User not found");
                                    return;
                                }
                            }
                        }

                        java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
                        pstmt.setDate(3, currentDate);
                        LocalTime currentTime = LocalTime.now();
                        pstmt.setTime(4, Time.valueOf(currentTime));

                        int rowsInserted = pstmt.executeUpdate();
                        if (rowsInserted > 0) {
                            response.sendRedirect("EntryRecord.jsp");
                        } else {
                            response.getWriter().write("Entry failed");
                        }
                    } catch (SQLException ex) {
                        response.getWriter().write("An error occurred during sign-in.");
                    }
                } catch (SQLException ex) {
                    response.getWriter().write("An error occurred.");
                }
            } else if (action.equals("sign_out")) {
    try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "admin")) {
        String updateAttendanceQuery = "UPDATE ATTENDANCE SET out_time = ? WHERE id = ?";
        try (PreparedStatement pstmtOut = con.prepareStatement(updateAttendanceQuery)) {
            LocalTime currentTime = LocalTime.now();
            pstmtOut.setTime(1, Time.valueOf(currentTime));
            pstmtOut.setString(2, memid);

            int rowsUpdatedAttendance = pstmtOut.executeUpdate();

            if (rowsUpdatedAttendance > 0) {
                // Perform an additional update query on user_session table
                String updateUserSessionQuery = "update user_session set status='exp' where user_id=?";

                try (PreparedStatement pstmtUserSession = con.prepareStatement(updateUserSessionQuery)) {
                    // Set parameters for the additional update query
                    pstmtUserSession.setString(1, memid);

                    // Execute the additional update query
                    int rowsUpdatedUserSession = pstmtUserSession.executeUpdate();

                    // Check if the additional update was successful
                    if (rowsUpdatedUserSession > 0) {
                        // Both updates were successful
                        response.sendRedirect("EntryRecord.jsp");
                    } else {
                        response.getWriter().write("Additional update on user_session failed");
                    }
                } catch (SQLException ex) {
                    response.getWriter().write("An error occurred during the additional update on user_session.");
                }
            } else {
                response.getWriter().write("Exit failed");
            }
        } catch (SQLException ex) {
            response.getWriter().write("An error occurred during sign-out.");
        }
    } catch (SQLException ex) {
        response.getWriter().write("An error occurred.");
    }
} else {
    response.getWriter().write("Invalid action.");
}

    }
    }

    @Override
    public String getServletInfo() {
        return "Attendance Entry Servlet";
    }

    private List<Attendance> retrieveAttendanceRecords() {
        // Implement your code to retrieve attendance records from the database
        // and return a List<Attendance> containing the records.
        // You can use JDBC to perform the database operations.
        return null; // Replace this with actual list retrieval logic
    }
}

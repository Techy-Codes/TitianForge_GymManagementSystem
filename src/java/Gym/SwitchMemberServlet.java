package Gym;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/SwitchMemberServlet")
public class SwitchMemberServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String memberIdString = request.getParameter("memberId");

        if (memberIdString != null && !memberIdString.isEmpty()) {
            int memberId = Integer.parseInt(memberIdString);
            Member member = getMemberById(memberId);

            if (member != null) {
                // Get the session associated with the request or create a new one
                HttpSession session = request.getSession(true);

                // Store the member object in the session
                session.setAttribute("member", member);

                // Create an empty list for workout data and store it in session

                // Track the session creation time
                Date createTime = new Date();
                session.setAttribute("createTime", createTime);
                session.setAttribute("id", request.getParameter("memberId"));
                // Redirect to the Profile.jsp page
                response.sendRedirect("Profile.jsp");
            } else {
                // Member not found, redirect back to the login page
                response.sendRedirect("SwitchMember.jsp");
            }
        } else {
            // Invalid memberId, redirect back to the login page
            response.sendRedirect("Login.html");
        }
    }
    
     private Member getMemberById(int memberId) {
        Member member = null;

        // Replace with your actual database retrieval code
        try {
            // Register the Oracle JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Replace with your actual database details
            String url ="jdbc:oracle:thin:@localhost:1521:XE";
            String dbUser = "system";
            String dbPass = "admin";

            try (Connection con = DriverManager.getConnection(url, dbUser, dbPass)) {
                String query = "SELECT id, name, height, weight, duedate FROM member WHERE id = ?";
                try (PreparedStatement pstmt = con.prepareStatement(query)) {
                    pstmt.setInt(1, memberId);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            int id = rs.getInt("id");
                            String name = rs.getString("name");
                            int height = rs.getInt("height");
                            int weight = rs.getInt("weight");
                            String dueDate = rs.getString("duedate");

                            member = new Member(id, name, height, weight, dueDate);
                        }
                    }
                }
            } catch (SQLException ex) {
                // Handle database errors
                ex.printStackTrace();
            }
        } catch (ClassNotFoundException ex) {
            // Handle driver class not found exception
            ex.printStackTrace();
        }

        return member;
    }
}

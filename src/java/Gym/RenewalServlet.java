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
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author hevin
 */
@WebServlet(name = "RenewalServlet", urlPatterns = {"/RenewalServlet"})
public class RenewalServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
      @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            String memberId =request.getParameter("memberId");
            String packId = request.getParameter("membershipId");
            int selectedPeriod = Integer.parseInt(request.getParameter("mnt"));
            
            String amount = request.getParameter("amount");
            String mode = request.getParameter("mode");

            boolean isInserted = insertIntoDatabase(memberId, packId,selectedPeriod,amount,mode);
            if (isInserted) {
                     response.sendRedirect("RenewSuccess.jsp");
                        } else {
                     response.getWriter().write("Entry failed");
                         }

    }
    
private boolean insertIntoDatabase(String memberId, String packId, int selectedPeriod, String amount, String mode) {
        boolean isSuccess = false;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@localhost:1521:XE";
            String dbUser = "system";
            String dbPass = "admin";
               
                java.sql.Date currentDate = new java.sql.Date(new Date().getTime());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currentDate);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDateToday = sdf.format(currentDate);

            if (selectedPeriod == 1) {
                    // Add 1 month to the current date
                       calendar.add(Calendar.MONTH, 1);
            } else if (selectedPeriod == 3) {
                    // Add 3 months to the current date
                         calendar.add(Calendar.MONTH, 3);
            } else if (selectedPeriod == 6) {
                      // Add 6 months to the current date
                          calendar.add(Calendar.MONTH, 6);
            } else if (selectedPeriod == 12) {
                               // Add 12 months to the current date
                         calendar.add(Calendar.MONTH, 12);
                    }

            Date adjustedDate = calendar.getTime();
            String formattedAdjustedDate = sdf.format(adjustedDate);
            try (Connection con = DriverManager.getConnection(url, dbUser, dbPass)) {
                String query = "INSERT INTO payment (MEMBER_ID, PACK_ID, DATEOFRENEWAL, DUEDATE, AMOUNT, MODEOFPAY, status) VALUES (?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?, ?, 'PAID')";
                try (PreparedStatement pstmt = con.prepareStatement(query)) {
                    pstmt.setString(1, memberId);
                    pstmt.setString(2, packId);
                    currentDate = new java.sql.Date(System.currentTimeMillis());
                    pstmt.setDate(3, currentDate);
                    pstmt.setString(4, formattedAdjustedDate); // Corrected casting
                    pstmt.setString(5, amount);
                    pstmt.setString(6, mode);

                    int rowsInserted = pstmt.executeUpdate();
                    isSuccess = rowsInserted > 0;
                    if (isSuccess) {
            // Update query
            String updateQuery = "UPDATE MEMBER SET DUEDATE='?' WHERE ID=?";
     
            
            try (PreparedStatement updatePstmt = con.prepareStatement(updateQuery)) {
                // Set parameters for the update query
                updatePstmt.setString(1, formattedAdjustedDate);
                updatePstmt.setString(2, memberId);
                
                // Execute the update query
                int rowsUpdated = updatePstmt.executeUpdate();
                
                // Check if the update was successful
                if (rowsUpdated > 0) {
                    // Update was successful
                    System.out.println("Update successful");
                } else {
                    // Update failed
                    System.out.println("Update failed");
                }
            }
        }
                }
            } catch (SQLException ex) {
                ex.printStackTrace(); // Handle database errors
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace(); // Handle driver class not found exception
        }
        return isSuccess;
    }


}

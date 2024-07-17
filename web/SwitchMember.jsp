<%-- 
    Document   : SwitchMember
    Created on : 18-Aug-2023, 8:46:45 pm
    Author     : hevin
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*, java.text.SimpleDateFormat, java.util.Date, java.util.Calendar"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Switch Member</title>
    <style>
    
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f0f0f0;
        }
        .container {
            max-width: 400px;
            margin: 0 auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
        h2 {
            font-size: 24px;
            margin-bottom: 10px;
        }
        select, input[type="submit"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 16px;
        }
        input[type="submit"] {
            background-color: #007BFF;
            color: #fff;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Select a Member to Switch:</h2>
        <form action="SwitchMemberServlet" method="post">
            <select name="memberId">
        <%
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                String url = "jdbc:oracle:thin:@localhost:1521:XE";
                String dbUser = "system";
                String dbPass = "admin";

                Connection con = DriverManager.getConnection(url, dbUser, dbPass);
                System.out.println("Connected to database"); // Debug line

                Statement stmt = con.createStatement();

                java.sql.Date currentDate = new java.sql.Date(new Date().getTime());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currentDate);

// Get today's date in yyyy-MM-dd format
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
String formattedDateToday = sdf.format(currentDate);

// Subtract one day from the current date
calendar.add(Calendar.DAY_OF_YEAR, +1);
Date NextDay = calendar.getTime();
String formattedDateNextDay = sdf.format(NextDay);

String query = "SELECT * FROM attendance WHERE attendance_date >= TO_DATE(?, 'YYYY-MM-DD') AND attendance_date <= TO_DATE(?, 'YYYY-MM-DD') AND out_Time IS NULL ";
PreparedStatement pstmt = con.prepareStatement(query);
pstmt.setString(1, formattedDateToday);
pstmt.setString(2, formattedDateNextDay);




                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
        %>
        <option value="<%= id %>"><%= name %></option>
        <%
                }

                rs.close();
                pstmt.close();
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        %>
    </select>
            <input type="submit" value="Switch">
        </form>
    </div>
</body>
</html>

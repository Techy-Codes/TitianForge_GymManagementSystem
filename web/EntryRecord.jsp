<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection, java.sql.DriverManager, java.sql.PreparedStatement, java.sql.ResultSet, java.sql.Timestamp" %>
<%@ page import="java.text.SimpleDateFormat,java.util.Date, java.util.Calendar" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Titan Forge Fitness</title>
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link rel="stylesheet" href="css/style.css">
    <link rel="icon" href="images/logo.png">
    <style>
/* Your existing CSS styles here */
.header {
  background: beige;
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  position: fixed;
  top: 100px; /* Adjust this value to control the distance from the top */
  left: 0;
  right: 0;
  z-index: 1000;
  padding: 2rem 9%;
  -webkit-box-shadow: 0 0.5rem 1.5rem rgba(0, 0, 0, 0.1);
  box-shadow: 0 0.5rem 1.5rem rgba(0, 0, 0, 0.1);
  -webkit-transition: .2s linear;
  transition: .2s linear;
}


.header .logo {
  font-size: 3rem;
  color: #130f40;
  font-weight: bolder;
  text-decoration: none;
}

.header .navbar {
  display: flex;
  align-items: center;
}

.header .navbar a {
  margin: 0 1rem;
  font-size: 1.7rem;
  color: rgb(108, 8, 48);
  text-decoration: none;
  transition: color 0.3s ease-in-out;
}

.header .navbar a:hover {
  color: #45b5b5;
}

.header .icons {
  display: flex;
  align-items: center;
}

.header .icons div {
  font-size: 3.5rem;
  margin-left: 1.5rem;
  color: #a10b53;
  cursor: pointer;
  transition: color 0.3s ease-in-out;
}

.header .icons div:hover {
  color: #e67e22;
}

/* Adjusted the position and animation for the login form */
.header .login-form {
  position: absolute;
  top: 100%;
  right: 2rem;
  width: 25rem;
  border-radius: 1rem;
  background: #fff;
  box-shadow: 0 0.5rem 1.5rem rgba(0, 0, 0, 0.1);
  padding: 2rem;
  display: none;
  animation: fadeIn 0.4s linear;
  z-index: 1001; /* Higher than the header */
}

.header.active .login-form {
  top: 120%;
  display: block;
}

.header .login-form h3 {
  color: #130f40;
  font-size: 2.5rem;
  padding-bottom: .5rem;
}

.header .login-form .box {
  width: 100%;
  border-bottom: 0.2rem solid #130f40;
  padding: 1.5rem 0;
  font-size: 1.6rem;
  color: #130f40;
  text-transform: none;
  margin: 1rem 0;
}

.header .login-form .btn-container {
  display: flex;
  justify-content: space-between;
  margin-top: 1.5rem;
}

.header .login-form .btn {
  width: 48%; /* Adjusted width */
  padding: 0.8rem 2.8rem;
  border-radius: 5rem;
  border: none;
  cursor: pointer;
  background: #130f40;
  color: #fff;
  font-size: 1.7rem;
  overflow: hidden;
  position: relative;
  transition: background 0.3s ease-in-out;
  text-align: center;
}

.header .login-form .btn:hover {
  background: #1a1747;
}

.header .login-form .links {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 1rem;
}

.header .login-form .links a {
  font-size: 1.4rem;
  color: #a41572;
  text-decoration: none;
}

.header .login-form .links a:hover {
  color: #130f40;
  text-decoration: underline;
}
/* ... (your existing styles) ... */

.header .navbar form {
    display: flex;
    align-items: center;
    margin-left: auto; /* Pushes the form to the right side */
}

.header .navbar form input[type="text"] {
    border: 2px solid #f0f0f0;
    border-radius: 4px;
    font-size: 12px;
    padding: 10px;
    width: 100%;
}

.header .navbar form .btn {
    margin-left: 10px;
    border: none;
    cursor: pointer;
    background: #130f40;
    color: #fff;
    font-size: 1.7rem;
    padding: 0.8rem 2.8rem;
    border-radius: 5rem;
    overflow: hidden;
    position: relative;
    transition: background 0.3s ease-in-out;
}

.header .navbar form .btn:hover {
    background: #1a1747;
}


     table {
    width: 100%;
    margin: auto;
    border: 2px solid rgba(255, 0, 0, 0.8);
    border-collapse: collapse;
    text-align: center;
    font-size: 16px;
    background: rgba(255, 0, 0, 0.1);
    color: rgb(41, 12, 123);
    margin-top: 100px;
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);
}

th, td {
    padding: 12px;
    border: 1px solid rgba(255, 0, 0, 0.8);
}

th {
    background-color: rgba(255, 0, 0, 0.8);
    color: white;
}

td {
    background-color: rgba(255, 255, 255, 0.8);
}

tr:nth-child(even) td {
    background-color: rgba(214, 28, 213, 0.6);
}

tr:nth-child(odd) td {
    background-color: rgba(239, 19, 131, 0.8);
}
.signin-btns {
    display: flex;
    align-items: center;
    gap: 1px;
}

.signin-btns form {
    display: flex;
    align-items: center;
}

.signin-btns form input[type="text"] {
   
       margin-left: 150px;
}

.signin-btn {
    width: 120px; /* Adjust the width as needed */
}



</style>
</head>
<body>
    
<!-- header section starts  -->
<header class="header">
    <a href="#" class="logo"> <img src="images/BAN.png" height="100px" width="200%"> </a>
    <nav class="navbar">
        <div class="signin-btns">
            <form action="EntryRecordServlet" method="post">
                <input type="text" name="mem_id" placeholder="Enter your Member ID" id="mem_id" class="box">
                <input type="submit" value="sign_in" class="btn" name="action"> <input type="submit" value="sign_out" class="btn" name="action">
            </form>
            <form action="EntryRecordServlet" method="post">
               
            </form>
        </div>
        <button class='btn' onclick="window.location.href='Register.html';" style="width:auto;">Add Member</button>
        <button class='btn' onclick="window.location.href='SwitchMember.jsp';" style="width:auto;">Workout Tracker</button>
        <button class='btn' onclick="window.location.href='RenewMembership.jsp';" style="width:auto;">Renewal</button>

    </nav>
</header>




<!-- header section ends -->
    <section class="about" id="about">
        <div class="image">
            <h3>Attendance Records</h3>
            <table border="1">
               
                <tbody>
                 <%
                    try (java.sql.Connection con = java.sql.DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "admin")) {
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
                     String nameQuery = "SELECT * FROM attendance WHERE attendance_date >= TO_DATE(?, 'YYYY-MM-DD') AND attendance_date <= TO_DATE(?, 'YYYY-MM-DD') ";
                         try (java.sql.PreparedStatement pstmtName = con.prepareStatement(nameQuery)) {
                                    pstmtName.setString(1, formattedDateToday);
                                    pstmtName.setString(2, formattedDateNextDay);

                                    java.sql.ResultSet rs = pstmtName.executeQuery();
                 %>
        <table border="1">
            <tbody>
                 <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>In Time</th>
                        <th>Out Time</th>
                    </tr>
                </thead>
                <%
                while (rs.next()) {
                    Timestamp inTime = rs.getTimestamp("in_time");
                    Timestamp outTime = rs.getTimestamp("out_time");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                    String formattedInTime = (inTime != null) ? dateFormat.format(inTime) : "N/A";
                    String formattedOutTime = (outTime != null) ? dateFormat.format(outTime) : "N/A";
                %>
                <tr>
                    <td><%= rs.getInt("id") %></td>
                    <td><%= rs.getString("name") %></td>
                    <td><%= formattedInTime %></td>
                    <td><%= formattedOutTime %></td>
                </tr>
                <%
                }
                %>
            </tbody>
        </table>
<%
    }
} catch (Exception e) {
    e.printStackTrace();
}
%>

                </tbody>
            </table>
        </div>
        
    </section>
<!--<audio controls autoplay>
    <source src="audio/bg.mp3" type="audio/mpeg">
    Your browser does not support the audio element.
</audio>-->
</body>
</html>

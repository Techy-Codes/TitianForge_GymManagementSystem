<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.*"%>
<%@ page import="java.sql.Connection, java.sql.DriverManager, java.sql.PreparedStatement, java.sql.ResultSet, java.sql.Timestamp" %>
<%@ page import="java.text.SimpleDateFormat" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Titian Forge Fitness</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }

        .header {
            background: beige;
            padding: 1rem;
            text-align: center;
        }

        .logo img {
            max-width: 100%;
            height: auto;
        }

        .profile {
            display: flex;
            align-items: flex-start;
            margin-top: 5rem; /* Adjust the top margin as needed */
        }

        .about, .tracker {
            border: 1px solid #ccc;
            padding: 1rem;
            margin: 1rem;
        }

        .about {
            flex: 40%;
        }

        .tracker {
            flex: 60%;
        }

        .about h2, .tracker h2 {
            font-size: 1.5rem;
            margin-bottom: 1rem;
        }

          .workoutTable {
        width: 100%;
        border-collapse: collapse;
        margin-top: 1rem;
        margin-bottom: 1rem; /* Add margin at the bottom to separate from the button */
        background-color: #f8f8f8; /* Background color for the table */
    }

    .workoutTable th, .workoutTable td {
        border: 1px solid #ccc;
        padding: 0.5rem;
        text-align: center;
        background-color: #fff; /* Background color for table cells */
    }

    .workoutTable th {
        background-color: #f2f2f2;
    }
    </style>
</head>
<body>
    <header class="header">
        <a href="#" class="logo"> <img src="images/BAN.png" height="100px" width="60%"> </a>
    </header>
    <section class="profile">
    <div class="about">
    <h2>About Me</h2>
    <p>Name: ${member.name}</p>
    <p>ID: ${member.id}</p>
    <p>Height:${member.height} centimeters</p>
    <p>Weight: ${member.weight} kilograms</p>
    <p>Membership Due: ${member.dueDate}</p>
    <p>Session ID: ${pageContext.session.id}</p>
</div>

    <div class="tracker">
        <a href="SwitchMember.jsp">Switch Member</a>
        <form action="InsertWorkoutServlet" method="post"> 
                <input type="hidden" name="memberId" value="${member.id}">

        <h2>Workout Tracker</h2>
        
        <select name="workoutId"> <!-- Use name attribute to identify the selected value -->
            <% 
                try {
                       Class.forName("oracle.jdbc.driver.OracleDriver");
                        String url = "jdbc:oracle:thin:@localhost:1521:XE";
                        String dbUser = "system";
                        String dbPass = "admin";

                    Connection con = DriverManager.getConnection(url, dbUser, dbPass);
                    Statement stmt = con.createStatement();
                    String query = "SELECT * FROM workouts";
                    ResultSet rs = stmt.executeQuery(query);
                    
                    while (rs.next()) {
                        String workoutId = rs.getString("workout_id");
                        String workoutName = rs.getString("name");
            %>
            <option value="<%= workoutId %>"><%= workoutName %></option>
            <%
                    }
                    
                    rs.close();
                    stmt.close();
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            %>
        </select>
        <button type="submit" name="addWorkout">Add Workout</button> <!-- Change to type="submit" and add name attribute -->
                    </form> 
     <table border="1" class="workoutTable">
                    <tbody>
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th>Set</th>
                                <th>Rep</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <%
                        try (java.sql.Connection con = java.sql.DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "admin")) {
                            String query = "SELECT  w.name,w.workout_id,us.user_id FROM user_session us INNER JOIN workouts w ON us.workout_id = w.workout_id WHERE us.user_id = ? AND us.status = 'LIVE'";
                            try (java.sql.PreparedStatement pstmt = con.prepareStatement(query)) {
                  
                                String memberId = (String) session.getAttribute("id");
                                pstmt.setString(1, memberId);
                                java.sql.ResultSet rs = pstmt.executeQuery();
                                while (rs.next()) {
     
                        %>
                       <form action="DeleteWorkoutServlet" method="post"> 
                        <tr>
                           
                            <td><%= rs.getString("name") %></td>
                            <td>3</td>
                            <td>15</td>
                            <td>
                            <input type="hidden" name="memberId" value=<%= rs.getString("user_id") %>>
                            <input type="hidden" name="WorkoutId" value=<%= rs.getString("workout_id") %>>
                            <button type="submit" name="removeWorkout">Remove</button>
                            </td>
                        </tr>
                       </form>
                        <%
                                }
                                  
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        %>
                    </tbody>
                </table>
</div>

</section>
<script>
    function speakText(text) {
        var utterance = new SpeechSynthesisUtterance(text);
        window.speechSynthesis.speak(utterance);
    }

    // Example: Trigger speech synthesis when the page loads
    document.addEventListener('DOMContentLoaded', function () {
        speakText("Welcome to Tietan Forge Fitness. Hello"+"${member.name}");
        speakText("Have a Great day with great moves");

    });
</script>

</body>

</html> 
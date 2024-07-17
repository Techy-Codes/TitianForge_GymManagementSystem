<%-- 
    Document   : RenewMembership
    Created on : 20-Aug-2023, 9:15:45 am
    Author     : hevin
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*, java.text.SimpleDateFormat, java.util.Date, java.util.Calendar"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Titian Forge Fitness</title>
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
    
    <script>
         function updateAmountAndDue() {
            var select = document.getElementById("membershipSelect");
            var amountInput = document.getElementById("amountInput");
            var dateInput = document.getElementById("dateInput");
            var mnt=document.getElementById("mnt");
            
            var selectedOption = select.options[select.selectedIndex];
            var amount = selectedOption.getAttribute("data-amount");
            var months = selectedOption.getAttribute("data-months");
            mnt.value =months;
            amountInput.value = amount;
            
            var currentDate = new Date();
            var dueDate = new Date(currentDate);
            dueDate.setMonth(currentDate.getMonth() + parseInt(months));
            
            var dd = String(dueDate.getDate()).padStart(2, '0');
            var mm = String(dueDate.getMonth() + 1).padStart(2, '0');
            var yyyy = dueDate.getFullYear();
            
            dateInput.value = dd+ '-' + mm + '-' +yyyy ;
        }
        

    </script>
</head>
<body>
    <div class="container">
        <h2>MEMBERSHIP RENEWAL</h2>
        <form action="RenewalServlet" method="post">
        <select name="memberId" id="memberSelect" onchange="updateAmountAndDue()">      
            <%
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                String url = "jdbc:oracle:thin:@localhost:1521:XE";
                String dbUser = "system";
                String dbPass = "admin";

                Connection con = DriverManager.getConnection(url, dbUser, dbPass);

                Statement stmt = con.createStatement();
                String query = "SELECT id, name, TO_CHAR(duedate, 'dd-MM-yyyy') AS formatted_duedate FROM Member ";
                PreparedStatement pstmt = con.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery();
               
                while (rs.next()) {
                    int id = rs.getInt("id");
                     String name = rs.getString("name");
                     String formattedDuedate = rs.getString("formatted_duedate");
    %>
    <option value="<%= id %>" data-duedate="<%= formattedDuedate %>"><%= name %></option>
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
    <select id="membershipSelect" name="membershipId" onchange="updateAmountAndDue()">
        <%
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                String url = "jdbc:oracle:thin:@localhost:1521:XE";
                String dbUser = "system";
                String dbPass = "admin";

                Connection con = DriverManager.getConnection(url, dbUser, dbPass);

                Statement stmt = con.createStatement();

                String query = "SELECT membership_id,membership_name,amount,period FROM Membership ";
                PreparedStatement pstmt = con.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery();
                
                while (rs.next()) {
                    String id = rs.getString("membership_id");
                    String name = rs.getString("membership_name");
                    String amount = rs.getString("amount");
                    String months = rs.getString("period");
        %>
        <option value="<%= id %>" data-amount="<%= amount %>" data-months="<%= months %>"><%= name %></option>            
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
            <input type='text' id="amountInput" placeholder="Amount" name="amount" readonly>
            <input type='hidden' id="mnt" placeholder="Month" name="mnt" >
            <input type='text' id="dateInput" placeholder="Due" name="due" readonly>
            
            <select id="mode" name="mode" placeholder="mode">
                
                <option value="cash">CASH</option>            
                <option value="upi">UPI</option>            
            </select> 

    <input type="submit" value="RENEW">
    </form>
    </div>
</body>
</html>

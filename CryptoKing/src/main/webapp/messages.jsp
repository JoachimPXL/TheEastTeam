<%@ page import="JavaBeans.Message" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %><%--
  Created by IntelliJ IDEA.
  User: 11500555
  Date: 23/04/2017
  Time: 21:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Index</title>
    <link rel="stylesheet" href="assets/style/layout.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://use.fontawesome.com/362994279d.js"></script>
</head>
<body>
<%
    String userName = null;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("user")) userName = cookie.getValue();
        }
    }
    if (userName == null) response.sendRedirect("login.jsp");
%>

    <div class="loginImage">
        <div class="loginBar">
            <div class="content">
                <p>Verstuur een bericht</p>
                <form method="post" action="SendMessage" enctype="multipart/form-data"> <!-- TODO Actiontag invullen naar nodige link-->
                    <div class="inputField">
                        <input type="text" placeholder="Ontvanger" name="receiver" required>
                    </div>
                    <div class="inputField">
                        <input type="text" placeholder="Boodschap" name="user" required>
                    </div>
                        <input type="hidden" value="<%=userName%>" name="userName">
                    <div class="inputField" >
                        <input type="file" name="attachment" id="attachment" accept="text/plain">
                    </div>
                    <input type="submit" value="SendMessage" class="button">
                </form>
                <form method="get" action="Message">
                    <button class="button">
                        Terugkeren naar home.
                    </button>
                </form>
            </div>
    </div>
</div>

<script>
    $('#messages').scrollTop(150);
</script>
</body>
</html>

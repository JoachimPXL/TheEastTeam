<%--
  Created by IntelliJ IDEA.
  User: 11500555
  Date: 23/04/2017
  Time: 20:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="assets/style/layout.css">
</head>
<body>
<div class="loginImage">
    <div class="loginBar">
        <div class="content">
            <h1>Basic Security Messenger</h1>
            <p>Choose nickname</p>
            <form method="post" action="Login">
                <div class="inputField">
                    <input type="text" placeholder="Gebruikersnaam" required="true" maxlength="15" minlength="2"  name="user" itemscope="session">
                </div>
                <div class="inputField">
                    <input type="password" placeholder="Wachtwoord" name="pass">
                </div>
                <input type="submit" value="Login" class="button">

            </form>
            <form method="post" action="register.jsp">
            <button class="button">
                Registreren
            </button>
            </form>
        </div>
        <% if (request.getAttribute("error") != null) {%>
        <h2><%=request.getAttribute("error")%></h2>
        <% }%>

    </div>
</div>
</body>
</html>

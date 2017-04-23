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
            <p>Meld u aan met uw gebruikersnaam en wachtwoord</p>
            <form action="home.php" id="loginForm">
                <div class="inputField">
                    <input type="text" placeholder="Gebruikersnaam">
                </div>
                <div class="inputField">
                    <input type="password" placeholder="Wachtwoord">
                </div>
                <input type="submit" value="AANMELDEN" class="button">
            </form>
        </div>

    </div>
</div>
</body>
</html>

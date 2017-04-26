<%--
  Created by IntelliJ IDEA.
  User: 11500555
  Date: 23/04/2017
  Time: 20:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>Title</title>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="assets/style/layout.css">
</head>
<body>

<div class="loginImage">
    <div class="loginBar">
        <div class="content">
            <h1>Basic Security Messenger</h1>
            <p>Maak een nieuw account aan</p>
            <form method="post" id="registerForm"> <!-- TODO Actiontag invullen naar nodige link-->
                <div class="inputField">
                    <input type="text" placeholder="Username" name="user">
                </div>
                <div class="inputField">
                    <input type="email" placeholder="Email" name="email">
                </div>
                <div class="inputField">
                    <input type="password" placeholder="Password" name="pass">
                </div>
                <div class="inputField">
                    <input type="password" placeholder="Wachtwoord bevestiging">
                </div>
                <input type="submit" value="Register" class="button">
            </form>
            <c:if test="${not empty errorMessage}">
                <c:out value="${errorMessage}"/>
            </c:if>
        </div>

    </div>
</div>

</body>
</html>

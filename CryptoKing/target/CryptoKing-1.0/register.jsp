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
            <form method="post" id="registerForm" action="Register" enctype="multipart/form-data"> <!-- TODO Actiontag invullen naar nodige link-->
                <div class="inputField">
                    <input type="text" placeholder="Username" name="user">
                </div>
                <div class="inputField">
                    <input type="email" placeholder="Email" name="email">
                </div>
                <div class="inputField">
                    <input type="password" placeholder="Password" name="pass">
                </div>
                <div class="inputField" >
                    <p for="files">Upload public key</p>
                     <input type="file" name="public" id="file" class="hidden">
                </div>
                <div class="inputField">
                    <p for="files">Upload Private key</p>
                    <input type="file" name="private" id ="files" class="hidden">
                </div>
                <input type="submit" value="Register" class="button">
            </form>
        </div>
        <div class="content">
            <h3><%=request.getAttribute("error")%></h3>
        </div>

    </div>
</div>

</body>
</html>

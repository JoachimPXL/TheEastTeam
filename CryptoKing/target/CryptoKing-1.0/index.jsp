<%--
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
</head>
<body>
<div class="people">

    <%
        String userName = null;
        Cookie[] cookies = request.getCookies();
        if(cookies !=null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("user")) userName = cookie.getValue();
            }
        }
        if(userName == null) response.sendRedirect("login.jsp");
    %>

    <h1>Logged in as: <%=userName %> </h1>
    <form action="LogoutServlet" method="post">
        <input type="submit" value="Logout" >
    </form>

    <h1>Contacten</h1>
    <div class="contact">
        <div class="contactImage">
        </div>
        <div class="contactInfo">
            <h4>Wietse Collaer</h4>
            <p>Alles goed?</p>
        </div>
    </div>
    <div class="contact">
        <div class="contactImage">

        </div>
        <div class="contactInfo">
            <h4>Wietse Collaer</h4>
            <p>Alles goed?</p>
        </div>
    </div>
    <div class="contact">
        <div class="contactImage">

        </div>
        <div class="contactInfo">
            <h4>Wietse Collaer</h4>
            <p>Alles goed?</p>
        </div>
    </div>

</div>
<div class="messages" id="messages">
    <div class="headerTitle">
        <h1 class="black">Berichten</h1>

    </div>
    <div class="messageContent">
        <div class="message">
            <div class="own">
                <p>Hallo, dit is een test (received)</p>
            </div>
        </div>
        <div class="message">
            <div class="send">
                <p>Send message</p>
            </div>
        </div>
    </div>
</div>
</body>
</html>

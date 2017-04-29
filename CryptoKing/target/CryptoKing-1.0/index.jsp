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
<div class="people">

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

    <h1>Logged in as: <%=userName %>
    </h1>
    <form action="LogoutServlet" method="post">
        <input type="submit" value="Logout">
    </form>

    <h1>Recente contacten.</h1>
    <%
        ArrayList<Message> list = (ArrayList<Message>) request.getAttribute("list");
        if (list.size() > 0) {
    %>
    <div class="contact">
        <div class="contactImage">
        </div>
        <div class="contactInfo" id="contact1">
            <h4>
                <%
                    try {
                        Message e = list.get(list.size() - 1);
                        out.print(e.getSenderName());

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                %>
            </h4>
        </div>
    </div>
    <% } %>
    <%
        if (list.size() > 1) {
    %>
    <div class="contact">
        <div class="contactImage">
        </div>
        <div class="contactInfo" id="contact2">
        <h4>
            <%
                try {
                    Message e = list.get(list.size() - 2);
                    out.print(e.getSenderName());

                } catch (Exception ex) {
                    out.print("...");
                }

            %>
        </h4>
        </div>
    </div>
    <%}%>
    <%
        if (list.size() > 2) {
    %>
    <div class="contact">
        <div class="contactImage">
        </div>
        <div class="contactInfo" id="contact3">
            <h4>
                <%
                    try {
                        Message e = list.get(list.size() - 3);
                        out.print(e.getSenderName());

                    } catch (Exception ex) {
                        out.print("...");
                    }

                %>
            </h4>
        </div>
    </div>
    <%}%>

    <%
        if (list.size() > 3) {
    %>
    <div class="contact">
        <div class="contactImage">

        </div>
        <div class="contactInfo" id="contact5">
            <h4>
                <%
                    try {
                        Message e = list.get(list.size() - 4);
                        out.print(e.getSenderName());

                    } catch (Exception ex) {
                        out.print("...");
                    }

                %>
            </h4>
        </div>
    </div>
    <%}%>
    <%
        if (list.size() > 4) {
    %>
    <div class="contact">
        <div class="contactImage">

        </div>
        <div class="contactInfo" id="contact6">
            <h4>
                <%
                    try {
                        Message e = list.get(list.size() - 5);
                        out.print(e.getSenderName());

                    } catch (Exception ex) {
                        out.print("...");
                    }

                %>
            </h4>
        </div>
    </div>
    <%}%>
    <%
        if (list.size() > 5) {
    %>
    <div class="contact">
        <div class="contactImage">

        </div>
        <div class="contactInfo" id="contact7">
            <h4>
                <%
                    try {
                        Message e = list.get(list.size() - 6);
                        out.print(e.getSenderName());

                    } catch (Exception ex) {
                        out.print("...");
                    }

                %>
            </h4>
        </div>
    </div>
    <%}%>
    <%
        if (list.size() > 6) {
    %>
    <div class="contact">
        <div class="contactImage">

        </div>
        <div class="contactInfo" id="contact8">
            <h4>
                <%
                    try {
                        Message e = list.get(list.size() - 7);
                        out.print(e.getSenderName());

                    } catch (Exception ex) {
                        out.print("...");
                    }

                %>
            </h4>
        </div>
    </div>
    <%}%>
</div>
<div class="messages" id="messages">
    <div class="headerTitle">
        <h1 class="black">Berichten</h1>
    </div>
    <div class="messageContent">
        <div class="message">
            <div class="own" id="receivedMessage">
                <% if (application.getAttribute("message") != null) {%>
                <%=application.getAttribute("message")%>
                <%}%>
            </div>
        </div>
    </div>
    <div class="message">
        <div class="send" id="sendMessage">
            <% if (application.getAttribute("message") != null) {%>
            <%=application.getAttribute("message")%>
            <%}%>
        </div>
    </div>
</div>


<div class="sendBar">
    <form action="/SendMessage" class="sendForm">
        <input type="file" id="selectedFile" style="display: none">
        <input type="button" value="FOTO" class="button" onclick="document.getElementById('selectedFile').click();">
        <input type="text" placeholder="Typ hier uw bericht">
        <input type="submit" id="submitButton" style="display: none">
        <i class="fa fa-paper-plane fa-3x" onclick="document.getElementById('submitButton').click();"></i>
    </form>
</div>
</div>

<script>
    $('#messages').scrollTop(150);
</script>
</body>
</html>

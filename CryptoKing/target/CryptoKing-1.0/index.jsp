<%@ page import="JavaBeans.Chat" %>
<%@ page import="java.util.ArrayList" %>
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
    <script src="https://use.fontawesome.com/362994279d.js"></script>
</head>
<script language="javascript" type="text/javascript">
    <!--
    //-->
    function formSubmit() {
        this.submit(window.location.href = 'LogoutServlet');
    }

</script>
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

    <h1>Aangemeld als: <%=userName %>
    </h1>
    <form action="LogoutServlet" method="post">
        <button type="submit" class="button">
            Afmelden
        </button>
    </form>

    <h1>Recente contacten:</h1>
    <%
        ArrayList<Chat> chats = null;
        try {
             chats = (ArrayList<Chat>) request.getAttribute("chats");
             } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (chats.size() > 0) {
    %>
    <div class="contact">
        <div class="contactImage">
        </div>
        <div class="contactInfo" id="contact1">
            <h4>
                <%
                    try  {
                        String contact;
                        if(chats.get(0).getReceivedList().size() > 0) {
                            contact = chats.get(chats.size() - 1).getReceivedList().get(chats.get(chats.size() - 1).getReceivedList().size() - 1).getSenderName();
                        } else {
                            contact = chats.get(chats.size() - 1).getSendList().get(chats.get(0).getSendList().size() - 1).getReceiverName();
                        }
                        out.print(contact);
                    } catch (Exception ex) {
                        out.print("Geen recente contacten");
                    }
                %>
            </h4>
        </div>
    </div>
    <h4>
    <%}%>
    <% out.println(" <form action=\"messages.jsp\">\n" +
        "        <button type=\"submit\" class=\"button\">\n" +
        "            Nieuw bericht\n" +
        "        </button>\n" +
        "    </form>"); %>
    <% try {
        if(chats.get(0) != null) { %>
   <form action="DeleteChat" method="post">
    <button type="submit" class="button">Verwijder gesprek</button>
       <input type="hidden" value="<%=userName%>" name="userName">
       <input type="hidden" value="<%=chats.get(0).getSender()%>" name="receiver">
   </form>
    <% } } catch (Exception ex) { ex.printStackTrace(); } %>
</div>
<div class="messages" id="messages">
    <div class="headerTitle">
        <h1 class="black">Berichten</h1>
    </div>
    <div class="messageContent">
        <div class="message">
            <%
                try {
                if (chats.get(0).getReceivedList().size() > 2) {
            %>
            <div class="own" id="receivedMessage3">
                <h4>
                    <%
                        try {
                            String message3 = chats.get(0).getReceivedList().get(chats.get(0).getReceivedList().size() - 3).getMessage();
                            out.print(message3);
                        } catch (Exception ex) {
                            out.print("");
                        }
                    %>
                </h4>
            </div>
            <%}
            } catch (Exception ex) {
                ex.printStackTrace();
            }%>
        </div>
    </div>
    <div class="messageContent">
        <div class="message">
            <%
                try {
                if (chats.get(0).getSendList().size() > 2) {
            %>
            <div class="send" id="sendMessage3">
                <h4>
                    <%
                        try {
                            String sendMessage3 = chats.get(0).getSendList().get(chats.get(0).getSendList().size() - 3).getMessage();
                            out.print(sendMessage3);
                        } catch (Exception ex) {
                            out.print("");
                        }

                    %>
                </h4>
            </div>
            <%}
            } catch (Exception ex) {
                    ex.printStackTrace();
            }%>
            <div class="message">

                <%
                    try {
                    if (chats.get(0).getReceivedList().size() > 1) {
                %>
                <div class="own" id="receivedMessage2">
                    <h4>
                        <%
                            try {
                                String message2 = chats.get(0).getReceivedList().get(chats.get(0).getReceivedList().size() - 2).getMessage();
                                out.print(message2);
                            } catch (Exception ex) {
                                out.print("");
                            }
                        %>
                    </h4>
                </div>
                <%}
                } catch (Exception ex) {
                    ex.printStackTrace();
                }%>
            </div>
        </div>
    </div>
    <div class="messageContent">
        <div class="message">
            <%
                try {
                if (chats.get(0).getSendList().size() > 1) {
            %>
            <div class="send" id="sendMessage2">
                <h4>
                    <%
                        try {
                            String sendMessage2 = chats.get(0).getSendList().get(chats.get(0).getSendList().size() - 2).getMessage();
                            out.print(sendMessage2);
                        } catch (Exception ex) {
                            out.print("");
                        }
                    %>
                </h4>
            </div>
            <%}
            } catch (Exception ex) {
                ex.printStackTrace();
            }%>
            <div class="message">
                <%
                    try {
                    if (chats.get(0).getReceivedList().size() > 0) {
                %>
                <div class="own" id="receivedMessage1">
                    <h4>
                        <%
                            try {
                                String message = chats.get(0).getReceivedList().get(chats.get(0).getReceivedList().size() - 1).getMessage();
                                out.print(message);
                            } catch (Exception ex) {
                                out.print("");
                            }
                        %>
                    </h4>
                </div>
                <%}
                } catch (Exception ex) {
                    ex.printStackTrace();
                }%>
            </div>
        </div>
    </div>
    <div class="messageContent">
        <div class="message">
            <%
                try {
                if (chats.get(0).getSendList().size() > 0) {
            %>
            <div class="send" id="sendMessage1">
                <h4>
                    <%
                        try {
                            String sendMessage = chats.get(0).getSendList().get(chats.get(0).getSendList().size() - 1).getMessage();
                            out.print(sendMessage);
                        } catch (Exception ex) {
                            out.print("");
                        }
                    %>
                </h4>
            </div>
            <%}
            } catch (Exception ex) {
                ex.printStackTrace();
            }%>
        </div>
        <% try { %>
        <div class="sendBar">
            <form action="SendMessage" class="sendForm" method="post">
                <!--<input type="file" name="attachment" style="display: none" accept="text/plain">-->
                <input type="hidden" value="<%=userName%>" name="userName">
                <input type="hidden" value="<%=chats.get(0).getSender()%>" name="receiver">
                <!--<input type="button" value="Bijlage" class="button" onclick="document.getElementById('attachment').click();">-->
                <input type="text" name="user" placeholder="Typ hier uw bericht">
                <input type="submit" id="submitButton" style="display: none">
                <i class="fa fa-paper-plane fa-3x" onclick="document.getElementById('submitButton').click();"></i>
            </form>
        </div>
        <% } catch (Exception ex) { ex.printStackTrace(); } %>
    </div>
</div>
</div>
<script>
    $('#messages').scrollTop(150);
</script>
</body>
</html>

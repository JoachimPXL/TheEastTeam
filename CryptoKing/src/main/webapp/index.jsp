<%@ page import="JavaBeans.Message" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="JavaBeans.Chat" %><%--
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
    function initInput()
    {
        var variable = '<%= request.getAttribute("chats") %>;'
        document.getElementById("IChats").value = variable;
    }
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
        ArrayList<Message> list = null;
        Chat currentChat = null;
        try {
             chats = (ArrayList<Chat>) request.getAttribute("chats");
             list = (ArrayList<Message>) request.getAttribute("list");
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
                    try {
                        String contact = chats.get(chats.size() - 1).getReceivedList().get(chats.get(chats.size() - 1).getReceivedList().size() - 1).getSenderName();

                        out.print(contact);
                    } catch (Exception ex) {
                        out.print("Geen recente contacten");
                    }
                %>
            </h4>
        </div>
    </div>
    <h4>
    <% }
     %>
    </h4>
    <%
        if (chats.size() > 1) {
    %>
    <div class="contact">
        <div class="contactImage">
        </div>
        <div class="contactInfo" id="contact2">
            <h4>
                <%
                    try {
                        String contact2 = chats.get(chats.size() - 1).getReceivedList().get(chats.get(chats.size() - 1).getReceivedList().size() - 1).getSenderName();

                        out.print(contact2);

                    } catch (Exception ex) {
                        out.print("...");
                    }

                %>
            </h4>
        </div>
    </div>


    <%}%>
    <%
        if (chats.size() > 2) {
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
        if (chats.size() > 3) {
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
        if (chats.size() > 4) {
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
        if (chats.size() > 5) {
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
        if (chats.size() > 6) {
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
                        out.print("");
                    }
                %>
            </h4>
        </div>
    </div>
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
            <form action="SendMessage" class="sendForm" method="post" >
                <input type="file" name="fileToEncrypt" style="display: none">
                <input type="hidden" value="<%=userName%>" name="userName">
                <input type="hidden" value="<%=chats.get(0).getSender()%>" name="receiver">
                <input type="button" value="Bijlage" class="button" onclick="document.getElementById('fileToEncrypt').click();">
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

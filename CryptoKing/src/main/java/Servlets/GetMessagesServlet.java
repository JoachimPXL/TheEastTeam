package Servlets;

import JavaBeans.Message;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by 11500555 on 28/04/2017.
 */
@WebServlet("/GetMessages")
public class GetMessagesServlet extends HttpServlet {

    private String dbURL = "jdbc:mysql://213.136.26.180/u5162p3748_joa?useLegacyDatetimeCode=false&serverTimezone=UTC";
    private String dbUser = "u5162p3748_jojo";
    private String dbPass = "test123";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String userName = "";
        Cookie[] cookies = request.getCookies();
        if(cookies !=null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("user")) userName = cookie.getValue();
            }
        } else {
            response.sendRedirect("index.jsp");
        }
        try {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
        PreparedStatement pst = conn.prepareStatement("select id, username from users where username=?");
        pst.setString(1, userName);
        ResultSet rs = pst.executeQuery();
        if(rs.next()) {
            if (rs.getString("username").equals(userName)) {

                ArrayList<Message> list = new ArrayList<Message>();

                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
                pst = conn.prepareStatement("select * from messages where receiverId=?");
                pst.setInt(1, rs.getInt(1));
                rs = pst.executeQuery();

                while (rs.next()) {
                    Message message = new Message(rs.getBytes(5), rs.getString(4), rs.getInt(2), rs.getInt(3));
                    list.add(message);
                }

                out.print(list.get(0).toString());

                //request.setAttribute("id", rs.getInt(1));
                //response.sendRedirect("index.jsp");
            } else {
                request.setAttribute("error", "Wrong login credentials.");
                request.getRequestDispatcher("/login.jsp")
                        .forward(request, response);
            }
        }
        } catch (SQLException ex) {

            } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}

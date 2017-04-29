package Servlets;

import JavaBeans.Message;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by 11500555 on 29/04/2017.
 */
@WebServlet("/Message")
public class MessageServlet extends HttpServlet {
    private String dbURL = "jdbc:mysql://213.136.26.180/u5162p3748_joa?useLegacyDatetimeCode=false&serverTimezone=UTC";
    private String dbUser = "u5162p3748_jojo";
    private String dbPass = "test123";
    private int receiverId;
    private String userName;
    private ArrayList<Message> list = new ArrayList<Message>();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Validate cookies to get receiverID and userName
        Cookie[] cookies = request.getCookies();
        if(cookies !=null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("user")) userName = cookie.getValue();
                if(cookie.getName().equals("receiverId")) receiverId = Integer.parseInt(cookie.getValue());
            }
        } else {
            response.sendRedirect("login.jsp");
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        //Get received messages;
        try {
            conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
            PreparedStatement pst = conn.prepareStatement("select * from messages where receiverId=?");
            pst.setInt(1, receiverId );
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                Message message = new Message(rs.getBytes(5), rs.getString(4), rs.getInt(2), rs.getInt(3), getSenderName(rs.getInt(2)));
                //out.print(message);
                list.add(message);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
         // however you get the data
        // set the attribute in the request to access it on the JSP
        request.setAttribute("list", list);

        RequestDispatcher rd = getServletContext()
                .getRequestDispatcher("/index.jsp");
        rd.forward(request, response);
    }

    private String getSenderName(int senderId) {
        Connection conn;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
            PreparedStatement pst = conn.prepareStatement("select username from users where Id=?");
            pst.setInt(1, senderId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
}

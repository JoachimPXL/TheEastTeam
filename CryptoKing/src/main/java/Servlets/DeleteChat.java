package Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

/**
 * Created by 11500555 on 3/05/2017.
 */
@WebServlet("/DeleteChat")
public class DeleteChat extends HttpServlet {
    private String dbURL = "jdbc:mysql://213.136.26.180/u5162p3748_joa?useLegacyDatetimeCode=false&serverTimezone=UTC";
    private String dbUser = "u5162p3748_jojo";
    private String dbPass = "test123";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = req.getParameter("userName");
        String receiver = req.getParameter("receiver");

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
            PreparedStatement pst = conn.prepareStatement("DELETE FROM messages WHERE senderId=? AND receiverId=?");
            pst.setInt(1, getUserId(receiver));
            pst.setInt(2, getUserId(user));
            int result = pst.executeUpdate();
            System.out.println(result + " " + "messages verwijderd." );

            PreparedStatement pstSend = conn.prepareStatement("DELETE FROM messages WHERE senderId=? AND receiverId=?");
            pstSend.setInt(1, getUserId(user));
            pstSend.setInt(2, getUserId(receiver));
            int resultSend = pst.executeUpdate();
            System.out.println(resultSend + " " + "messages verwijderd." );
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn = null;
        }
        resp.sendRedirect(req.getContextPath() + "/Message");
    }

    private int getUserId(String user) {
        Connection conn;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
            String owner = "Jojo"; // Deadcode
            PreparedStatement pst = conn.prepareStatement("select id from users where username=?");
            pst.setString(1, user);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn = null;
        }
        return 0;
    }
}

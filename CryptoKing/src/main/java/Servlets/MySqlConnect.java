package Servlets;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * Created by 11500555 on 23/04/2017.
 */
@WebServlet("/Login")
public class MySqlConnect extends HttpServlet {

    private String dbURL = "jdbc:mysql://213.136.26.180/u5162p3748_joa?useLegacyDatetimeCode=false&serverTimezone=UTC";
    private String dbUser = "u5162p3748_jojo";
    private String dbPass = "test123";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String user = request.getParameter("user");
        String pass = request.getParameter("pass");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
            PreparedStatement pst = conn.prepareStatement("select username, password from users where username=?");
            pst.setString(1, user);
            ResultSet rs = pst.executeQuery();
            if(rs.next()) {
                if (rs.getString("username").equals(user) && rs.getString("password").equals(pass)) {
                    out.println(rs.getString("username") + rs.getString("password"));
                    //aanmaken cookie
                    Cookie loginCookie = new Cookie("user",user);
                    loginCookie.setMaxAge(30*60);
                    response.addCookie(loginCookie);
                    //response.sendRedirect("index.jsp");
                    request.getRequestDispatcher("GetMessages").forward(request,response);
                } else {
                    request.setAttribute("error", "Wrong login credentials.");
                    request.getRequestDispatcher("/login.jsp")
                    .forward(request, response);
                }
            } else {
                request.setAttribute("error", "Wrong login credentials.");
                request.getRequestDispatcher("/login.jsp")
                        .forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("SQL EXCEPTION.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

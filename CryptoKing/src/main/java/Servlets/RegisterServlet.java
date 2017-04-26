package Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * Created by 11500555 on 26/04/2017.
 */
@WebServlet("/Register")
public class RegisterServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String user = request.getParameter("user");
        String pass = request.getParameter("pass");
        String email = request.getParameter("email");
        try {
            Connection conn = getConnection();
            PreparedStatement pst = conn.prepareStatement("select username, password from users where username=?");
            pst.setString(1, user);
            ResultSet rs = pst.executeQuery();
            if(rs.next()) {
                request.setAttribute("errorMessage", "Please choose a valid username");
            } else {
                String sql = "INSERT INTO users (username, email, password) VALUES (?,?,?)";
                try (Connection con = getConnection();
                     PreparedStatement stmt = con.prepareStatement(sql)) {
                    stmt.setString(1, user);
                    stmt.setString(2, email);
                    stmt.setString(3, pass);
                    stmt.executeUpdate();
                    request.setAttribute("errorMessage", "User created, go to login to use CryptoKing.");
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    ex.printStackTrace(System.err);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("SQL EXCEPTION.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://213.136.26.180/u5162p3748_joa?useLegacyDatetimeCode=false&serverTimezone=UTC", "u5162p3748_jojo", "test123");
    }
}

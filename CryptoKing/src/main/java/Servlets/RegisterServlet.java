package Servlets;

import JavaBeans.User;
import sun.misc.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.sql.*;

/**
 * Created by 11500555 on 26/04/2017.
 */
@WebServlet("/Register")
@MultipartConfig(maxFileSize = 16177215)
public class RegisterServlet extends HttpServlet {

    private String dbURL = "jdbc:mysql://213.136.26.180/u5162p3748_joa?useLegacyDatetimeCode=false&serverTimezone=UTC";
    private String dbUser = "u5162p3748_jojo";
    private String dbPass = "test123";


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String user = request.getParameter("user");
        String pass = request.getParameter("pass");
        String email = request.getParameter("email");
        InputStream inputStreamPublic = null;    // input stream of the upload file
        InputStream inputStreamPrivate = null;

        // obtains the upload file part in this multipart request
        Part filePartPublic = request.getPart("public");
        Part filePartPrivate = request.getPart("private");

        if (filePartPublic != null && filePartPrivate != null) {
            // obtains input stream of the upload file
            inputStreamPublic = filePartPublic.getInputStream();
            inputStreamPrivate = filePartPrivate.getInputStream();
        }

        User gebruiker = new User();
        try {
            gebruiker = new User(user, pass, email, getBytesFromInputstream(inputStreamPublic), getBytesFromInputstream(inputStreamPrivate));
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "The selected keys were not added properly, please try again.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }

        try {
            Connection conn = getConnection();
            PreparedStatement pst = conn.prepareStatement("select username, password from users where username=?");
            pst.setString(1, user);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                request.setAttribute("error", "Username already in use, please choose a different username.");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
            } else {
                String sql = "INSERT INTO users (username, email, password, publicKey, privateKey) VALUES (?,?,?,?,?)";
                try (Connection con = getConnection();
                     PreparedStatement stmt = con.prepareStatement(sql)) {
                    stmt.setString(1, gebruiker.getUsername());
                    stmt.setString(2, gebruiker.getEmail());
                    stmt.setString(3, gebruiker.getPassword());
                    stmt.setBytes(4, gebruiker.getPublicKey());
                    stmt.setBytes(5, gebruiker.getPrivateKey());
                    stmt.executeUpdate();
                    request.setAttribute("error", "Account has been created!");
                    request.getRequestDispatcher("/register.jsp").forward(request, response);
                    con.close();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    ex.printStackTrace(System.err);
                }
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("SQL EXCEPTION.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private byte[] getBytesFromInputstream(InputStream inputStreamPublic) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = inputStreamPublic.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();

        return buffer.toByteArray();
    }

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(dbURL, dbUser, dbPass);
    }
}

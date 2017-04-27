package Servlets;

import JavaBeans.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.*;

/**
 * Created by 11500555 on 26/04/2017.
 */
@WebServlet("/Register")
public class RegisterServlet extends HttpServlet {

    private String dbURL = "jdbc:mysql://213.136.26.180/u5162p3748_joa?useLegacyDatetimeCode=false&serverTimezone=UTC";
    private String dbUser = "u5162p3748_jojo";
    private String dbPass = "test123";

    /*protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp")
                .forward(request, response);
    }*/

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
        //Part filePartPrivate = request.getPart("private");

        if (filePartPublic != null ) { //&& filePartPrivate != null
            // obtains input stream of the upload file
            inputStreamPublic = filePartPublic.getInputStream();
            //inputStreamPrivate = filePartPrivate.getInputStream();
        }

        try {
            // connects to the database
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass);

            // constructs SQL statement
            String sql = "INSERT INTO contacts (first_name, publick) values (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, user);

            if (inputStreamPublic != null ) { //&& inputStreamPrivate != null
                // fetches input stream of the upload file for the blob column
                statement.setBlob(2, inputStreamPublic);
                //statement.setBlob(3, inputStreamPrivate);
            }

            // sends the statement to the database server
            int row = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        User gebruiker = new User();
        try {
            //gebruiker = new User(user,pass,email, StartEncryption.getPublicKey(fileNamePublic,"RSA"),StartEncryption.getPrivateKey(fileNamePrivate,"RSA"));
        } catch (Exception e) {
            e.printStackTrace();
            out.print("ERROR bij aanmaken gebruiker. ER GING IETS MIS BIJ HET PARSEN VAN DE FILE");
        }

        try {
            Connection conn = getConnection();
            PreparedStatement pst = conn.prepareStatement("select username, password from users where username=?");
            pst.setString(1, user);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                request.setAttribute("error", "Username already in use, please choose a different username.");
                //request.getRequestDispatcher("/register.jsp").forward(request, response);
            } else {
                String sql = "INSERT INTO users (username, email, password, publicKey) VALUES (?,?,?,?)";
                try (Connection con = getConnection();
                     PreparedStatement stmt = con.prepareStatement(sql)) {
                    stmt.setString(1, gebruiker.getUsername());
                    stmt.setString(2, gebruiker.getEmail());
                    stmt.setString(3, gebruiker.getPassword());
                    stmt.setBytes(4, gebruiker.getPublicKey().getEncoded());
                    //stmt.setBytes(5, gebruiker.getPrivateKey().getEncoded());
                    stmt.executeUpdate();
                    out.print("Account has been created");
                    request.setAttribute("error", "Account has been created!");
                    //request.getRequestDispatcher("/register.jsp").forward(request, response);
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

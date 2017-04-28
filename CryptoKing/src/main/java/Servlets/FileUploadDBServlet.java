package Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by 11500555 on 26/04/2017.
 */
@WebServlet("/uploadServlet")
@MultipartConfig(maxFileSize = 16177215)
public class FileUploadDBServlet extends HttpServlet {

    // database connection settings
    private String dbURL = "jdbc:mysql://213.136.26.180/u5162p3748_joa?useLegacyDatetimeCode=false&serverTimezone=UTC";
    private String dbUser = "u5162p3748_jojo";
    private String dbPass = "test123";

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        // gets values of text fields
        String firstName = request.getParameter("firstName");
        InputStream inputStream = null;	// input stream of the upload file

        // obtains the upload file part in this multipart request
        Part filePart = request.getPart("photo");
        if (filePart != null) {
            // prints out some information for debugging
            System.out.println(filePart.getName());
            System.out.println(filePart.getSize());
            System.out.println(filePart.getContentType());

            // obtains input stream of the upload file
            inputStream = filePart.getInputStream();
        }

        Connection conn = null;	// connection to the database
        String message = null;	// message will be sent back to client

        try {
            // connects to the database
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            conn = DriverManager.getConnection(dbURL, dbUser, dbPass);

            // constructs SQL statement
            String sql = "INSERT INTO contacts (first_name, publick) values (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, firstName);

            if (inputStream != null) {
                // fetches input stream of the upload file for the blob column
                statement.setBlob(2, inputStream);
            }

            // sends the statement to the database server
            int row = statement.executeUpdate();
            if (row > 0) {
                message = "File uploaded and saved into database";
            }
        } catch (SQLException ex) {
            message = "ERROR: " + ex.getMessage();
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                // closes the database connection
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            // sets the message in request scope
            request.setAttribute("Message", message);

            // forwards to the message page
            getServletContext().getRequestDispatcher("/Message.jsp").forward(request, response);
        } // c:\xampp\tomcat\webapps\CryptoKing-1.0\data\
    }
}

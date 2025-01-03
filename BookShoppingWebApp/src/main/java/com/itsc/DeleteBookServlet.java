package com.itsc;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/deleteurl")
public class DeleteBookServlet extends HttpServlet {
    private static final String query = "DELETE FROM books WHERE id = ?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Get PrintWriter
        PrintWriter pw = resp.getWriter();
        // Set content type
        resp.setContentType("text/html");

        // Get the id of the record to delete
        int id = Integer.parseInt(req.getParameter("id"));

        // Load the JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }

        // Generate the connection and execute the delete query
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookregister", "root", "15hr12ap905")) {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);

            int count = ps.executeUpdate();
            if (count == 1) {
                pw.println("<h2>Record deleted successfully.</h2>");
                
            } else {
                pw.println("<h2>Record not deleted.</h2>");
            }
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h1>" + se.getMessage() + "</h1>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1>" + e.getMessage() + "</h1>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}

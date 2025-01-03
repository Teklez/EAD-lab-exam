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
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/BookRegistrationServlet")
public class BookRegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public BookRegistrationServlet() {
        super();
    }

    /**
     * Handles GET requests.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<h1>Book Registration Servlet</h1>");
        out.println("<p>Use the form to submit book details via POST request.</p>");
    }

    /**
     * Handles POST requests.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get form data
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String price = request.getParameter("price");

        String message;

        // Validate inputs
        if (title == null || title.isEmpty() || 
            author == null || author.isEmpty() || 
            price == null || price.isEmpty()) {
            message = "Error: All fields are required!";
            request.setAttribute("message", message);
            request.getRequestDispatcher("/Home.html").forward(request, response);
            return;
        }

        // Load the JDBC driver and connect to the database
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            message = "Database driver not found!";
            request.setAttribute("message", message);
            request.getRequestDispatcher("Home.html").forward(request, response);
            return;
        }

        // Database connection
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/BookstoreDB", "zemen", "justme")) {
            // Prepare SQL query
            String query = "INSERT INTO books (title, author, price) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);

            // Set parameters
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setString(3, price);

            // Execute update
            int rows = ps.executeUpdate();

            if (rows > 0) {
                message = "Book registered successfully!";
            } else {
                message = "Failed to register the book!";
            }
        } catch (SQLException e) {
            message = "Database connection error: " + e.getMessage();
        }

        // Forward the message back to the form
        request.setAttribute("message", message);
        request.getRequestDispatcher("/Home.jsp").forward(request, response);
    }

}
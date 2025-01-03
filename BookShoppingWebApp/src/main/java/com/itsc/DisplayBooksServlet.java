package com.itsc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/booklist")
public class DisplayBooksServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		String action = request.getParameter("action");

		if ("edit".equals(action)) {
			editBook(request, response, out);
		} else if ("delete".equals(action)) {
			deleteBook(request, response, out);
		} else {
			showBookList(out);
		}
	}

	private void showBookList(PrintWriter out) throws IOException {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			out.println("<div class='alert alert-danger'>Database driver not found!</div>");
			e.printStackTrace(out);
			return;
		}

		try (Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/StudentsDB", "zemen",
				"justme")) {
			String query = "SELECT id, name, edition, price FROM books";
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			out.println("<div class='container mt-4'>");
			out.println("<h2 class='mb-4'>Book List</h2>");
			out.println("<table class='table table-bordered table-striped'>");
			out.println("<thead class='table-dark'>");
			out.println("<tr>");
			out.println("<th>Book ID</th>");
			out.println("<th>Book title</th>");
			out.println("<th>Book author</th>");
			out.println("<th>Book price</th>");
			out.println("<th>Edit</th>");
			out.println("<th>Delete</th>");
			out.println("</tr>");
			out.println("</thead>");
			out.println("<tbody>");

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String edition = rs.getString("edition");
				float price = rs.getFloat("price");

				out.println("<tr>");
				out.println("<td>" + id + "</td>");
				out.println("<td>" + name + "</td>");
				out.println("<td>" + edition + "</td>");
				out.println("<td>" + price + "</td>");
				out.println("<td><a href='booklist?action=edit&id=" + id
						+ "' class='btn btn-warning btn-sm'>Edit</a></td>");
				out.println("<td><a href='booklist?action=delete&id=" + id
						+ "' class='btn btn-danger btn-sm'>Delete</a></td>");
				out.println("</tr>");
			}

			out.println("</tbody>");
			out.println("</table>");
			out.println("</div>");
		} catch (SQLException e) {
			e.printStackTrace(out);
		}
	}

}

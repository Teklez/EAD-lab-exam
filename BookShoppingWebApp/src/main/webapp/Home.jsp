<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Book Registration</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(document).ready(function() {
            // Fetch the book list when the button is clicked
            $("#viewBookListBtn").click(function() {
                $.ajax({
                    url: "booklist",  // Servlet URL
                    type: "GET",
                    success: function(data) {
                        $("#bookList").html(data); // Update the book list div with the response
                    },
                    error: function(xhr, status, error) {
                        console.log("Error: " + error);
                    }
                });
            });
        });
    </script>
</head>
<body class="bg-light">
    <!-- Registration Form -->
    <div class="card mx-auto mt-5" style="width: 40rem;">
        <div class="card-header bg-primary text-white text-center">
            <h2>Book Registration</h2>
        </div>
        <div class="card-body">
            <c:if test="${not empty message}">
                <div class="alert alert-info text-center">
                    ${message}
                </div>
            </c:if>
            <form action="RegisterServlet" method="post">
                <div class="mb-3">
                    <label for="bookName" class="form-label">Book Name</label>
                    <input type="text" class="form-control" id="bookName" name="bookName" placeholder="Enter book name" required>
                </div>
                <div class="mb-3">
                    <label for="bookEdition" class="form-label">Book Edition</label>
                    <input type="text" class="form-control" id="bookEdition" name="bookEdition" placeholder="Enter book edition" required>
                </div>
                <div class="mb-3">
                    <label for="bookPrice" class="form-label">Book Price</label>
                    <input type="number" class="form-control" id="bookPrice" name="bookPrice" placeholder="Enter book price" step="0.01" required>
                </div>
                <div class="d-flex justify-content-between">
                    <button type="submit" class="btn btn-success">Register</button>
                    <button type="reset" class="btn btn-secondary">Cancel</button>
                </div>
            </form>
        </div>
    </div>
    
    <!-- Button to fetch book list -->
    <div class="text-center mt-3">
        <button id="viewBookListBtn" class="btn btn-info">View Book List</button>
    </div>

    <!-- Book List Section (Initially empty) -->
    <div id="bookList" class="card mx-auto mt-5" style="width: 80rem;">
        <div class="card-header bg-info text-white text-center">
            <h2>Book List</h2>
        </div>
        <div class="card-body">
            <!-- The book list will be inserted here by AJAX -->
        </div>
    </div>
</body>
</html>

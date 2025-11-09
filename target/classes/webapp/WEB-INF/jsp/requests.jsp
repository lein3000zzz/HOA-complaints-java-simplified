<%--
  Created by IntelliJ IDEA.
  User: Vladislav
  Date: 11/9/2025
  Time: 11:26 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List,org.severov_v.entities.Request" %>
<%
    @SuppressWarnings("unchecked")
    List<Request> requests = (List<Request>) request.getAttribute("requests");
    if (requests == null) {
        requests = java.util.Collections.emptyList();
    }
    String error = (String) request.getAttribute("error");
    String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Requests</title>
    <link rel="stylesheet" href="<%= ctx %>/css/style.css">
</head>
<body>
<h1>Requests</h1>

<% if (error != null) { %>
<div class="error"><%= error %></div>
<% } %>

<section>
    <form class="inline" method="get" action="<%= ctx %>/requests">
        <input type="hidden" name="action" value="read"/>
        <label>By ID:
            <input type="number" name="id" min="1"/>
        </label>
        <button type="submit">Load</button>
    </form>
    <form class="inline" method="get" action="<%= ctx %>/requests">
        <button type="submit" title="List all requests">List All</button>
    </form>
</section>

<table>
    <tr>
        <th>ID</th>
        <th>Complaining ID</th>
        <th>Type</th>
        <th>House Address</th>
        <th>Complaint Text</th>
        <th>Status</th>
        <th>Actions</th>
    </tr>
    <% if (requests.isEmpty()) { %>
    <tr><td colspan="7" class="empty">No requests found.</td></tr>
    <% } else {
        for (Request r : requests) { %>
    <tr>
        <td><%= r.getId() %></td>
        <td><%= r.getIdComplaining() %></td>
        <td><%= r.getType() == null ? "" : r.getType() %></td>
        <td><%= r.getHouseAddress() == null ? "" : r.getHouseAddress() %></td>
        <td><%= r.getComplaintText() == null ? "" : r.getComplaintText() %></td>
        <td><%= r.getStatus() == null ? "" : r.getStatus() %></td>
        <td>
            <a href="<%= ctx %>/requests?action=edit&id=<%= r.getId() %>">Edit</a>
            <a href="<%= ctx %>/requests?action=delete&id=<%= r.getId() %>"
               onclick="return confirm('Delete request #<%= r.getId() %>?');">Delete</a>
        </td>
    </tr>
    <%  } } %>
</table>

<% Request editRequest = (Request) request.getAttribute("request"); %>
<% if (editRequest != null) { %>
<h2>Edit Request</h2>
<form method="post" action="<%= ctx %>/requests" accept-charset="UTF-8">
    <input type="hidden" name="id" value="<%= editRequest.getId() %>"/>
    <fieldset>
        <label>
            Complaining ID:
            <input type="number" name="idComplaining" value="<%= editRequest.getIdComplaining() %>" min="0" required/>
        </label><br/>
        <label>
            Type:
            <select name="type" required>
                <option value="APARTMENT" <%= "APARTMENT".equals(editRequest.getType().toString()) ? "selected" : "" %>>APARTMENT</option>
                <option value="HOUSE" <%= "HOUSE".equals(editRequest.getType().toString()) ? "selected" : "" %>>HOUSE</option>
            </select>
        </label><br/>
        <label>
            House Address:
            <input type="text" name="houseAddress" value="<%= editRequest.getHouseAddress() %>" required/>
        </label><br/>
        <label>
            Complaint Text:
            <textarea name="complaintText" rows="3" required><%= editRequest.getComplaintText() %></textarea>
        </label><br/>
        <label>
            Status:
            <select name="status" required>
                <option value="CREATED" <%= "CREATED".equals(editRequest.getStatus().toString()) ? "selected" : "" %>>CREATED</option>
                <option value="ASSIGNED" <%= "ASSIGNED".equals(editRequest.getStatus().toString()) ? "selected" : "" %>>ASSIGNED</option>
                <option value="COMPLETED" <%= "COMPLETED".equals(editRequest.getStatus().toString()) ? "selected" : "" %>>COMPLETED</option>
            </select>
        </label><br/>
        <button type="submit">Update</button>
    </fieldset>
</form>
<% } %>

<h2>Create Request</h2>
<form method="post" action="<%= ctx %>/requests" accept-charset="UTF-8">
    <fieldset>
        <label>
            Complaining ID:
            <input type="number" name="idComplaining" min="0" required/>
        </label><br/>
        <label>
            Type:
            <select name="type" required>
                <option value="APARTMENT">APARTMENT</option>
                <option value="HOUSE">HOUSE</option>
            </select>
        </label><br/>
        <label>
            House Address:
            <input type="text" name="houseAddress" required/>
        </label><br/>
        <label>
            Complaint Text:
            <textarea name="complaintText" rows="3" required></textarea>
        </label><br/>
        <button type="submit">Create</button>
    </fieldset>
</form>

<p><a href="<%= ctx %>/residents">Residents</a></p>
</body>
</html>
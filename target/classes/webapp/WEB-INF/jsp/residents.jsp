<%--
  Created by IntelliJ IDEA.
  User: Vladislav
  Date: 11/9/2025
  Time: 11:25 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List,org.severov_v.entities.Resident" %>
<%
    @SuppressWarnings("unchecked")
    List<Resident> residents = (List<Resident>) request.getAttribute("residents");
    if (residents == null) {
        residents = java.util.Collections.emptyList();
    }
    String error = (String) request.getAttribute("error");
    String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Residents</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
<h1>Residents</h1>

<% if (error != null) { %>
<div class="error"><%= error %></div>
<% } %>

<section>
    <form class="inline" method="get" action="<%= ctx %>/residents">
        <input type="hidden" name="action" value="read"/>
        <label>By ID:
            <input type="number" name="id" min="1" />
        </label>
        <button type="submit">Load</button>
    </form>
    <form class="inline" method="get" action="<%= ctx %>/residents">
        <button type="submit" title="List all residents">List All</button>
    </form>
</section>

<table>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Phone</th>
        <th>Actions</th>
    </tr>
    <% if (residents.isEmpty()) { %>
    <tr><td colspan="4" class="empty">No residents found.</td></tr>
    <% } else {
        for (Resident r : residents) { %>
    <tr>
        <td><%= r.getId() %></td>
        <td><%= r.getFullName() %></td>
        <td><%= r.getPhoneNumber() == null ? "" : r.getPhoneNumber() %></td>
        <td>
            <a href="<%= ctx %>/residents?action=edit&id=<%= r.getId() %>">Edit</a>
            <a href="<%= ctx %>/residents?action=delete&id=<%= r.getId() %>"
               onclick="return confirm('Delete resident #<%= r.getId() %>?');">Delete</a>
        </td>
    </tr>
    <%  } } %>
</table>

<% Resident editResident = (Resident) request.getAttribute("resident"); %>
<% if (editResident != null) { %>
<h2>Edit Resident</h2>
<form method="post" action="<%= ctx %>/residents" accept-charset="UTF-8">
    <input type="hidden" name="id" value="<%= editResident.getId() %>"/>
    <fieldset>
        <label>
            Name:
            <input type="text" name="name" value="<%= editResident.getFullName() %>" required/>
        </label><br/>
        <label>
            Phone:
            <input type="text" name="number" value="<%= editResident.getPhoneNumber() %>"/>
        </label><br/>
        <button type="submit">Update</button>
    </fieldset>
</form>
<% } %>

<h2>Create Resident</h2>
<form method="post" action="<%= ctx %>/residents" accept-charset="UTF-8">
    <fieldset>
        <label>
            Name:
            <input type="text" name="name" required/>
        </label><br/>
        <label>
            Phone:
            <input type="text" name="number" placeholder="+79205687648"/>
        </label><br/>
        <button type="submit">Create</button>
    </fieldset>
</form>

<p><a href="<%= ctx %>/requests">Requests</a></p>
</body>
</html>
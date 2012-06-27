<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page session="false" %>

<html>
<body>
    <h1>List Of Recipes</h1>
    <table>
    <tr>
        <th>Recipe</th>
        <th>Author</th>
        <th>Date Created</th>
    </tr>
    <c:forEach items="${recipes}" var="recipe">
    <tr>
        <td>${recipe.name}</td>
        <td>${recipe.author}</td>
        <td>${recipe.dateCreated}</td>
        <td>
            <a href="/isis/delete/${recipe.id}">DELETE</a>
        </td>
        <td>
            <a href="/isis/update/${recipe.id}">EDIT</a>
        </td>
    </tr>
    </c:forEach>
    </table>
    <a href="/isis/newrecipe">Add New</a>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: Vladimir
  Date: 29.03.2017
  Time: 22:59
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
  <title>Meal list</title>
    <style type="text/css">
        .notExceeded{
            color: #0f6;
        }
        .exceeded {
            color: #f00;
        }
    </style>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>Meal list</h2>


            <table border="1" cellpadding="8" cellspacing="0" width="80%">
                <thead>
                <tr>
                    <th width="40%">Meal name</th>
                    <th width="20%">Date and time</th>
                    <th width="20%">Calories</th>
                </tr>
                </thead>
                <c:forEach items="${list}" var="meal">
                    <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.MealWithExceed"/>

                    <tr class="${meal.exceed ? 'exceeded' : 'notExceeded'}">
                        <td>${meal.description}</td>
                        <td>
                            <fmt:parseDate value="${meal.dateTime}"
                                           pattern="y-M-dd'T'H:m" var="parsedDate"/>
                            <fmt:formatDate value="${parsedDate}" pattern="yyyy.MM.dd HH:mm"/>
                        </td>
                        <td>${meal.calories}</td>
                    </tr>
                </c:forEach>

            </table>




</body>
</html>

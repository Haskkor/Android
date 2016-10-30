<%-- 
    Document   : index
    Created on : 14 févr. 2015, 10:08:24
    Author     : Jérémy
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SupTracking</title>
    </head>
    <body>
        <ul>
            <li>
                <c:url value="/offer-page" var="offerPageUrl" />
                <a href="${offerPageUrl}">Offer Page</a>
            </li>
            <li>
                <c:choose>
                    <c:when test="${not empty user}">
                        <c:url value="/logout" var="logoutUrl" />
                        <a href="${logoutUrl}">Logout</a>
                    </c:when>
                    <c:otherwise>
                        <c:url value="/login" var="loginUrl" />
                        <a href="${loginUrl}">Login</a>
                    </c:otherwise>
                </c:choose>
            </li>
            <li>
                <c:choose>
                    <c:when test="${empty user}">
                        <c:url value="/register" var="registerUrl" />
                        <a href="${registerUrl}">Register</a>
                    </c:when>
                </c:choose>
            </li>
        </ul>
    </body>
</html>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Excursions">
<jsp:attribute name="body">

    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>Date from</th>
            <th>Duration in hours</th>
            <th>Price</th>
            <th>Description</th>
            <th>Create date</th>
            <th>Place</th>
            <th>Type of excursion</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${excursions}" var="excursion">
            <tr>
                <td>${excursion.id}</td>
                <td><fmt:formatDate value="${excursion.fromDate}" pattern="dd. MM. yyyy"/></td>
                <td><c:out value="${excursion.durationInHours}"/></td>
                <td><c:out value="${excursion.price}"/></td>
                <td><c:out value="${excursion.description}"/></td>
                <td><fmt:formatDate value="${excursion.created}" pattern="dd. MM. yyyy"/></td>
                <td><c:out value="${excursion.place}"/></td>
                <td><c:out value="${excursion.excursionType}"/></td>
                <td><a href="/admin/excursion/delete">Delete</a></td>
           </tr>
        </c:forEach>
        </tbody>
    </table>

</jsp:attribute>
</my:pagetemplate>
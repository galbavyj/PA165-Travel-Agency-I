<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Trips">
<jsp:attribute name="body">

    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>Country</th>
            <th>City</th>
            <th>Street</th>
            <th>Number of house</th>
            <th>From</th>
            <th>To</th>
            <th>Price</th>
            <th>Created</th>
            <th>Picture</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${trips}" var="trip">
            <tr>
                <td>${trip.id}</td>
                <td><c:out value="${trip.addressOfHotel.country}"/></td>
                <td><c:out value="${trip.addressOfHotel.city}"/></td>
                <td><c:out value="${trip.addressOfHotel.street}"/></td>
                <td><c:out value="${trip.addressOfHotel.numberOfHouse}"/></td>
                <td><fmt:formatDate value="${trip.fromDate}" pattern="dd. MM. yyyy"/></td>
                <td><fmt:formatDate value="${trip.toDate}" pattern="dd. MM. yyyy"/></td>
                <td><c:out value="${trip.price}"/></td>
                <td><fmt:formatDate value="${trip.createdDate}" pattern="dd. MM. yyyy"/></td>
                <td><c:out value="${trip.filePathToPicture}"/></td>
                <td><a href="/admin/customer/delete/${trip.id}">Delete</a></td>
           </tr>
        </c:forEach>
        </tbody>
    </table>

</jsp:attribute>
</my:pagetemplate>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<my:pagetemplate title="Reservations">
<jsp:attribute name="body">
    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>Trip</th>
            <th>Total price</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${reservations}" var="reservation">
            <tr>
                <td><c:out value="${reservation.id}"/></td>
                <td><c:out value="${reservation.trip.addressOfHotel.city}"/></td>
                <td><c:out value="${reservation.totalPrice}"/> EUR</td>
                <td>
                    <a href="/pa165/shopping/reservation/${reservation.id}" class="btn btn-primary">Detail</a>
                </td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}/shopping/reservations/${reservation.id}/delete}">
                        <button type="submit" class="btn btn-primary">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</jsp:attribute>
</my:pagetemplate>


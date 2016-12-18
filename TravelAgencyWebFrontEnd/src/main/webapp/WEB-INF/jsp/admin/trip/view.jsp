<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Product Administration">
<jsp:attribute name="body">

    <form method="post" action="${pageContext.request.contextPath}/admin/trip/delete/${trip.id}">
        <button type="submit" class="btn btn-primary">Delete</button>
    </form>


    <table class="table">
        <thead>
        <tr>
            <th>id</th>
            <th>from</th>
            <th>to</th>
            <th>created</th>
            <th>country</th>
            <th>street</th>
            <th>number of house</th>
            <th>price</th>
        </tr>
        </thead>
        <tbody>
            <tr>
                <td>${trip.id}</td>
                <td><fmt:formatDate value="${trip.fromDate}" pattern="yyyy-MM-dd"/></td>
                <td><fmt:formatDate value="${trip.toDate}" pattern="yyyy-MM-dd"/></td>
                <td><fmt:formatDate value="${trip.createdDate}" pattern="yyyy-MM-dd"/></td>
                <td><c:out value="${trip.addressOfHotel.country}"/></td>
                <td><c:out value="${trip.addressOfHotel.street}"/></td>
                <td><c:out value="${trip.addressOfHotel.numberOfHouse}"/></td>
                <td><c:out value="${trip.price}"/></td>
            </tr>
        </tbody>
    </table>

        <div class="row">
            <div class="col-xs-6">
                <table class="table">
                    <caption>Possible excursions</caption>
                    <thead>
                    <tr>
                        <th>id</th>
                        <th>description</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${possibleExcursions}" var="excursion">
                        <tr>
                            <td>${excursion.id}</td>
                            <td><c:out value="${excursion.description}"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

</jsp:attribute>
</my:pagetemplate>
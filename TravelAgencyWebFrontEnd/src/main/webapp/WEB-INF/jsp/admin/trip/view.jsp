<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Trip detail">
<jsp:attribute name="body">
    <table>
    <tr><td><form method="post" action="${pageContext.request.contextPath}/admin/trip/delete/${trip.id}">
        <button type="submit" class="btn btn-primary">Delete</button>
            </form></td>
    <td><form method="get" action="${pageContext.request.contextPath}/admin/trip/edit/${trip.id}">
                    <button type="submit" class="btn btn-primary">Edit</button>
                    </form></td>
    <td><form method="get" action="${pageContext.request.contextPath}/admin/trip/uploadForm/${trip.id}">
        <button type="submit" class="btn btn-primary">Upload picture</button>
        </form></td></tr>
    </table>

    <table class="table">
        <thead>
        <tr>
            <th>Id</th>
            <th>From</th>
            <th>To</th>
            <th>Created</th>
            <th>Country</th>
            <th>Street</th>
            <th>Number of house</th>
            <th>Price</th>
        </tr>
        </thead>
        <tbody>
            <tr>
                <td>${trip.id}</td>
                <td><fmt:formatDate value="${trip.fromDate}" pattern="dd. MM. yyyy"/></td>
                <td><fmt:formatDate value="${trip.toDate}" pattern="dd. MM. yyyy"/></td>
                <td><fmt:formatDate value="${trip.createdDate}" pattern="dd. MM. yyyy"/></td>
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
                        <th>Id</th>
                        <th>Place</th>
                        <th>Description</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${possibleExcursions}" var="excursion">
                        <tr>
                            <td>${excursion.id}</td>
                            <td><c:out value="${excursion.place}"/></td>
                            <td><c:out value="${excursion.description}"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="col-xs-6">
                    <div class="panel panel-default">
                    <div class="panel-body">
                      
                <img class="img-responsive img-rounded" src="<c:url value="${trip.filePathToPicture}"/>" height="550" width="550"/>
                    </div>
                    </div>
            </div>
        </div>

</jsp:attribute>
</my:pagetemplate>
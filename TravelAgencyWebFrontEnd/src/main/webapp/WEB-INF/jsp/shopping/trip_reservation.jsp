<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Trip reservation">
<jsp:attribute name="body">

    <form:form method="post" action="${pageContext.request.contextPath}/shopping/makeReservation" modelAttribute="createReservation">
        <!--<input type="hidden" name="trip" value="${trip}"/>-->
        <form:hidden path="tripID"/>
    <div class="row">
        <div class="col-xs-5">
            <h2><c:out value="${trip.addressOfHotel.city}"/></h2>
            <br />
            <h4>Destination: <c:out value="${trip.addressOfHotel.country}"/></h4>
            <h4>
                Date:
                <fmt:formatDate value="${trip.fromDate}" pattern="dd.MM.yyyy"/>
                -
                <fmt:formatDate value="${trip.toDate}" pattern="dd.MM.yyyy"/>
            </h4>
            <br />
            <h4>Price: <c:out value="${trip.price}"/> EUR</h4>
            <button class="btn btn-primary" type="submit">Create reservation</button>
        </div>
        
        <div class="col-xs-6">
            <table class="table">
                <c:if test="${countExcursion > 0}">
                <caption>Step 2 : Select excursions.</caption>
                <thead>
                    <tr>
                        <th></th>
                        <th>Place</th>
                        <th>Date</th>
                        <th>Type</th>
                        <th>Dur</th>
                        <th>Price</th>
                    </tr>
                </thead>
                </c:if>
                <tbody>
                <c:forEach items="${trip.possibleExcursions}" var="excursion">
                    <tr>
                        <td><form:checkbox path="excursionsID" value="${excursion.id}"/></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/shopping/excursion/${excursion.id}">
                                <c:out value="${excursion.place}"/>
                            </a>
                        </td>
                        <td><fmt:formatDate value="${excursion.fromDate}" pattern="dd.MM.yyyy"/></td>
                        <td><c:out value="${excursion.excursionType}"/></td>
                        <td><c:out value="${excursion.durationInHours}"/>h</td>
                        <td><c:out value="${excursion.price}"/> EUR</td>
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
    <br />
    <br />

    </form:form>
</jsp:attribute>
</my:pagetemplate>





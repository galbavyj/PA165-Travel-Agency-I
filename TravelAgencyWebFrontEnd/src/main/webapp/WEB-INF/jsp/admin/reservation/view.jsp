<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Reservation info">
<jsp:attribute name="body">

    <div class="row">
        <div class="col-xs-5">
            <h2>
                <a href="${pageContext.request.contextPath}/admin/trip/view/${reservation.trip.id}">
                    <c:out value="${reservation.trip.addressOfHotel.country}"/>
                </a>
            </h2>
            <h4>
                Destination: <c:out value="${reservation.trip.addressOfHotel.city}"/>
            </h4>
            <h4>
                Date:
                <fmt:formatDate value="${reservation.trip.fromDate}" pattern="dd.MM.yyyy"/>
                -
                <fmt:formatDate value="${reservation.trip.toDate}" pattern="dd.MM.yyyy"/>
            </h4>
            <h4>
                Price: <c:out value="${reservation.trip.price}"/> EUR
            </h4>
        </div>
        <div class="col-xs-6">
            <table class="table">
            <c:if test="${countExcursion > 0}">
                <caption>Excursions:</caption>
                <thead>
                        <tr>
                            <th>Place</th>
                            <th>Date</th>
                            <th>Type</th>
                            <th>Dur</th>
                            <th>Price</th>
                        </tr>
                </thead>
                </c:if>
                <tbody>
                <c:forEach items="${reservation.excursions}" var="excursion">
                    <tr>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/excursion/view/${excursion.id}">
                                <c:out value="${excursion.place}"/>
                            </a>
                        </td>
                        <td>
                           <fmt:formatDate value="${excursion.fromDate}" pattern="dd.MM.yyyy"/>

                        </td>
                        <td>
                            <c:out value="${excursion.excursionType}"/>
                        </td>
                        <td>
                            <c:out value="${excursion.durationInHours}"/>h
                        </td>
                        <td>
                            <c:out value="${excursion.price}"/> EUR
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

    </div>
    <h3>
        Total price: <span style="color: green; font-weight: bold;"><c:out value="${reservation.totalPrice}"/>&nbsp;EUR</span>
    </h3>

    </jsp:attribute>
</my:pagetemplate>

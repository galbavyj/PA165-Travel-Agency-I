<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Trip reservation">
<jsp:attribute name="body">

    <form:form method="post" action="${pageContext.request.contextPath}/shopping/reserve" modelAttribute="createReservation">
        <!--<input type="hidden" name="tripId" value="${trip.id}"/>-->
        <form:hidden path="tripId"/>
    <div class="row">
        <div class="col-xs-5">
            <h2>
                <c:out value="${trip.name}"/>
            </h2>
            <h5>
                  <c:out value="${trip.description}"/>
            </h5>
            <br />
            <h4>
                Destination: <c:out value="${trip.destination}"/>
            </h4>
            <h4>
                Date:
                <fmt:formatDate value="${trip.dateFrom}" pattern="dd.MM.yyyy"/>
                -
                <fmt:formatDate value="${trip.dateTo}" pattern="dd.MM.yyyy"/>

            </h4>
            <br />
            <h4>
                Price: <c:out value="${trip.price}"/> EUR
            </h4>
        </div>
        <div class="col-xs-6">
            <table class="table">
                <caption>Step 2 : Select excursions.</caption>
                <tbody>
                <c:forEach items="${trip.excursions}" var="excursion">
                    <tr>    <!--CHANGE PATH -->
                        <td><form:checkbox path="excursionsId" value="${excursion.id}"/></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/shopping/excursion/${excursion.id}">
                                <c:out value="${excursion.name}"/>
                            </a>
                        </td>
                        <td>
                           <fmt:formatDate value="${excursion.dateFrom}" pattern="dd.MM.yyyy"/>
                           -
                           <fmt:formatDate value="${excursion.dateTo}" pattern="dd.MM.yyyy"/>
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

    <br />
    <br />
    <button class="btn btn-primary" type="submit">Create reservation</button>

    </form:form>
</jsp:attribute>
</my:pagetemplate>





<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Available trips">
<jsp:attribute name="body">
    <div class="row">
    <div class="col-xs-12">
        <table class="table">
            <caption>Step 1 : For creating reservation select one trip.</caption>
                <tbody>
                <c:forEach items="${trips}" var="trip">
                <tr>

                   <td>
                       <h4>
                       <a href="${pageContext.request.contextPath}/shopping/trip/${trip.id}">
                            <c:out value="${trip.name}"/>
                       </a>
                       <h4>
                   </td>
                   <td>
                        <h4>
                            <c:out value="${trip.destination}"/>
                        <h4>
                   </td>
                   <td>
                        <h4>

                        <fmt:formatDate value="${trip.dateFrom}" type="date" dateStyle="MEDIUM"/>
                        -
                        <fmt:formatDate value="${trip.dateTo}" type="date" dateStyle="MEDIUM"/>
                        <h4>
                   </td>
                   <td>
                        <h4>
                           <c:out value="${trip.price}"/> EUR
                        <h4>
                    </td>
                    </h4>
                   </tr>
                </c:forEach>
                </tbody>
            </table>
            </div>
    </div>
</jsp:attribute>
</my:pagetemplate>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Excursion info">
<jsp:attribute name="body">

    <div class="row">
        <div class="col-xs-5">
            <h2>
                <c:out value="${excursion.place}"/>
            </h2>
            <h5>
                  <c:out value="${excursion.description}"/>
            </h5>
            <br />
            <h4>
                Date: <fmt:formatDate value="${excursion.fromDate}" pattern="dd.MM.yyyy"/>
            </h4>
            <h4>
                Duration: <c:out value="${excursion.durationInHours}"/>h
            </h4>
            <h4>
                Type: <c:out value="${excursion.excursionType}"/>
            </h4>
            <br />
            <h4>
                Price: <c:out value="${excursion.price}"/> EUR
            </h4>
        </div>
    </div>
</jsp:attribute>
</my:pagetemplate>

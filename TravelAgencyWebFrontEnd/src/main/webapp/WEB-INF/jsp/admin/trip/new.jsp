<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="New trip">
<jsp:attribute name="body">

    <form:form method="post" action="${pageContext.request.contextPath}/admin/trip/create"
               modelAttribute="tripCreate" cssClass="form-horizontal">
        <div class="form-group">
            <form:label path="possibleExcursionsId" cssClass="col-sm-2 control-label">Possible excursions</form:label>
            <c:forEach items="${excursions}" var="e">
                    <tr>
                        <td><form:checkbox path="possibleExcursionId" value="${e.id}"/></td>
                        <td>
                                <c:out value="${e.place}"/>
                        </td>
                        
                    </tr><br>
           </c:forEach>
        </div>
        <div class="form-group ${name_error?'has-error':''}">
            <form:label path="fromDate" cssClass="col-sm-2 control-label">From</form:label>
            <div class="col-sm-10">
                <form:input type="date" path="fromDate" cssClass="form-control"/>
                <form:errors path="fromDate" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="toDate" cssClass="col-sm-2 control-label">To</form:label>
            <div class="col-sm-10">
                <form:input type="date" path="toDate" cssClass="form-control"/>
                <form:errors path="toDate" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group" >
            <form:label path="country" cssClass="col-sm-2 control-label">Country</form:label>
            <div class="col-sm-10">
                <form:input path="country" cssClass="form-control"/>
                <form:errors path="country" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group" >
            <form:label path="city" cssClass="col-sm-2 control-label">city</form:label>
            <div class="col-sm-10">
                <form:input path="city" cssClass="form-control"/>
                <form:errors path="city" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group" >
            <form:label path="street" cssClass="col-sm-2 control-label">street</form:label>
            <div class="col-sm-10">
                <form:input path="street" cssClass="form-control"/>
                <form:errors path="street" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group" >
            <form:label path="numberOfHouse" cssClass="col-sm-2 control-label">Number of house</form:label>
            <div class="col-sm-10">
                <form:input path="numberOfHouse" cssClass="form-control"/>
                <form:errors path="numberOfHouse" cssClass="help-block"/>
            </div>
        </div>

        <div class="form-group ${price_error?'has-error':''}" >
            <form:label path="price" cssClass="col-sm-2 control-label">Price</form:label>
            <div class="col-sm-10">
                <form:input path="price" cssClass="form-control"/>
                <form:errors path="price" cssClass="help-block"/>
            </div>
        </div>


        <button class="btn btn-primary" type="submit">Create trip</button>
    </form:form>

</jsp:attribute>
</my:pagetemplate>
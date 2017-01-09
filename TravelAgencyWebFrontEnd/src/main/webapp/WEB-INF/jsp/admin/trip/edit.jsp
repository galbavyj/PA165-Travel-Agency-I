<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Updating trip">
<jsp:attribute name="body">

    <form:form method="post" action="${pageContext.request.contextPath}/admin/trip/update/${tripEdit.id}"
               modelAttribute="tripEdit" cssClass="form-horizontal">
        <div class="form-group">
            <form:label path="possibleExcursionId" cssClass="col-sm-2 control-label">Possible excursions</form:label>
            <table>
            <c:forEach items="${excursions}" var="e">        
                    <tr>
                        <td><form:checkbox path="possibleExcursionId" value="${e.id}"/></td>
                        <td><c:out value="${e.place}"/></td>
                        <td>(<fmt:formatDate value="${e.fromDate}" pattern="dd. MM. yyyy"/>)</td>
                    </tr>
           </c:forEach>
            </table>
        </div>
        <c:if test="${excursionDateFail}">
            <center><font color="red">Selected possible excursion has to be accessible in trip date</font></center>
        </c:if>
       <div class="form-group">
            <form:label path="createdDate" cssClass="col-sm-2 control-label">Created</form:label>
            <div class="col-sm-10">
                <form:input type="date" path="createdDate" cssClass="form-control" readonly="true"/>
            </div>
        </div>
        <div class="form-group ${fromDate_error?'has-error':''}">
            <form:label path="fromDate" cssClass="col-sm-2 control-label">From</form:label>
            <div class="col-sm-10">
                <form:input type="date" path="fromDate" cssClass="form-control"/>
                <form:errors path="fromDate" cssClass="help-block"/>
            </div>
        </div>
        <c:if test="${dateFail}">
            <center><font color="red">toDate can't be before fromDate</font></center>
        </c:if>
        <div class="form-group ${toDate_error?'has-error':''}">
            <form:label path="toDate" cssClass="col-sm-2 control-label">To</form:label>
            <div class="col-sm-10">
                <form:input type="date" path="toDate" cssClass="form-control"/>
                <form:errors path="toDate" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group ${country_error?'has-error':''}">
            <form:label path="country" cssClass="col-sm-2 control-label">Country</form:label>
            <div class="col-sm-10">
                <form:input path="country" cssClass="form-control"/>
                <form:errors path="country" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group ${city_error?'has-error':''}" >
            <form:label path="city" cssClass="col-sm-2 control-label">city</form:label>
            <div class="col-sm-10">
                <form:input path="city" cssClass="form-control"/>
                <form:errors path="city" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group ${street_error?'has-error':''}" >
            <form:label path="street" cssClass="col-sm-2 control-label">street</form:label>
            <div class="col-sm-10">
                <form:input path="street" cssClass="form-control"/>
                <form:errors path="street" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group ${numberOfHouse_error?'has-error':''}" >
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
      
        <form:label path="filePathToPicture" cssClass="col-sm-2 control-label">Filepath to picture</form:label>
        <div class="col-sm-10">
            <form:input path="filePathToPicture" cssClass="form-control" readonly="true"/>
        </div>

        <button class="btn btn-primary" type="submit">Update trip</button>
    </form:form>

</jsp:attribute>
</my:pagetemplate>
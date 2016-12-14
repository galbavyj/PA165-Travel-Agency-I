<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Create an excursion">
<jsp:attribute name="body">

    <form:form method="post" action="${pageContext.request.contextPath}/admin/excursion/create"
               modelAttribute="excursionCreate" cssClass="form-horizontal">
        <div class="form-group">
            <form:label path="id" cssClass="col-sm-2 control-label">Trips</form:label>
            <div class="col-sm-10">
                <form:select path="id" cssClass="form-control">
                    <c:forEach items="${trips}" var="t">
                        <form:option value="${t.id}">${t.name}</form:option>
                    </c:forEach>
                </form:select>
                <p class="help-block"><form:errors path="id" cssClass="error"/></p>
            </div>
        </div>
        <div class="form-group ${place_error?'has-error':''}">
            <form:label path="place" cssClass="col-sm-2 control-label">Place</form:label>
            <div class="col-sm-10">
                <form:input path="place" cssClass="form-control"/>
                <form:errors path="place" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group ${description_error?'has-error':''}">
            <form:label path="description" cssClass="col-sm-2 control-label">Description</form:label>
            <div class="col-sm-10">
                <form:textarea cols="80" rows="10" path="description" cssClass="form-control"/>
                <form:errors path="description" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group ${price_error?'has-error':''}">
            <form:label path="price" cssClass="col-sm-2 control-label">Price</form:label>
            <div class="col-sm-10">
                <form:input path="price" cssClass="form-control"/>
                <form:errors path="price" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group ${fromDate_error?'has-error':''}" >         
            <form:label path="fromDate" cssClass="col-sm-2 control-label">Date from</form:label>
            <div class="col-sm-10">
                <fmt:formatDate value="${form.bean.dateProperty}"  
                type="date" 
                pattern="dd-mm-yyyy"
                var="theFormattedDate" />
                <form:input path="fromDate"  value="${theFormattedDate}" cssClass="form-control"/>
                <form:errors path="fromDate" cssClass="help-block"/>
            </div>
        </div>
        <div class="form-group">
            <form:label path="excursionType" cssClass="col-sm-2 control-label">Type of excursion</form:label>
            <div class="col-sm-10">
                <form:select path="excursionType" cssClass="form-control">
                    <c:forEach items="${types}" var="c">
                        <form:option value="${c}">${c}</form:option>
                    </c:forEach>
                </form:select>
                <form:errors path="excursionType" cssClass="error"/>
            </div>
        </div>
        <div class="form-group ${durationInHours_error?'has-error':''}" >
            <form:label path="durationInHours" cssClass="col-sm-2 control-label">Duration in hours</form:label>
            <div class="col-sm-10">
                <form:input path="durationInHours" cssClass="form-control"/>
                <form:errors path="durationInHours" cssClass="help-block"/>
            </div>
        </div>


        <button class="btn btn-primary" type="submit">Create excursion</button>
    </form:form>

</jsp:attribute>
</my:pagetemplate>
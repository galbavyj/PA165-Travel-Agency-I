<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Customers">
<jsp:attribute name="body">

    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>First name</th>
            <th>Last name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Created</th>
            <th>Street</th>
            <th>Number of house</th>
            <th>City</th>
            <th>Country</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${customers}" var="customer">
            <tr>
                <td>${customer.id}</td>
                <td><c:out value="${customer.firstName}"/></td>
                <td><c:out value="${customer.lastName}"/></td>
                <td><c:out value="${customer.email}"/></td>
                <td><c:out value="${customer.phoneNumber}"/></td>
                <td><fmt:formatDate value="${customer.created}" pattern="dd. MM. yyyy"/></td>
                <td><c:out value="${customer.address.street}"/></td>
                <td><c:out value="${customer.address.numberOfHouse}"/></td>
                <td><c:out value="${customer.address.city}"/></td>
                <td><c:out value="${customer.address.country}"/></td>
           </tr>
        </c:forEach>
        </tbody>
    </table>

</jsp:attribute>
</my:pagetemplate>
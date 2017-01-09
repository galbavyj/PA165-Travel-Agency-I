<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Upload picture">
<jsp:attribute name="body">

    <form action="${pageContext.request.contextPath}/admin/trip/upload/${tripId}" method="post" enctype="multipart/form-data">
    <input type="file" name="file" />
    <button class="btn btn-primary" type="submit">Upload picture</button>
    </form>

</jsp:attribute>
</my:pagetemplate>

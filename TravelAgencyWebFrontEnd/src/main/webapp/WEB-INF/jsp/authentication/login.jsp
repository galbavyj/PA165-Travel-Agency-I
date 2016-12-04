<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate>
<jsp:attribute name="body">
    <caption>Welcome to our travel agency!</caption>
    <a>Log in to proceed:</a>
    <div class="jumbotron">
        <form method="POST" action="/auth/login">
            Email: <input type="text" name="email"/><br/>
            Password: <input type="password" name="password"/><br/>
            <input type="submit"/>
        </form>
    </div>

</jsp:attribute>
</my:pagetemplate>

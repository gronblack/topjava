<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--https://getbootstrap.com/docs/4.0/examples/sticky-footer/--%>
<footer class="footer">
    <div class="container">
        <span class="text-muted"><spring:message code="app.footer"/></span>
    </div>
    <c:if test="${not empty param.entity}">
        <script type="text/javascript">
            const LOCALE = "<spring:message code="common.locale"/>";
            const i18n = [];
            i18n["addTitle"] = '<spring:message code="${param.entity}.add"/>';
            i18n["editTitle"] = '<spring:message code="${param.entity}.edit"/>';
            <c:forEach var="key" items='<%=new String[]{"common.deleted","common.saved","common.enabled","common.disabled","common.errorStatus","common.confirm"}%>'>
            i18n["${key}"] = "<spring:message code="${key}"/>";
            </c:forEach>
        </script>
    </c:if>
</footer>
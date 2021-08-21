<%@ attribute name="status" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ attribute name="name" required="true" description="Name of corresponding property in bean object" %>
<%@ attribute name="labelCode" required="true" description="Field label" %>
<%@ attribute name="inputType" required="false" description="Input type" %>
<%@ attribute name="error" required="false" description="Error message" %>

<spring:bind path="${name}">
    <div class="form-group${(empty error)?'':' error'}">
        <label class="col-form-label"><spring:message code="${labelCode}"/></label>
        <form:input path="${name}" type="${(empty inputType)?'text':inputType}" class="form-control ${(empty error)?'':' is-invalid'}"/>
        <div class="invalid-feedback">${error}</div>
    </div>
</spring:bind>
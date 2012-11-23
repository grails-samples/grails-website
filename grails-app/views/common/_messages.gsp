<g:if test="${flash.message}">
    <div id="message" class="alert alert-info">
        <g:message code="${flash.message}" />
    </div>
</g:if>
<g:if test="${message}">
    <div id="message" class="alert alert-info">
        <g:message code="${message}" />
    </div>
</g:if>
<g:if test="${error}">
    <div id="errors" class="alert alert-error">
        <g:message code="${error}" />
    </div>
</g:if>
<g:hasErrors bean="${bean}">
    <div id="errors" class="alert alert-error">
        <g:renderErrors bean="${bean}"></g:renderErrors>
    </div>
</g:hasErrors>
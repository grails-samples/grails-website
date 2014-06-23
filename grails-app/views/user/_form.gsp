<g:hiddenField name="id" value="${userInstance?.id}"/>
<g:hiddenField name="version" value="${userInstance?.version}"/>



<div class="control-group ${hasErrors(bean: userInstance, field: 'email', 'error')}">
    <label class="control-label" for="email">
        <g:message code="user.email.label" default="Email"/>
    </label>

    <div class="controls">
        <g:field type="email" name="email" required="" value="${userInstance?.email}"/>
    </div>
</div>


<div class="control-group ${hasErrors(bean: userInstance, field: 'login', 'error')}">
    <label class="control-label" for="login">
        <g:message code="user.login.label" default="Login"/>
    </label>

    <div class="controls">
        <g:textField name="login" maxlength="15" required="" value="${userInstance?.login}"/>
    </div>
</div>


<div class="control-group ${hasErrors(bean: userInstance, field: 'enabled', 'error')}">
    <label class="control-label" for="enabled">
        <g:message code="user.enabled.label" default="Enabled"/>
    </label>

    <div class="controls">
        <g:checkBox name="enabled" value="${userInstance?.enabled}"/>
    </div>
</div>

<div class="control-group ${hasErrors(bean: userInstance, field: 'permissions', 'error')}">
    <label class="control-label" for="permissions">
        <g:message code="user.permissions.label" default="Permissions"/>
    </label>

    <div class="controls">
        <g:textArea name="permissions" cols="40" rows="7" value="${userInstance?.permissions?.join('\n')}" /> (Separated by new lines or semi-colons)

    </div>
</div>


<div class="control-group ${hasErrors(bean: userInstance, field: 'roles', 'error')}">
    <label class="control-label" for="roles">
        <g:message code="user.roles.label" default="Roles"/>
    </label>

    <div class="controls">
        <g:select name="roles" from="${org.grails.auth.Role.list()}" multiple="multiple" optionKey="id" size="5" value="${userInstance?.roles*.id}" class="many-to-many"/>
    </div>
</div>

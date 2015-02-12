<% import grails.persistence.Event %>
<% import grails.util.Holders %>
<%=packageName%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="admin"/>
    <g:set var="entityName" value="\${message(code: '${domainClass.propertyName}.label', default: '${className}')}"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>
</head>

<body>

<h1 class="page-header">
    <g:message code="default.create.label" args="[entityName]"/>
    <span class="pull-right">
        <g:link class="btn" action="list"><g:message code="default.list.label" args="[entityName]"/></g:link>
    </span>
</h1>

<g:if test="\${flash.message}">
    <div class="alert alert-info">\${flash.message}</div>
</g:if>

<g:hasErrors bean="\${${propertyName}}">
    <div class="alert alert-error">
        <g:renderErrors bean="\${${propertyName}}" as="list"/>
    </div>
</g:hasErrors>

<g:form action="save" class="form-horizontal" <%=multiPart ? ' enctype="multipart/form-data"' : '' %>>
<fieldset>

    <%  excludedProps = Event.allEvents.toList() << 'version' << 'id' << 'dateCreated' << 'lastUpdated'
    persistentPropNames = domainClass.persistentProperties*.name
    props = domainClass.properties.findAll { persistentPropNames.contains(it.name) && !excludedProps.contains(it.name) }
    Collections.sort(props, comparator.constructors[0].newInstance([domainClass] as Object[]))
    display = true
    boolean hasHibernate = Holders.pluginManager.hasGrailsPlugin('hibernate')
    props.each { p ->
        if (!Collection.class.isAssignableFrom(p.type)) {
            if (hasHibernate) {
                cp = domainClass.constrainedProperties[p.name]
                display = (cp ? cp.display : true)
            }
            if (display) { %>

    <div class="control-group \${hasErrors(bean: ${propertyName}, field: '${p.name}', 'error')}">
        <label class="col-sm-2 control-label" for="${p.name}">
            <g:message code="${domainClass.propertyName}.${p.name}.label" default="${p.naturalName}"/>
        </label>

        <div class="col-sm-10">
            ${renderEditor(p)}
        </div>
    </div>

    <%  }   }   } %>

    <div class="form-actions">
        <g:submitButton name="create" class="btn btn-primary"
                        value="\${message(code: 'default.button.create.label', default: 'Create')}"/>
        <g:link class="btn" action="list">Cancel</g:link>
    </div>
</fieldset>
</g:form>

</body>
</html>

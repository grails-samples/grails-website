<% import grails.persistence.Event %>
<%=packageName%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="admin"/>
    <g:set var="entityName" value="\${message(code: '${domainClass.propertyName}.label', default: '${className}')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>

<h1 class="page-header">
    <g:message code="default.list.label" args="[entityName]"/>
    <span class="pull-right">
        <g:link class="btn btn-primary" action="create">
            <g:message code="default.new.label" args="[entityName]"/>
        </g:link>
    </span>
</h1>

<g:if test="\${flash.message}">
    <div class="alert alert-info">
        \${flash.message}
    </div>
</g:if>

<table class="table table-bordered table-striped">
    <thead>
    <tr>
        <% excludedProps = Event.allEvents.toList() << 'version'
        allowedNames = domainClass.persistentProperties*.name << 'id' << 'dateCreated' << 'lastUpdated'
        props = domainClass.properties.findAll { allowedNames.contains(it.name) && !excludedProps.contains(it.name) && !Collection.isAssignableFrom(it.type) }
        Collections.sort(props, comparator.constructors[0].newInstance([domainClass] as Object[]))
        props.eachWithIndex { p, i ->
            if (i < 6) {
                if (p.isAssociation()) { %>
        <th><g:message code="${domainClass.propertyName}.${p.name}.label" default="${p.naturalName}"/></th>
        <% } else { %>
        <g:sortableColumn property="${p.name}"
                          title="\${message(code: '${domainClass.propertyName}.${p.name}.label', default: '${p.naturalName}')}"/>
        <% }
        }
        } %>
    </tr>
    </thead>
    <tbody>
    <g:each in="\${${propertyName}List}" status="i" var="${propertyName}">
        <tr class="\${(i % 2) == 0 ? 'odd' : 'even'}">
            <% props.eachWithIndex { p, i ->
                if (i == 0) { %>
            <td><g:link action="show"
                        id="\${${propertyName}.id}">\${fieldValue(bean: ${propertyName}, field: "${p.name}")}</g:link></td>
            <% } else if (i < 6) {
                if (p.type == Boolean.class || p.type == boolean.class) { %>
            <td><g:formatBoolean boolean="\${${propertyName}.${p.name}}"/></td>
            <% } else if (p.type == Date.class || p.type == java.sql.Date.class || p.type == java.sql.Time.class || p.type == Calendar.class) { %>
            <td><g:formatDate date="\${${propertyName}.${p.name}}"/></td>
            <% } else { %>
            <td>\${fieldValue(bean: ${propertyName}, field: "${p.name}")}</td>
            <% }
            }
            } %>
        </tr>
    </g:each>
    </tbody>
</table>

<div class="pagination">
    <g:paginate total="\${${propertyName}Total}"/>
</div>

</body>
</html>

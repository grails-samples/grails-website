<% import grails.persistence.Event %>
<%=packageName%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="admin"/>
    <g:set var="entityName" value="\${message(code: '${domainClass.propertyName}.label', default: '${className}')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>

<h1 class="page-header">
    <g:message code="default.show.label" args="[entityName]"/>
    <span class="pull-right">
        <g:form>
            <g:hiddenField name="id" value="\${${propertyName}?.id}"/>
            <g:link class="btn" action="list">
                <g:message code="default.list.label" args="[entityName]"/>
            </g:link>
            <g:actionSubmit class="btn btn-info" action="edit"
                            value="\${message(code: 'default.button.edit.label', default: 'Edit')}"/>
            <g:actionSubmit class="btn btn-danger" action="delete"
                            value="\${message(code: 'default.button.delete.label', default: 'Delete')}"
                            onclick="return confirm('\${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
        </g:form>
    </span>
</h1>

<g:if test="\${flash.message}">
    <div class="alert alert-info">
        \${flash.message}
    </div>
</g:if>

<table class="table table-bordered table-striped">
    <tbody>
    <% excludedProps = Event.allEvents.toList() << 'version'
    allowedNames = domainClass.persistentProperties*.name << 'id' << 'dateCreated' << 'lastUpdated'
    props = domainClass.properties.findAll { allowedNames.contains(it.name) && !excludedProps.contains(it.name) }
    Collections.sort(props, comparator.constructors[0].newInstance([domainClass] as Object[]))
    props.each { p -> %>
    <tr>
        <td class="show-label">
            <g:message code="${domainClass.propertyName}.${p.name}.label" default="${p.naturalName}"/>
        </td>

        <% if (p.isEnum()) { %>
            <td class="show-value">\${${propertyName}?.${p.name}?.encodeAsHTML()}</td>
        <% } else if (p.oneToMany || p.manyToMany) { %>
            <td class="show-value">
                <ul>
                    <g:each in="\${${propertyName}.${p.name}}" var="${p.name[0]}">
                        <li>
                            <g:link controller="${p.referencedDomainClass?.propertyName}" action="show"
                                    id="\${${p.name[0]}.id}">\${${p.name[0]}?.encodeAsHTML()}</g:link>
                        </li>
                    </g:each>
                </ul>
            </td>
        <% } else if (p.manyToOne || p.oneToOne) { %>
        <td class="show-value">
                <g:link controller="${p.referencedDomainClass?.propertyName}" action="show"
                        id="\${${propertyName}?.${p.name}?.id}">\${${propertyName}?.${p.name}?.encodeAsHTML()}
                </g:link>
            </td>
        <% } else if (p.type == Boolean.class || p.type == boolean.class) { %>
            <td class="show-value"><g:formatBoolean boolean="\${${propertyName}?.${p.name}}"/></td>
        <% } else if (p.type == Date.class || p.type == java.sql.Date.class || p.type == java.sql.Time.class || p.type == Calendar.class) { %>
            <td class="show-value"><g:formatDate date="\${${propertyName}?.${p.name}}"/></td>
        <% } else if (!p.type.isArray()) { %>
        <td class="show-value">\${fieldValue(bean: ${propertyName}, field: "${p.name}")}</td>
        <% } %>
    </tr>
    <% } %>
    </tbody>
</table>

</body>
</html>

<%@ page import="org.grails.common.ApprovalStatus" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Wiki Page List</title>
    <meta name="layout" content="admin"/>
    <r:require modules="fancyBox"/>
</head>
<body>

<h1 class="page-header">
    Wiki Page List
    <span class="pull-right">
        <input type="text" id="title" placeholder="New Page Title" />
        <input class="btn" id="create-page" type="submit" value="Create Page" />
    </span>
</h1>

<flash:message flash="${flash}"/>

<table class="table table-bordered table-striped">
    <thead>
    <tr>
        <g:sortableColumn property="id" title="ID"/>
        <g:sortableColumn property="title" title="Title"/>
        <g:sortableColumn property="locked" title="Locked?"/>
        <g:sortableColumn property="lastUpdated" title="Last Updated"/>
        <th>Modified By</th>
        <th>Versions</th>
        <th>&nbsp;</th>
    </tr>
    </thead>
    <tbody>
    <g:each in="${wikiPageInstanceList}" status="i" var="wikiPageInstance">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
            <td><g:link action="show" id="${wikiPageInstance.id}">${fieldValue(bean: wikiPageInstance, field: "id")}</g:link></td>
            <td>${fieldValue(bean: wikiPageInstance, field: "title")}</td>
            <td><g:formatBoolean boolean="${wikiPageInstance.locked}"/></td>
            <td>${fieldValue(bean: wikiPageInstance, field: "lastUpdated")}</td>
            <td>${wikiPageInstance.latestVersion?.author?.login}</td>
            <td>${wikiPageInstance.latestVersion?.number}</td>
            <td>
                <a href="#" class="preview btn btn-mini" data-contents="${wikiPageInstance.body.encodeAsHTML()}">Preview</a>
                <a href="/${wikiPageInstance.title}" class="btn btn-mini" target="_blank">Go To Page</a>
            </td>
        </tr>
    </g:each>
    </tbody>
</table>

<div class="pagination">
    <g:paginate total="${wikiPageInstanceTotal}"/>
</div>

<r:script>
    $(function () {
        // Redirect to the create wiki page
        $('#create-page').click(function() {
            var newPageTitle = $('input#title').val();
            if (newPageTitle.length > 0) {
                // Open a new page
                window.open('/create/' + newPageTitle);
            }
            else {
                alert('You must enter a wiki page title');
            }
        });

        // Show preview of wiki page
        $('.preview').click(function() {
            var body = $(this).data('contents');
            $.ajax({
                type    : "POST",
                cache   : false,
                url     : "/previewWikiPage",
                data    : { body: body },
                success : function(data) {
                    $.fancybox(data, {
                        maxWidth    : 710,
                        maxHeight   : 600,
                        fitToView   : false,
                        width       : '70%',
                        height      : '70%',
                        autoSize    : false,
                        closeClick  : false,
                        openEffect  : 'fade',
                        closeEffect : 'fade'
                    });
                }
            });
            return false;
        });
    });
</r:script>

</body>
</html>
<html>
<head>
    <meta content="admin" name="layout"/>
    <style type="text/css">
        h2 {
            margin-top:30px;
        }
    </style>
</head>

<body>

<h1>Admin Area</h1>
<div class="row">
    <div class="span6">

        <h2>Users</h2>
        <g:form url="[controller:'user', action:'search']">
           <input name="q"></input>
           <button class="btn btn-primary">Search</button>
        </g:form>

    </div>
    <div class="span6">
        <h2>Pending Approvals</h2>
        <g:render template="/contentPendingApproval/pendingApprovals" />

    </div>
</div>
<div class="row">
    <div class="span6">
        <h2>Recent Wiki Updates</h2>
        <table class="table table-bordered table-striped">
            <thead>
            <tr>
                <g:sortableColumn property="title" title="Title"/>
                <g:sortableColumn property="author" title="Author"/>
                <g:sortableColumn property="locked" title="Locked"/>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
                <g:each in="${recentWikiPages}" var="page">
                <tr>
                    <td><g:link controller="wikiPage" action="show" id="${page.id}">${page.title}</g:link></td>
                    <td>${page.latestVersion.author.login}</td>
                    <td>${page.locked}</td>
                    <td>
                           <g:link controller="wikiPage" action="edit" id="${page.id}"
                                    class="btn btn-primary">Edit</g:link>

                            <g:set var="latestVersionNumber" value="${page.latestVersion.number}" />
                            <g:if test="${latestVersionNumber}">
                                <g:link controller="content" action="diffWikiVersion"
                                        params="[id:page.title, number:latestVersionNumber, diff:latestVersionNumber-1]"
                                        class="btn btn-primary"
                                        >Diff</g:link>

                            </g:if>

                            <g:link controller="wikiPage" action="rollback"  id="${page.title}"
                                     class="btn btn-danger"
                                     value="Send Response to Submitter"
                                     onclick="return confirm('Are you sure?');">Rollback</g:link>


                    </td>
                </tr>
                </g:each>
            </tbody>
        </table>

    </div>
</div>
</body>
</html>

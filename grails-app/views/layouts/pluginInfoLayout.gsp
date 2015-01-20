<g:applyLayout name="main">
<head>
    <title><g:layoutTitle /></title>
    <content tag="pageCss">
        <g:pageProperty name="page.pageCss" />
    </content>

    <g:layoutHead />

    <g:render template="/common/messages_effects" model="${pageScope.getVariables()}"/>
</head>
<body>
    <g:layoutBody/>
</body>
</g:applyLayout>

<g:applyLayout name="main">
<head>
    <title><g:layoutTitle/></title>
    <r:require modules="subpage"/>
    <g:layoutHead />

    <g:render template="/common/messages_effects" model="${pageScope.getVariables()}"/>

    <!-- Google+ JS -->
    <link href="https://plus.google.com/117411438136918964913" rel="publisher" />
</head>
<body>
    <div id="contentTitle">
        <h1><wiki:shorten html="${g.layoutTitle(default:'')}" length="35" /></h1>
    </div> 
            
    <div id="navMenu">
        <g:render template="/user/profileBox" />				
        <g:render template="/content/nav"></g:render>
        <div id="navFooter">
        </div>
        <div id="navAds">
            <g:render template="/content/ads"></g:render>					
        </div>
    </div>
    <div id="contentWindow">
            <div id="contentWindowTop">				
            </div>
            <div id="contentDecoration">				
            </div>
            <div id="contentBody">
                <g:layoutBody/>						
            </div>
            <div id="contentFooter">

            </div>
    </div>
</body>
</g:applyLayout>

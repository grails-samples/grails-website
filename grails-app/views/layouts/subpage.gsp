<g:applyLayout name="main">
<head>
    <title><g:layoutTitle/></title>
    <content tag="pageCss">
        <link rel="stylesheet" href="${resource(dir: 'css/new', file: 'subpage.css')}" type="text/css" />
        <link rel="stylesheet" href="${resource(dir:'css',file:'content.css')}" />
    </content>
    <g:javascript library="yui" />
    <yui:javascript dir="animation" file="animation-min.js" /> 
    <g:javascript src="common/yui-effects.js" />
    <g:javascript library="diff_match_patch" />
    <g:layoutHead />

    <g:render template="/common/messages_effects" model="${pageScope.getVariables()}"/>
</head>
<body>
    <div id="contentTitle">
        <h1><wiki:shorten text="${g.layoutTitle(default:'')}" length="35" /></h1>
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

<g:applyLayout name="main">
<head>
    <title><g:layoutTitle/></title>
    <r:require modules="section"/>
    <g:layoutHead />
</head>
<body>
    <div id="contentTitle">
        <h1><wiki:shorten text="${g.layoutTitle(default:'')}" length="35" /></h1>
    </div> 
            
    <div id="navMenu">
        <g:render template="/user/profileBox" />				
        <div id="sectionMenu">
            <div class="header">
                <g:pageProperty name="page.headerText" />
            </div>
            <ul>
                <li class="all"><g:link action="list" params="[category:'all']">All</g:link></li>
                <li class="featured"><g:link action="list" params="[category:'featured']">Featured</g:link></li>
                <li class="popular"><g:link action="list" params="[category:'popular']">Highest Voted</g:link></li>
                <li class="newest"><g:link action="list" params="[category:'newest']">Newest</g:link></li>
            </ul>
            <div class="footer">
            </div>
        </div>
        <g:render template="/content/nav"></g:render>
        <div id="navFooter">
        </div>
        <div id="navAds">
            <g:render template="/content/ads"></g:render>					
        </div>
    </div>
    <div id="contentWindow">
        <div id="searchBar">
            <g:render template="/plugin/searchBar"/>
        </div>
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

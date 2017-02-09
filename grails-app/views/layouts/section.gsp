<g:applyLayout name="master">
<head>
    <title><g:layoutTitle/></title>
    <g:layoutHead />
</head>
<body>

<div id="content" class="content-aside" role="main">

    <section class="aside">
        <aside id="tutorials">
            <h3><wiki:shorten html="${g.layoutTitle(default:'')}" length="35" camelCase="true" /></h3>
            <ul>
                <li class="all"><g:link action="list" params="[category:'all']">All</g:link></li>
                <li class="featured"><g:link action="list" params="[category:'featured']">Featured</g:link></li>
                <li class="popular"><g:link action="list" params="[category:'popular']">Highest Voted</g:link></li>
                <li class="newest"><g:link action="list" params="[category:'newest']">Newest</g:link></li>
            </ul>
        </aside>
    </section>

    %{--<div id="navMenu">--}%
        %{--<g:render template="/user/profileBox" />				--}%
        %{--<div id="sectionMenu">--}%
            %{--<div class="header">--}%
                %{--<g:pageProperty name="page.headerText" />--}%
            %{--</div>--}%
            %{--<ul>--}%
                %{--<li class="all"><g:link action="list" params="[category:'all']">All</g:link></li>--}%
                %{--<li class="featured"><g:link action="list" params="[category:'featured']">Featured</g:link></li>--}%
                %{--<li class="popular"><g:link action="list" params="[category:'popular']">Highest Voted</g:link></li>--}%
                %{--<li class="newest"><g:link action="list" params="[category:'newest']">Newest</g:link></li>--}%
            %{--</ul>--}%
            %{--<div class="footer">--}%
            %{--</div>--}%
        %{--</div>--}%
        %{--<g:render template="/content/nav"></g:render>--}%
        %{--<div id="navFooter">--}%
        %{--</div>--}%
        %{--<div id="navAds">--}%
            %{--<g:render template="/content/ads"></g:render>					--}%
        %{--</div>--}%
    %{--</div>--}%

    <div id="main">
        <article>
            <g:render template="/plugin/searchBar"/>
            <div id="contentWindowTop">
            </div>
            <div id="contentBody">
                <div id="contentDecoration"></div>
                <g:layoutBody/>
            </div>
            <div id="contentFooter">
            </div>
        </article>
    </div>
</body>
</g:applyLayout>

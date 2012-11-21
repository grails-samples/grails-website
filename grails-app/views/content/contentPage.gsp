<head>
    <title>Wiki - ${content?.title}</title>
    <meta content="master" name="layout"/>
    <r:require modules="content"/>
</head>

<body>

<div id="content" class="content-aside" role="main">

    <g:render template="sideNav"/>

    <section id="main">
        <article>
            <div id="wikiLastUpdated">Last updated by ${latest?.author?.login}
                <prettytime:display date="${content.lastUpdated}"/></div>
            <g:render template="viewActions" model="[content: content]"/>

            <flash:message flash="${flash}"/>

            <div id="editPane" style="margin-top:10px;">
                <g:if test="${content?.deprecated}">
                    <wiki:deprecated uri="${content?.deprecatedUri}"/>
                </g:if>
                <wiki:text key="${content?.title}">
                    ${content?.body}
                </wiki:text>
            </div>
        </article>
    </section>
</div>

<g:render template="previewPane"/>
</body>
</html>

<head>
    <meta content="master" name="layout"/>
    <title>Web Site: ${webSiteInstance.title.encodeAsHTML()}</title>
    <r:require modules="community"/>
    <style type="text/css">
        .actionIcon {
            float:left;
            position:relative;
            right:20px;
            bottom:10px;
        }
    </style>
</head>

<body>

<div id="content" class="content-aside" role="main">

    <g:render template="/community/sideNav"/>

    <section id="main" class="website">
        <g:if test="${request.user == webSiteInstance.submittedBy}">
            <div class="alert alert-info">
                <i class="icon-edit"></i> You can
                <g:link title="Edit WebSite" action="edit" id="${webSiteInstance?.id}">
                    edit the content
                </g:link> of the website using Grails.
            </div>
        </g:if>

        <h1>${webSiteInstance?.title?.encodeAsHTML()}</h1>
        %{-- <a href="#" class="like">+1</a> --}%
        <div class="colset">
            <div class="img">
                <bi:hasImage bean="${webSiteInstance}">
                    <a href="${webSiteInstance.url.encodeAsHTML()}" target="_blank">
                        <bi:img size="large" bean="${webSiteInstance}"/>
                    </a>
                </bi:hasImage>
            </div>
            <div class="content">
                <small style="color: #999; font-size: 14px;">
                    <wiki:shorten key="${'webSite_large_' + webSiteInstance.id}" wikiText="${webSiteInstance?.shortDescription}" length="80" />
                </small>
                <p class="tags">
                    <a href="${webSiteInstance.url.encodeAsHTML()}" target="_blank">${webSiteInstance?.url?.encodeAsHTML()}</a>
                </p>
                <p><wiki:text key="${'webSite_' + webSiteInstance.id}">${webSiteInstance.description}</wiki:text></p>
            </div>
        </div>

        <div class="disqus-container">
            <disqus:comments bean="${webSiteInstance}" url="${createLink(uri:request.forwardURI, absolute:true)}"/>
        </div>
    </section>
</div>

</body>

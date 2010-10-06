<g:applyLayout name="pluginInfoLayout">
<head>
    <title><g:layoutTitle default="Plugin - ${plugin.title}" /></title>

    <content tag="pageCss">
        <rateable:resources />
        <gui:resources components="['tabView','dialog','autoComplete']" javascript='animation'/>
        <link rel="stylesheet" type="text/css" href="${resource(dir:'css',file:'tabview.css')}" />
        <link rel="stylesheet" type="text/css" href="${resource(dir:'css',file:'content.css')}" />
        <link rel="stylesheet" type="text/css" href="${resource(dir:'css',file:'plugins.css')}" />
    </content>

    <g:javascript src="common/yui-effects.js" />
    <g:javascript library="diff_match_patch" />

    <g:layoutHead />

    <g:render template="../content/wikiJavaScript"/>    

    <style type="text/css" media="screen">
.yui-navset-bottom .yui-nav li a em {
    display:inline;
}
.yui-navset .yui-nav li a em, .yui-navset-top .yui-nav li a em, .yui-navset-bottom .yui-nav li a em {
    display:inline;
}
    </style>
</head>
<body>

    <div id="contentPane">

	<div id="pluginBigBox">
            <g:render template="/user/profileBox" />		
            <div id="pluginBgTop"></div>
            <div id="pluginBox">
                <div id="pluginDetailWrapper">

                    <g:layoutBody />

                    <div class="pluginBoxBottom"></div>
                    <g:render template="../content/previewPane"/>
                </div>	
            </div>		
	</div>
    </div>
</body>
</g:applyLayout>

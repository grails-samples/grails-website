<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title>Download Grails</title>
    <meta content="subpage" name="layout" />
    <g:javascript library="scriptaculous" />
    <style type="text/css">

        .download-table  {

            margin:10px;
            padding:5px;
            width:90%;
            margin-bottom:40px;
        }
        .download-table td  {
            border-top:0px;
            margin:10px;
            padding:5px;

        }

        .download-table th  {
            background-color: lightgray;
            border:none;
            border-top:0px;
            margin:10px;
            padding:5px;
            color:#48802C;
        }



    </style>
</head>
<body>
    <div id="contentPane">
		<g:render template="download" collection="${stableDownloads}" var="download"/>
<%--
		<g:if test="${betaDownload}">
			<g:render template="download" model="[download:betaDownload]"/>
		</g:if>
--%> 



        <p>Past releases can be found at the <g:link controller="download" action="archive" id="Grails">Archive</g:link>.</p>
        <br />

        <h3>Grails Development Builds</h3>
        <p>

            You can obtain a development build of Grails from our Hudson continuous integration server at <a href="http://hudson.grails.org" class="pageLink">http://hudson.grails.org</a>
        </p>
        <p class="paragraph">Follow these steps:
        </p>

        <ul class="star">
            <li>Click on the link for the version you want: grails_core_1.y.x</li>
            <li>Under 'Last Successful Artifacts', click on the Grails distribution link: grails-1.y.z.BUILD-SNAPSHOT.zip</li>
        </ul>

        <br />

        <h3>Grails Plugin Downloads</h3>

        <p>Grails has a number of plugins available for it that extend its capability. Check out the <g:link controller="content" id="Plugins">Plugins page</g:link> for more info on available plugins and how they can be installed.</p>
    </div>
</body>
</html>

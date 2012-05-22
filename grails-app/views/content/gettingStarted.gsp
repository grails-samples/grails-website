<g:setProvider plugin="yui" library="yui"/>

<head>
    <title>Getting Started</title>
    <meta content="master" name="layout"/>
    <g:render template="wikiJavaScript"/>
    <r:require modules="content"/>
</head>

<body>

<div id="content" class="content-aside" role="main">

    <g:render template="sideNav"/>

    <div id="main">
        <article>
            <h2>Quick introduction - What is Grails?</h2>

            <p id="grails-intro-presentation">
            <iframe src="http://app.sliderocket.com:80/app/fullplayer.aspx?id=A0F94305-C30B-9EE9-2A1D-EB45AFEB183C"
                    width="448" height="354" scrolling=no frameBorder="1"
                    style="border:1px solid #333333;border-bottom-style:none"></iframe>
        </p>
            <h2>Next steps</h2>
            <download:latest var="grailsDownload" software="Grails"/>
            <ul>
                <li><download:link software="Grails" version="${grailsDownload?.softwareVersion}"
                                   file="Binary Zip">download</download:link> and <g:link
                        uri="/doc/latest/guide/gettingStarted.html#requirements">install</g:link> Grails</li>
                <li>get a simple project <g:link
                        uri="/doc/latest/guide/gettingStarted.html#creatingAnApplication">up and running</g:link></li>
                <li>check out the <g:link uri="/tutorials">tutorials</g:link></li>
            </ul>
        </article>
    </div>

</div>

</body>
</html>

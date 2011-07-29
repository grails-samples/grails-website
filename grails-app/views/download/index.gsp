<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title>Download Grails</title>
    <meta content="subpage" name="layout" />
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
        <div class="downloads">
            <shiro:hasRole name="Administrator">
            <p>As an administrator, you can change which versions of Grails appear on this page and in which order. Note that you should only specify at most one beta/milestone release and that should take the form 'm.n beta' or 'm.n milestone'.</p>
            <p>You'll have to refresh the page to see your changes.</p>
            <p id="versionDisplayOrder">
                <g:include controller="download" action="adminShowVersionOrder"/>
            </p>
            </shiro:hasRole>
            <h2>Latest stable releases</h2>
            <g:render template="download" collection="${stableDownloads}" var="download"/>
            <g:if test="${betaDownloads}">
                <h2>Latest beta release</h2>
                <g:render template="download" collection="${betaDownloads}" var="download"/>
            </g:if>
    <%--
                    <g:if test="${betaDownload}">
                            <g:render template="download" model="[download:betaDownload]"/>
                    </g:if>
    --%> 



            <p>Past releases can be found at the <g:link controller="download" action="archive" id="Grails">Archive</g:link>.</p>
            <br />
            
            <h3>Grails on Ubuntu</h3>
            <p>Get Grails via Ubuntu's package manager by simply following the first three steps of:</p>
            <script src="https://gist.github.com/901884.js"> </script>
            <p>What could be easier? The package work for both i386 and amd64 flavours of Lucid, Maverick, and Natty. In
            addition, it works with both the <tt>openjdk-6-jdk</tt> and <tt>sun-java6-jdk</tt> JDK packages. Note that
            you do not need to set either the <tt>JAVA_HOME</tt> or <tt>GRAILS_HOME</tt> environment variables when using
            this package.</p>
            <p>As you can see from the code above, you can install multiple versions of Grails side-by-side and then switch between them using the <tt>update-alternatives</tt> command. The 'grails' package tracks the latest stable release, so when you upgrade it will automatically install the latest stable release at the top of this page. You can even install a 'grails-bash-completion' package to get bash completion for Grails commands.</p>
            <p>One final thing: you can keep up to date with the development of the package via its
            <a href="https://launchpad.net/~groovy-dev/+archive/grails">Launchpad page</a>.</p>

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
    </div>
</body>
</html>

<head>
    <meta content="master" name="layout"/>
    <title>Getting Started with Grails</title>
    <r:require modules="learn"/>
    <r:script>
        function loadPresentation(playerFrame) {
            if(document.location.protocol == 'https:') {
                playerFrame.attr('src', 'https://app.sliderocket.com:80/app/fullplayer.aspx?id=A0F94305-C30B-9EE9-2A1D-EB45AFEB183C');
            }
            else {
                playerFrame.attr('src', 'http://app.sliderocket.com:80/app/fullplayer.aspx?id=A0F94305-C30B-9EE9-2A1D-EB45AFEB183C');
            }
            
        }

        function clearPresentation(playerFrame) {
            playerFrame.attr('src', '');
        }

        $(function(){
            // Load SlideRocket presentation before its modal appears so that
            // it seems to load faster for the user.
            loadPresentation($(".modal iframe"));

            $(".modal").on('show', function(){
                // Cover the presentation until it has restarted.
                var shadePane = $(this).find('#shadePane')
                shadePane.show();
                $('.modal-backdrop').addClass('dark');

                loadPresentation($(this).find('iframe'));

                // Fade out the opaque pane hiding the presentation once
                // it's likely the presentation has started.
                shadePane.delay(1000).fadeOut(200);
            });

            $(".modal").on('hide', function(){
                clearPresentation($(this).find('iframe'));
            });
        });
    </r:script>
    <style>
    .modal .body {
        width: 660px;
        height: 450px;
        position: relative;
    }

    #shadePane {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        z-index: 100;
        background-color: white; 
    }
    </style>
</head>

<body>

<div id="content" class="content-aside" role="main">

    <g:render template="sideNav" />

    <section id="main">
        <article class="learngrails5steps">
            <h2>Learn Grails <small>in 5 easy steps</small></h2>
            <p>The following 5 steps will take you from knowing nothing about Grails to running a Grails application - all in a matter of minutes! And if you already have some knowledge of the framework, skip the steps you don't need.</p>
            <g:set var="step" value="${0}" />
            <div class="item">
                <h3><span class="number">${++step}.</span> What is Grails?</h3>
                <p>Never heard of Grails? Heard of it but not sure what it is or does? Watch this short presentation for a quick overview.</p>
                <p class="links">
                    <a class="btn blueLight play" href="#modal-pres1" data-toggle="modal" data-backdrop="true">
                        <g:message code="page.learn.play"/>
                    </a>
                </p>
            </div>
<%--             <div class="item">
                <span class="number">${++step}</span>
                <h3>Grails for ...</h3>
                <p>Understand how Grails differs from the frameworks you already know.</p>
                <div class="colset2">
                    <div class="col1">
                        <h4>Java developers</h4>
                        <p></p>
                        <p class="links"><a class="btn blueLight play" href="#"><g:message code="page.learn.play"/></a></p>
                    </div>
                    <div class="col2">
                        <h4>PHP/Ruby/Python developers</h4>
                        <p></p>
                        <p class="links"><a class="btn blueLight play" href="#"><g:message code="page.learn.play"/></a></p>
                    </div>
                </div>
            </div> --%>
            <div class="item">
                <h3><span class="number">${++step}.</span> Try a sample application</h3>
                <p>Download the introductory sample application from GitHub:</p>
                <p class="links"><a class="btn blueLight" href="https://github.com/grails-samples/grails-petclinic/archive/master.zip">Sample Application</a></p>
                <p>Simply unpack the zip file and run either <tt>./grailsw run-app</tt> (Unix-like systems) or <tt>grailsw run-app</tt> (from the command prompt on Windows) to have a running web application. You will need a Java SDK installed and the <tt>JAVA_HOME</tt> environment variable set for this to work.</p>
                <p>You can find more sample Grails applications <a href="https://github.com/grails-samples">on GitHub</a>.</p>
            </div>
            <div class="item downloads">
                <h3><span class="number">${++step}.</span> Download Grails</h3>
                <p>Grab the latest stable release of Grails.</p>
                <p><a href="${latestDownload.mirrors[0].urlString}" class="btn blue">Download Grails ${latestVersion}</a></p>
                <p>You can find other Grails releases and methods of installation on the <g:link uri="/download">downloads page</g:link>, for example installation via apt-get on <strong>Ubuntu</strong> or <a href="http://gvmtool.net/">the <strong>gvm</strong> tool.</a></p>
            </div>
            <div class="item">
                <h3><span class="number">${++step}.</span> Installation</h3>
                <p>Quick and easy. Setting up a framework has never been as simple.</p>
                <p class="links"><a class="btn blueLight" href="/doc/latest/guide/gettingStarted.html#requirements">Read manual installation</a></p>
            </div>
            <div class="item">
                <h3><span class="number">${++step}.</span> Manual & Tutorials</h3>
                <p>Extend your knowledge via the great user guide and extensive tutorial selection.</p>
                <p class="links"><a class="btn blueLight" href="/doc/latest">Manual</a> <g:link class="btn blueLight" controller="tutorial" action="list">Tutorials</g:link></p>
            </div>
        </article>
    </section>

</div>

<div class="modal hide modal-fixed" id="modal-pres1">
    <div class="head">
        <h3>What is Grails!</h3>
        <a class="close" data-dismiss="modal">×</a>
    </div>
    <div class="body">
        <div id="shadePane">&nbsp;</div>
        <iframe width="660" height="450" scrolling="no" frameBorder="0" style="border:0px solid #333333;border-bottom-style:none;"></iframe>
    </div>
</div>

</body>

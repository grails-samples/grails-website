<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
    <meta content="master" name="layout"/>
    <r:require modules="learn"/>
    <r:script>
        $(function(){
            $(".modal").on('shown', function(){
                $('.modal-backdrop').addClass('dark');
                $(this).find('iframe').attr('src', 'http://app.sliderocket.com:80/app/fullplayer.aspx?id=A0F94305-C30B-9EE9-2A1D-EB45AFEB183C');
            });
            $(".modal").on('hide', function(){
                $(this).find('iframe').attr('src', '');
            });
        });
    </r:script>
</head>

<body>

<div id="content" class="content-aside" role="main">

    <g:render template="sideNav" />

    <div id="main">
        <article class="numbers">
            <h2>Learn Grails <small>in 5 easy steps</small></h2>
            <p>The following 5 steps will take you from knowing nothing about Grails to running a Grails application - all in a matter of minutes! And if you already have some knowledge of the framework, skip the steps you don't need.</p>
            <g:set var="step" value="${0}" />
            <div class="item">
                <h3><span class="number">${++step}.</span> What is Grails?</h3>
                <p>Never heard of Grails? Heard of it but not sure what it is or does? Watch this short presentation for a quick overview.</p>
                <p class="links"><a class="btn blueLight play" href="#modal-pres1" data-toggle="modal" data-backdrop="true">
                    <g:message code="page.learn.play"/>
                </a></p>
            </div>
<!--             <div class="item">
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
            </div> -->
            <div class="item">
                <h3><span class="number">${++step}.</span> Try a sample application</h3>
                <p>Check out one of the available sample applications from Github.</p>
                <p class="links"><a class="btn blueLight" href="https://github.com/grails-samples">Sample Applications</a></p>
            </div>
            <div class="item downloads">
                <h3><span class="number">${++step}.</span> Download Grails</h3>
                <p>Grab the latest stable release of Grails.</p>
                <p><a href="${latestDownload.mirrors[0].urlString}" class="btn blue">Download Grails ${latestVersion}</a></p>
                <p>You can find other Grails releases and methods of installation on the <g:link uri="/download">downloads page</g:link>.
            </div>
            <div class="item">
                <h3><span class="number">${++step}.</span> Installation</h3>
                <p>Quick and easy. Setting up a framework has never been as simple.</p>
                <p class="links"><a class="btn blueLight" href="/doc/latest/guide/gettingStarted.html#requirements">Read manual installation</a></p>
            </div>
            <div class="item">
                <h3><span class="number">${++step}.</span> Manual & Tutorials</h3>
                <p>Extend your knowledge via the great user guide and extensive tutorial selection.</p>
                <p class="links"><a class="btn blueLight" href="/latest/doc">Manual</a> <g:link class="btn blueLight" controller="tutorial" action="list">Tutorials</g:link></p>
            </div>
        </article>
    </div>

</div>

</body>
</html>

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
            <h2>Learn Grails <small>in 6 easy steps</small></h2>
            <p>The following 6 steps will take you from knowing nothing about Grails to running a Grails application - all in a matter of minutes! And if you already have some knowledge of the framework, skip the steps you don't need.</p>
            <div class="item">
                <span class="number">1</span>
                <h3>What is Grails?</h3>
                <p>Never heard of Grails? Heard of it but not sure what it is or does? Watch this short presentation for a quick overview.</p>
                <p class="links"><a class="btn blueLight play" href="#modal-pres1" data-toggle="modal" data-backdrop="true">
                    <g:message code="page.learn.play"/>
                </a></p>
            </div>
            <div class="item">
                <span class="number">2</span>
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
            </div>
            <div class="item">
                <span class="number">3</span>
                <h3>Try a sample application</h3>
                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do.</p>
                <p class="links"><a class="btn blueLight" href="#">Download latest release (2.0.1)</a></p>
            </div>
            <div class="item">
                <span class="number">4</span>
                <h3>Download Grails</h3>
                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do.</p>
                <g:render template="/download/downloadLatestButton" model="[downloadFile: latestDownload, softwareVersion: latestVersion]"/>
                <%--
                <p class="links"><a class="btn blueLight" href="#">Download latest release (2.0.1)</a></p>
                --%>
            </div>
            <div class="item">
                <span class="number">5</span>
                <h3>Installation</h3>
                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do.</p>
                <p class="links"><a class="btn blueLight" href="#">Read manual installation</a></p>
            </div>
            <div class="item">
                <span class="number">6</span>
                <h3>Manual & Tutorials</h3>
                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do.</p>
                <p class="links"><a class="btn blueLight" href="#">Manual</a> <a class="btn blueLight" href="#">Tutorials</a></p>
            </div>
        </article>
    </div>

</div>

</body>
</html>

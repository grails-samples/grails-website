<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
    <meta content="master" name="layout"/>
    <r:require modules="homepage"/>

    <feed:meta kind="rss" version="2.0" controller="blog" action="feed" params="[format: 'rss']"/>
    <feed:meta kind="atom" version="1.0" controller="blog" action="feed" params="[format: 'atom']"/>

    <script>
        var _state = 0, _banners, _containerBanner, _time = 10;

        function tick() {
            if (_state == (_banners.length - 1))
                _state = 0;
            else
                _state++;
            _containerBanner.animate({'left':-_state * 980}, 500);
            setTimeout('tick()', _time * 1000);
        }

        $(function () {
            _banners = $('div.banner').detach();
            _containerBanner = $('<div />')
                    .css('position', 'absolute')
                    .css('left', 0).css('top', 0)
                    .width(980 * _banners.length);
            _containerBanner.append(_banners);
            $('div#banner').append(_containerBanner);

            setTimeout("tick()", _time * 1000);
        });
    </script>
</head>

<body>

<div id="banner">
    <div class="springone banner"></div>
    <div class="fullStackFramework banner">
        <div class="left">
            <h2>Grails Framework, Full stack</h2>

            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut <strong>labore et dolore magna aliqua</strong>.
            </p>

            <p>
                <a href="/download" class="btn">Download Grails</a>&nbsp;&nbsp;
                <a href="/learn" class="btn primary">Start using Grails!</a>
            </p>
        </div>
        <ul>
            <li>
                <a href="#">rapid</a>

                <p class="hide">Get instant feedback, see instant results. Grails is the premier dynamic language web framework for the JVM. Lorem ipsum dolor sit amet, consectetur adipisicing elit.</p>
            </li>
            <li>
                <a href="#">dynamic</a>

                <p class="hide">Get instant feedback, see instant results. Grails is the premier dynamic language web framework for the JVM. Lorem ipsum dolor sit amet, consectetur adipisicing elit.</p>
            </li>
            <li>
                <a href="#">robust</a>

                <p class="hide">Get instant feedback, see instant results. Grails is the premier dynamic language web framework for the JVM. Lorem ipsum dolor sit amet, consectetur adipisicing elit.</p>
            </li>
        </ul>
    </div>
</div>

<section class="section-1">
    <h2><strong>What is Grails?</strong> <small>in short</small></h2>
    <p><strong>Grails is an open source, full stack, web application framework for the JVM</strong>. It takes advantage of the <strong><a href="http://groovy.codehaus.org/">Groovy</a></strong> programming language and <strong>convention over configuration</strong> to provide a productive and stream-lined development experience. Powered by <strong><a href="http://springframework.org">Spring</a></strong>, Grails outperforms the competition. <g:link uri="/learn">Learn more</g:link></p>
    <div class="left">
        <h3>Latest News</h3>
        <article class="news">
            <h3><a href="#"><time datetime="2012-06-04">juin 04, 2012</time> Support Crowd-Funded Open-Source Grails Note Taking App Akanoo</a></h3>
            <p>Akanoo will be a Grails-driven, visually appealing, easy-to-use open-source note taking app for web and mobile. It allows you to create, arrange... <a href="#">Read more</a></p>
        </article>
        <article class="news">
            <h3><a href="#"><time datetime="2012-04-08">mai 28, 2012</time> Multiple versions of Grails released: 2.0.4, 1.3.9 & 2.1.RC2</a></h3>
            <p>We're pleased to announce the release of several different versions of Grails: 2.0.4 - Patch release for 2.0.x, 1.3.9 - Patch release for 1.3.x fixing a ... <a href="#">Read more</a></p>
        </article>
        <article class="news">
            <h3><a href="#"><time datetime="2012-04-08">mai 28, 2012</time> Transaction logic engine for Grails 2.0.3</a></h3>
            <p>Grails provides great declarative UI and persistence. But that still leaves all the business logic portion of your apps to be written (and tested and maintained) by hand ... <a href="#">Read more</a></p>
        </article>
        <a href="#">Read more news</a>
    </div>
    <div class="right">
        <div class="twitter">
            <h2>@grailsframework</h2>
            <div id="jstwitter"></div>
            <p class="follow">
                <a href="https://twitter.com/grailsframework" class="btn blue">Follow @grailsframework</a>
            </p>
        </div>
    </div>
</section>

<section class="section-3">
    <div class="col1">
        <h3>Training</h3>
        <p><a href="http://www.springsource.com/training">SpringSource University</a> provides a comprehensive education and certification program for SpringSource software products as well as Spring, Groovy, Grails, and Apache open source technologies.</p>
        <p>We offer both public and customized private training and have trained over 4,000 people. Whichever course you decide to take, you are guaranteed to experience what many before you refer to as “The best Enterprise Software Class I have ever attended”.</p>
    </div>
    <div class="col2">
        <h3>Support &amp; services</h3>
        <p>SpringSource enterprise software and <a href="http://www.springsource.com/services/enterprisesupport">support subscriptions</a> are the way to get developer and production support for Grails and other SpringSource software products and certified versions of our open source technologies including patches, updates, security fixes, and legal indemnification.</p>
        <p><a href="http://www.springsource.com/groovy-grails-consulting">Consulting services</a> are available to companies that wish to leverage the knowledge and expertise of SpringSource’s Grails technology experts.</p>
    </div>

    <div class="col3">
        <h3>Community</h3>
        <p>Get involved! Grails has a vibrant and buzzing community. You can grab the <a href="http://github.com/grails/grails-core">source code</a> from GitHub, report issues on the Grails <a href="http://jira.grails.org/browse/GRAILS">JIRA issue tracker</a>, participate at the <a href="http://grails.org/Mailing+lists">mailing lists</a> or <a href="http://grails.1312388.n4.nabble.com/Grails-user-f1312389.html">Nabble forums</a> or catch-up on the latest news on the <a href="http://grails.org/blog">Grails blog</a>.</p>
        <p>The whole Grails site is written in Grails 2.0.0 and is an open wiki, the <a href="http://github.com/grails/grails-samples/tree/master/grails.org">source code</a> for which is available from Github. Visit the Grails <a href="http://grails.org/Community">community pages</a> for more ways to participate.</p>
    </div>
</section>

</body>
</html>

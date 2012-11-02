<html>
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

            $('#springoneBanner').addClass('springone');

            setTimeout("tick()", _time * 1000);
        });
    </script>
</head>

<body>

<div id="banner">
    <div class="fullStackFramework banner">
        <div class="left">
            <h2>Grails Framework, Full stack</h2>

            <p>Build modern, sophisticated and robust Groovy web applications in record time! <strong>Grails brings back the enjoyment of Java web development</strong>.
            </p>

            <p>
                <a href="/download" class="btn">Download Grails</a>
                <a href="/learn" class="btn primary">Start using Grails!</a>
            </p>
        </div>
        <ul>
            <li>
                <a href="#">rapid</a>

                <p class="hide">Have your next Web project done in weeks instead of months. Grails delivers a new age of Java web application productivity.</p>
            </li>
            <li>
                <a href="#">dynamic</a>

                <p class="hide">Get instant feedback, see instant results. Grails is the premier dynamic language web framework for the JVM.</p>
            </li>
            <li>
                <a href="#">robust</a>

                <p class="hide">Powered by Spring and designed for the JVM, Grails outperforms the competition. Dynamic, agile web development without compromises.</p>
            </li>
        </ul>
    </div>
    <%--
    <div id="springoneBanner" class="banner"></div>
    --%>
</div>

<section class="section-1">
    <h2><strong>What is Grails?</strong> </h2>
    <p><strong>Grails is an Open Source, full stack, web application framework for the JVM</strong>. It takes advantage of the <strong><a href="http://groovy.codehaus.org/">Groovy</a></strong> programming language and <strong>convention over configuration</strong> to provide a productive and stream-lined development experience. <g:link uri="/learn">Learn more</g:link></p>
    <div class="left">
    <div id="#downloadNow" class="downloads">
        <h2>Grab the latest and greatest release!</h2>
        <g:render template="/download/downloadLatestButton"
                  model="[downloadFile: latestBinary, softwareVersion: latestDownload.softwareVersion]"/>
    </div>

        <h3>Latest News</h3>
        <g:each in="${latestNews}" var="newsItem">
            <g:set var="cacheKey" value="${'news_' + newsItem.id}"/>
            <article class="news">
                <h3><g:link controller="newsItem" action="show" id="${newsItem.id}">
                    <time datetime="${joda.format(value:newsItem.dateCreated, pattern:'yyyy-MM-dd')}">
                        <joda:format value="${newsItem.dateCreated}" pattern="dd MMM yyyy" />
                    </time> ${newsItem.title.encodeAsHTML()}</g:link></h3>
                <p>
                    <wiki:shorten key="${cacheKey}" wikiText="${newsItem.body}" length="200"/>
                    <g:link controller="newsItem" action="show" id="${newsItem.id}">Read more</g:link>
                </p>
            </article>                        
        </g:each>
        <g:link controller="newsItem">Read more news</g:link>
    </div>
    <div id="homeRightPanel" class="right">

        
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
        <p><a href="http://www.springsource.com/training">SpringSource University</a> provides a comprehensive education and certification program for SpringSource software products as well as Spring, Groovy, Grails, and Apache Open Source technologies.</p>
        <p>We offer both public and customized private training and have trained over 4,000 people. Whichever course you decide to take, you are guaranteed to experience what many before you refer to as “The best Enterprise Software Class I have ever attended”.</p>
    </div>
    <div class="col2">
        <h3>Support &amp; services</h3>
        <p>SpringSource enterprise software and <a href="http://www.springsource.com/services/enterprisesupport">support subscriptions</a> are the way to get developer and production support for Grails and other SpringSource software products and certified versions of our Open Source technologies including patches, updates, security fixes, and legal indemnification.</p>
        <p><a href="http://www.springsource.com/groovy-grails-consulting">Consulting services</a> are available to companies that wish to leverage the knowledge and expertise of SpringSource’s Grails technology experts.</p>
    </div>

    <div class="col3">
        <h3>Community</h3>
        <p>Get involved! Grails has a vibrant and buzzing community. You can grab the <a href="http://github.com/grails/grails-core">source code</a> from GitHub, report issues on the Grails <a href="http://jira.grails.org/browse/GRAILS">JIRA issue tracker</a>, participate at the <a href="/Mailing+lists">mailing lists</a> or <a href="http://grails.1312388.n4.nabble.com/Grails-user-f1312389.html">Nabble forums</a> or catch-up on the latest news on the <a href="/blog">Grails blog</a>.</p>
        <p>The whole Grails site is written in Grails 2.0.0 and is an open wiki, the <a href="http://github.com/grails/grails-samples/tree/master/grails.org">source code</a> for which is available from Github. Visit the Grails <a href="/Community">community pages</a> for more ways to participate.</p>
    </div>
</section>

</body>
</html>

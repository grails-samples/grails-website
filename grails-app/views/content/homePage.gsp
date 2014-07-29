<head>
    <meta content="master" name="layout"/>
    <r:require modules="homepage"/>

    <%-- RSS feeds --%>
    <link rel="alternate" type="application/rss+xml" title="Wiki Updates RSS 2.0" href="/wiki/latest?format=rss"/>
    <link rel="alternate" type="application/atom+xml" title="Wiki Updates Atom 1.0" href="/wiki/latest?format=atom"/>
    <link rel="alternate" type="application/rss+xml" title="Latest Screencasts RSS 2.0" href="/screencast/feed?format=rss"/>
    <link rel="alternate" type="application/atom+xml" title="Latest Screencasts Atom 1.0" href="/screencast/feed?format=atom"/>
    <link rel="alternate" type="application/rss+xml" title="Latest Plugins RSS 2.0" href="/plugins/feed?format=rss"/>
    <link rel="alternate" type="application/atom+xml" title="Latest Plugins Atom 1.0" href="/plugins/feed?format=atom"/>

    <script>

        var _state = 0, _banners, _containerBanner, _time = 10;
        var _width;
        function tick() {
            if (_state == (_banners.length - 1))
                _state = 0;
            else
                _state++;
            _containerBanner.animate({'left':-_state * _width}, 500);
            setTimeout('tick()', _time * 1000);
        }

        $(function () {
            _width =  $(".banner").width();
            _banners = $('div.banner').detach();
            _containerBanner = $('<div />')
                    .css('position', 'absolute')
                    .css('left', 0).css('top', 0)
                    .width(_width * _banners.length);
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
                <a href="#">rapid
                  <p class="hide">Have your next Web project done in weeks instead of months. Grails delivers a new age of Java web application productivity.</p>
                </a>
            </li>
            <li>
                <a href="#">dynamic
                  <p class="hide">Get instant feedback, see instant results. Grails is the premier dynamic language web framework for the JVM.</p>
                </a>
            </li>
            <li>
                <a href="#">robust
                  <p class="hide">Powered by Spring and designed for the JVM, Grails outperforms the competition. Dynamic, agile web development without compromises.</p>
                </a>
            </li>
        </ul>
    </div>

    <div id="springoneBanner" class="banner">
        <a href="http://springone2gx.com"><g:img dir="img/banner" file="springone2gx2014_banner_1200x300.png" /></a>
    </div>
    
    <div id="skyBanner" class="sky caseStudy banner">
        <p class="ico">
            <g:img dir="img/banner/caseStudy" file="logo.png" />
        </p>
        <h2>Sky Video Gallery & Sky TV Guide</h2>
        <p class="links">
            <a href="http://video.sky.com">video.sky.com</a> &amp;
            <a href="http://tv.sky.com">tv.sky.com</a>
        </p>
        <p class="desc">“Groovy is so much quicker and simpler to write code with, so we can get applications up and running faster,” Mullen confirms. “With Groovy and Grails we can create a new feature in a week, when before it could easily take a month or more.”</p>
        <div class="screen">
            <p class="extra">Sky Video</p>
            <a href="http://video.sky.com">Companies using Grails</a>
        </div>
        <ul class="logos">
            <li><a href="http://www.atlassian.com/"><g:img dir="img/banner/caseStudy/logos" file="atlassian.png" title="Atlassian" /></a></li>
            <li><a href="http://sky.com"><g:img dir="img/banner/caseStudy/logos" file="sky.png" title="Sky" /></a></li>
            <li><a href="http://www.linkedin.com/"><g:img dir="img/banner/caseStudy/logos" file="linkedin.png" title="Linkedin" /></a></li>
            <li><a href="http://www.eharmony.com/"><g:img dir="img/banner/caseStudy/logos" file="eharmony.png" title="eHarmony" /></a></li>
            <li><a href="http://espn.go.com/"><g:img dir="img/banner/caseStudy/logos" file="espn.png" title="ESPN" /></a></li>
            <li><a href="https://www.virtuwell.com/"><g:img dir="img/banner/caseStudy/logos" file="virtuwell.png" title="Virtuwell" /></a></li>
            <li><a href="https://netflix.com/"><g:img dir="img/banner/caseStudy/logos" file="netflix.png" title="NETFLIX" /></a></li>
        </ul>
    </div>    
</div>

<section class="section-1">
    <h2><strong>What is Grails?</strong> </h2>
    <p><strong>Grails is an Open Source, full stack, web application framework for the JVM</strong>. It takes advantage of the <strong><a href="http://groovy.codehaus.org/">Groovy</a></strong> programming language and <strong>convention over configuration</strong> to provide a productive and stream-lined development experience. <g:link uri="/learn">Learn more</g:link></p>
    <div class="colset-2">
        <div class="left">
            <div id="#downloadNow" class="downloads downloads-small">
                <h2>Grab the latest and greatest release!</h2>
                <p>You can find other Grails releases and methods of installation on <g:link controller="download" action="index">the downloads page.</g:link></p>
                <g:render template="/download/downloadLatestButton" model="[downloadFile: latestBinary, softwareVersion: latestDownload.softwareVersion]"/>
            </div>

            <h3 class="news-title">Latest News <small>(<g:link controller="newsItem">Read more</g:link>)</small></h3>
            <g:each in="${latestNews}" var="newsItem">
                <g:set var="cacheKey" value="${'news_' + newsItem.id}"/>
                <article class="news">
                    <h3><g:link controller="newsItem" action="show" id="${newsItem.id}">
                        ${newsItem.title.encodeAsHTML()}</g:link></h3>
                    <p class="date">Published on <time datetime="${joda.format(value:newsItem.dateCreated, pattern:'yyyy-MM-dd')}">
                        <joda:format value="${newsItem.dateCreated}" pattern="dd MMM yyyy" />
                    </time></p>
                    <p>
                        <wiki:shorten key="${cacheKey}" wikiText="${newsItem.body}" length="200"/>
                        <g:link controller="newsItem" action="show" id="${newsItem.id}">Read more</g:link>
                    </p>
                </article>
            </g:each>
        </div>
        <div class="right">
            <div class="twitter">
                <h2>@grailsframework</h2>
                <div id="jstwitter"></div>
                <p class="follow">
                    <a href="https://twitter.com/grailsframework" class="btn blue">Follow @grailsframework</a>
                </p>
            </div>
            <h3 class="news-title" style="margin-top:67px">Training Events</h3>
            <div class='PivotalAdBannerDiv'></div>
            <r:script>
            (function() {
                var s = document.createElement('script');
                s.type = 'text/javascript';
                s.async = true;
                s.src = 'http://bserv.cfapps.io/gb/www.grails.org';
                var x = document.getElementsByTagName('script')[0];
                x.parentNode.insertBefore(s, x);
            })();
            </r:script>            
        </div>
    </div>
</section>

<section class="section-3">
    <div class="col1">
        <h3>Training</h3>
        <p><a href="http://www.gopivotal.com/training">Pivotal Training</a> provides a comprehensive education and certification program for Pivotal software products as well as Spring, Groovy, Grails, and Apache Open Source technologies.</p>
        <p>We offer both public and customized private training and have trained over 4,000 people. Whichever course you decide to take, you are guaranteed to experience what many before you refer to as “The best Enterprise Software Class I have ever attended”.</p>
    </div>
    <div class="col2">
        <h3>Support &amp; services</h3>
        <p>Pivotal <a href="http://www.gopivotal.com/support/oss">support subscriptions</a> are the way to get developer and production support for Grails and other Pivotal software products and certified versions of our Open Source technologies including patches, updates, security fixes, and legal indemnification.</p>
        <p><a href="http://www.gopivotal.com/contact/spring-consulting">Consulting services</a> are available to companies that wish to leverage the knowledge and expertise of Pivotal's Grails technology experts.</p>
    </div>

    <div class="col3">
        <h3>Community</h3>
        <p>Get involved! Grails has a vibrant and buzzing community. You can grab the <a href="http://github.com/grails/grails-core">source code</a> from GitHub, report issues on the Grails <a href="http://jira.grails.org/browse/GRAILS">JIRA issue tracker</a>, participate at the <a href="/Mailing+lists">mailing lists</a> or <a href="http://stackoverflow.com/tags/grails">Stack Overflow</a> or catch-up on the <a href="/news">latest news</a>.</p>
        <p>This whole web site is written in Grails, the <a href="http://github.com/grails-samples/grails-website/tree/master">source code</a> for which is available from GitHub. Visit the Grails <a href="/community">community pages</a> for more ways to participate.</p>
    </div>
</section>

</body>

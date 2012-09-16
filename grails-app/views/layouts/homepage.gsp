<!DOCTYPE html>
<!--[if lt IE 7 ]> <html class="ie6"> <![endif]-->
<!--[if IE 7 ]>    <html class="ie7"> <![endif]-->
<!--[if IE 8 ]>    <html class="ie8"> <![endif]-->
<!--[if IE 9 ]>    <html class="ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html class=""> <!--<![endif]-->
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta name="robots" content="NOODP">	
    <meta name="Description" content="Grails is a high-productivity web framework based on the Groovy language that embraces the coding by convention paradigm, but is designed specifically for the Java platform.">	
	
    <title>Grails - The search is over.</title>
    
    <r:require modules="homepage"/>
    <r:external uri="/images/favicon.ico"/>
    <r:layoutResources/>
    
    <g:layoutHead />
    
    <r:script>
var startIndex = Math.floor(Math.random() * 3)
// Set the ads up for cycling every 4s
var ads = $$('div.advert');

/*
if (startIndex < 2) {
    ads.slice(startIndex + 1).invoke('hide');
}

new PeriodicalExecuter(function(pe) {
    var firstHiddenAd = ads.find(function(el) { return !el.visible(); });
    if (!firstHiddenAd) {
        ads.slice(1).invoke('fade');
    }
    else {
        firstHiddenAd.appear();
    }
}, 4);
*/
    </r:script>
    <%--
    <style>
#events {
    left: 635px;
    top: 2px;
    background: none;
    width: 270px;
    height: 270px;
}

#events h2 {
    font-weight: bold;
    color: #363636;
    font-size: 10pt;
    margin: 0px 0px 0px 8px;
    padding: 0px;
}

    </style>
    --%>
</head>
<body>

<div align="center">
	<g:render template="/content/logos"></g:render>
    <div class="mainMenuBarWrapper">
		<g:render template="/content/mainMenuBar" />
    </div><!-- mainMenuBarWrapper -->
</div><!-- center -->

<div id="graphicHeader"></div>

<div id="barDecoration"></div>

<div id="contentWrapper">
    <div id="contentCenter" align="center">
        <div id="contentArea">
            
            <div id="grailsAttributes">
                <div class="left">
        	        <h2>Rapid</h2>
                    <p>Have your next Web 2.0 project done in weeks instead of months. Grails delivers a new age of Java web application productivity.</p>		
                </div><!-- left column -->
                <div class="center">
        			<h2>Dynamic</h2>
                    <p>Get instant feedback, see instant results. Grails is the premier dynamic language web framework for the JVM.</p>
                </div><!-- center column -->
                <div class="right">
        			<h2>Robust</h2>
                    <p>Powered by <a href="http://springframework.org">Spring</a>, Grails outperforms the competition. Dynamic, agile web development without compromises.</p>
                </div><!-- right column -->
            </div><!-- grailsAttributes -->
            
            <div class="eventDownloadWrapper">
                <div id="screencasts">
                    <h2>What is grails?</h2>
                    <div class="castBox">
                        <div class="castScreen">
                            <a href="http://portal.sliderocket.com/vmware/what-is-grails">
                                <r:img width="240" height="190" uri="/images/grails-taster-badge.png"/>
                            </a>
                        </div><!-- castScreen -->
                        <div class="playOverlay">
                            <a href="http://portal.sliderocket.com/vmware/what-is-grails">
                                <r:img width="50" height="51" uri="/images/play.png"/>
                            </a>
                        </div>
                        <h4><g:link controller="content" action="gettingStarted">Find out more</g:link></h4>
                    </div><!-- castBox -->
                </div><!-- screencasts -->

                <div id="downloadBox">
                    <div class="downloadPluginWrapper">
                        <div id="download">
                            <download:latest var="grailsDownload" software="Grails" />
                            <h3>
                                <g:if test="${grailsDownload}">
                                    <download:link software="Grails" version="${grailsDownload?.softwareVersion}" file="Binary Zip">
                                        <r:img uri="/images/new/download_button.png" 
                                               border="0"
                                               title="Download Grails ${grailsDownload?.softwareVersion}"
                                               alt="Download Grails ${grailsDownload?.softwareVersion}" />
                                    </download:link>								
                                </g:if>
                                <g:else>
                                    <g:link controller="content" id="Download">
                                        <r:img uri="/images/new/download_button.png" 
                                               border="0"
                                               alt="Download Grails" />								
                                    </g:link>
                                </g:else>
                            </h3>
                            <p id="downloadVersion">${grailsDownload?.softwareVersion}</p>
                            <p>Download the latest Grails&#8482 ${grailsDownload?.softwareVersion} binary release to experience a new level of productivity on the Java&#8482 platform.</p>
                            <h4><g:link controller="content" id="Download">More Downloads</g:link></h4>
                        </div><!-- download -->
                        <div id="plugins">
                            <h3>
                                <g:link controller="plugin" action="home">
                                    <r:img uri="/images/new/plugins_button.png" alt="Grails Plugins" border="0" />
                                </g:link>
                            </h3>
                            <ul>
                                <g:each var="plugin" in="${newestPlugins}">
                                    <li><g:link controller="plugin" action="show" params="[name:plugin.name]"><wiki:shorten text="${plugin.title}" /></g:link></li>
                                </g:each>
                            </ul>
                            <h4><g:link controller="plugin" action="home">View All</g:link></h4>
                        </div><!-- plugins -->
                    </div><!-- downloadPluginWrapper -->
                </div><!-- downloadBox -->
            </div><!-- eventDownloadWrapper -->
            
            <div class="newsScreencastWrapper">
                <div id="latestNews">
                    <h2>Latest News</h2>
                    <div id="newsBox">
                        <ul>
                            <g:each var="newsItem" in="${newsItems}">
                                <li>
                                    <g:link controller="blog"  action="showEntry" params="[author:newsItem.author, title: newsItem.title]">
                                        <div class="detail">
                                            <h4><wiki:shorten length="50" text="${newsItem.title}" /></h4>
                                            <div class="author">by ${newsItem.author} </div>
                                            <div class="comments">${newsItem.comments.size()} comments</div>
                                        </div>
                                        <div class="calendar">
                                            <div class="month">${newsItem.month}</div>
                                            <div class="day">${newsItem.day}</div>
                                        </div>
                                    </g:link>
                                </li>
                            </g:each>
                        </ul>
                        <div class="actions">
                            <g:link controller="blog" action="list">More news</g:link> |	
                            <g:link controller="blog" action="createEntry">Add news</g:link> | <g:link controller="blog" action="feed" params="[format:'rss']">Subscribe</g:link>
                        </div><!-- actions -->
                    </div><!-- newsBox -->
                </div><!-- latestNews -->
            
                <div id="events">
                    <%--
                    <h2>Conferences</h2>
                    <div class="castBox">
                        <div class="advert">
                            <a href="http://www.springone2gx.com/conference/chicago/2011/10/register">
                                <r:img width="250" height="230" uri="/images/SpringOne2GX_Banner_250x240.png"/>
                            </a>
                        </div>
                        <div class="advert">
                            <a href="http://www.springsource.com/events/s2gforum-5-31-2011-london">
                                <r:img width="250" height="230" uri="/images/s2gforum-london-2011-240.png"/>
                            </a>
                        </div>
                    </div>
                    --%>
                    <h3>Training Events</h3>
                    <wiki:text page="Training Events" />
                </div><!-- events -->
                
                <div id="globeGraphic"></div>
            </div><!-- newsScreencastWrapper -->
            
        </div><!-- contentArea -->
    </div><!-- contentCenter -->
    
    <div id="grailsOptionsWrapper">
        <div id="btmSectionGraphicsWrapper">
            <div id="mountainLeft"></div>
            <div id="knight"></div>
            <div id="mountainRight"></div>
            <div id="castle"></div>
        </div><!-- btmSectionGraphicsWrapper-->
        <div id="btmSectionBackgroundStretch">
            <div align="center">
                <div id="grailsOptions">
                    <div class="left">
            	        <h3>Training</h3>
                        <p><a href="http://mylearn.vmware.com/mgrReg/plan.cfm?plan=31719&ui=www_edu">VMware</a> provides a comprehensive education and certification program for VMware software products as well as Spring, Groovy, Grails, and Apache open source technologies.
	<br><br>
	 We offer both public and customized private training and have trained over 4,000 people. Whichever course you decide to take, you are guaranteed to experience what many before you refer to as “The best Enterprise Software Class I have ever attended”.
						</p>		
                    </div><!-- left column -->
                    <div class="center">
            			<h3>Support &amp; Services</h3>
                        <p>VMware enterprise software and <a href="http://www.vmware.com/support/product-support/vfabric-suite.html">support subscriptions</a> are the way to get developer and production support for Grails and other SpringSource software products and certified versions of our open source technologies including patches, updates, security fixes, and legal indemnification. 
	<br><br><a href="http://www.springsource.org/consulting">Consulting services</a> are available to companies that wish to leverage the knowledge and expertise of VMware’s Grails technology experts.</p>
                    </div><!-- center column -->
                    <div class="right">
                        <h3>Community</h3>
                        <p>
Get involved! Grails has a vibrant and buzzing community. You can grab the <a href="https://github.com/grails-samples/grails-website">source code</a> from GitHub, report issues on the Grails  <a href="http://jira.grails.org/browse/GRAILS">JIRA issue tracker</a>, participate at the <a href="http://grails.org/Mailing+lists">mailing lists</a> or <a href="http://grails.1312388.n4.nabble.com/Grails-user-f1312389.html">Nabble forums</a> or catch-up on the latest news on the <g:link controller="blog" action="list">Grails blog</g:link>.							
<br></br><br></br>

The whole Grails site is written in Grails <g:meta name="app.grails.version" /> and is an open wiki,  the <a href="http://github.com/grails/grails-samples/tree/master/grails.org">source code</a> for which is available from Github.
Visit the Grails <g:link controller="content" id="Community">community pages</g:link> for more ways to participate. 
                        </p>
                    </div><!-- right column -->
                </div><!-- grailsOptions -->
            </div><!-- center -->
        </div><!-- btmSectionBackgroundStretch -->
    </div><!-- grailsOptionsWrapper -->
</div><!-- contentWrapper -->

<div id="footer">
    <div align="center">
        <div class="innerFooter">
            <a href="http://www.jfrog.org/">
                <r:img uri="/images/artifactory-logo.png" class="artifactory" alt="Artifactory logo" title="In association with JFrog"/>
            </a>
            <a href="http://twitter.com/grailsframework"><div class="twitter"></div></a>
            <p>
            <a href="http://www.vmware.com/help/legal.html">Terms of Use</a> |
            <a href="http://www.vmware.com/help/privacy.html">Privacy</a>
            &nbsp;&nbsp;&nbsp;&nbsp;&copy; Copyright 2009-2012 VMware.<br/>All Rights Reserved.
            </p>
        </div><!-- innerFooter -->
    </div><!-- center -->
</div><!-- footer -->

    <%-- Google Analytics --%>
    <g:render template="/content/analytics" />
    
    <r:layoutResources/>
</body>
</html>

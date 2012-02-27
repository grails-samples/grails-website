	<div id="search">
	    <!-- <g:form method="GET" url="[controller:'content', action:'search']" name="searchForm">
	        <input type="text" accessKey="s" name="q" ${params.q ? 'value="' + params.q + '"' : ''}/></g:form> -->
	</div><!-- search -->	 
	<div id="nav">
	            <ul>
	                <li id="start" class="header"><g:link controller="content" action="gettingStarted">Get Started</g:link>
	                <ul>
	                    <li><g:link controller="content" id="Installation">Installation</g:link></li>
	                    <li><g:link controller="content" id="Quick Start">Quick Start</g:link></li>
	                    <li><g:link controller="content" id="IDE Integration">IDE Setup</g:link></li>
	                    <li><g:link controller="tutorial" action="index">Tutorials</g:link></li>
	                    <plugin:isAvailable name="screencasts">
	                    	<li><g:link controller="screencast" action="list">Screencasts</g:link></li>
                            </plugin:isAvailable>
	                </ul>
	                </li>
	                <li id="reference" class="header"><a href="http://grails.org/doc/latest">Reference</a>
	                <ul>
	                    <li><g:link controller="content" id="Documentation">Documentation</g:link></li>
	                    <li><g:link controller="content" id="FAQ">FAQs</g:link></li>
	                    <li><g:link controller="content" id="Roadmap">Roadmap</g:link></li>
	                </ul>
	                </li>
	                <li id="community" class="header"><g:link controller="content" id="Community">Community</g:link>
	                <ul>
	                    <li><g:link controller="webSite" action="list">Sites using Grails</g:link></li>
	                    <plugin:isAvailable name="jobs">
	                        <li><g:link controller="job" action="list">Job Listings</g:link></li>    
	                    </plugin:isAvailable>
	                    <li><g:link controller="content" id="Testimonials">Testimonials</g:link></li>
	                    <li><g:link controller="content" id="Contribute">Contribute</g:link></li>
	                    <li><a href="http://jira.grails.org/browse/GRAILS">Issue Tracker</a></li>
	                    <li><a href="http://github.com/grails/grails-core/">Source code</a></li>	
	                    <li><g:link controller="content" id="Plugins">Plugins</g:link></li>
	                    <li><g:link controller="content" id="Mailing lists">Mailing Lists</g:link></li>
						<li><a href="http://grails.1312388.n4.nabble.com/Grails-user-f1312389.html">Nabble Forums</a></li>
						<li><a href="http://webchat.freenode.net/?channels=grails">IRC Webchat</a></li>
                        <li>&nbsp;</li>
                        <li>Find us on:<br />
                        <a href="http://twitter.com/#!/grailsframework"><img src="http://a4.twimg.com/help/1307050985_2229" style="border:0;padding-top:10px;width:32px;height:32px;"/></a>
                        <a href="https://plus.google.com/117411438136918964913?prsrc=3" style="text-decoration:none;"><img src="https://ssl.gstatic.com/images/icons/gplus-32.png" alt="" style="border:0;padding-top:10px;width:32px;height:32px;"/></a>
                        <a href="http://www.facebook.com/groups/5965658003/" style="text-decoration:none;"><r:img uri="/images/f_logo.png" style="border:0;padding-top:10px;width:32px;height:32px;"/></a>
                        </li>
	                </ul>
	                </li>
	    <!--			<li><a href="">Weblog</a></li>-->
	            </ul>
	</div><!-- / nav -->	


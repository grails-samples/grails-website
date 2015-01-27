<%@page import="grails.util.Environment" 
%><g:set var="titlepart"><g:layoutTitle default="Grails - The search is over."/></g:set><g:set var="headpart"> 
    <asset:javascript src="master.js"/>
    <asset:stylesheet src="master.css"/>
    <g:if test="${Environment.current != Environment.PRODUCTION}">
        <script>
            var disqus_developer = 1;
        </script>
    </g:if>

    <link rel="shortcut icon" href="${resource(dir: 'img', file: 'favicon.ico')}" type="image/x-icon">

    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="${resource(dir: 'img', file: 'grails-icon-144x144.png')}">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="${resource(dir: 'img', file: 'grails-icon-114x114.png')}">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="${resource(dir: 'img', file: 'grails-icon-72x72.png')}">
    <link rel="apple-touch-icon-precomposed" href="${resource(dir: 'img', file: 'grails-icon-57x57.png')}">


    <link href='https://fonts.googleapis.com/css?family=PT+Sans+Narrow:400,700' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Magra:400' rel='stylesheet' type='text/css'>

    <g:layoutHead/>
</g:set><g:set var="bodypart">
    <g:layoutBody/>

    <footer id="footer" role="contentinfo">
        <div class="buildWithGrails">
            Built with Grails <g:meta name="app.grails.version" />
        </div>

        <div class="copyright">
            Copyright © 2014 Pivotal Software, Inc. All rights reserved.<br/>
            All Rights Reserved.
        </div>
        <ul class="privacy">
            <li><a href="http://www.pivotal.io/privacy-policy">Terms of Use</a></li>
            <li class="last"><a href="http://www.pivotal.io/privacy-policy">Privacy</a></li>
        </ul>
        <ul class="external-services">
            <li class="last"><a class="artifactory" href="http://www.jfrog.com/">Artifactory</a></li>
        </ul>
    </footer>
</g:set><g:set var="bodyendpart">
<asset:deferredScripts/>
<script type="text/javascript">
(function() {
  var didInit = false;
  function initMunchkin() {
    if(didInit === false) {
      didInit = true;
      Munchkin.init('625-IUJ-009');
    }
  }
  var s = document.createElement('script');
  s.type = 'text/javascript';
  s.async = true;
  s.src = document.location.protocol + '//munchkin.marketo.net/munchkin.js';
  s.onreadystatechange = function() {
    if (this.readyState == 'complete' || this.readyState == 'loaded') {
      initMunchkin();
    }
  };
  s.onload = initMunchkin;
  document.getElementsByTagName('head')[0].appendChild(s);
})();
</script>
</g:set><stemplate:render template="/templates/plugins.html" parts="[head: headpart, title: titlepart, maincontent: bodypart, bodyend: bodyendpart]" />
<ul>
    <g:set var="nameWithGrails">grails-${fullName}</g:set>
    
    <li><g:link controller="repository" action="artifact" params="[type:'zip', plugin:plugin, fullName: nameWithGrails]" absolute="true">grails-${fullName}.zip</a></g:link>
    <li><g:link controller="repository" action="artifact" params="[type:'pom', plugin:plugin, fullName: nameWithGrails]" absolute="true">grails-${fullName}.pom</a></g:link>
    <li><g:link controller="repository" action="artifact" params="[type:'xml', plugin:plugin, fullName: nameWithGrails]" absolute="true">grails-${fullName}-plugin.xml</a></g:link>        
</ul>
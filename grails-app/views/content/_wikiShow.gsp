<g:setProvider library="yui"/>

<g:set var="updateElement" value="${update ?: 'contentPane'}"/>

<div id="wikiLastUpdated">Last updated by ${latest?.author?.login} <prettytime:display date="${content.lastUpdated}"/></div>
<g:render template="/content/viewActions" model="[content:content, update:updateElement]" />
<g:render template="/common/messages" model="${pageScope.getVariables() + [bean:content]}" />

<wiki:text key="${content?.title}">
    ${content?.body}
</wiki:text>
<script type="text/javascript">
   if(myYUI.get('message')!=null) {
        myYUI.fade('message', {delay:3});
   }
</script>

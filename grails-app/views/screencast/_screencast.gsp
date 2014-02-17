<article class="item">
    <header>
        <h3 style="padding: 5px 0;">
            <a href="/screencast/${screencastInstance?.id}">${screencastInstance?.title?.encodeAsHTML()}</a>
            <g:if test="${screencastInstance?.isNew}">
                <g:img dir="img/page" file="new.png" />
            </g:if>
        </h3>
        <p class="meta">
            Tags:
            <g:if test="${screencastInstance.tags.size() > 0}">
                <g:each in="${screencastInstance.tags}" var="tag" status="i"><g:if test="${i > 0}">, </g:if><g:link action="tagged" params="[tag:tag]">${tag}</g:link></g:each>
            </g:if>
            <g:else>
                none
            </g:else>
            <br/>
            submitted by ${screencastInstance?.submittedBy?.login}
            <g:if test="${screencastInstance?.dateCreated}">
                <prettytime:display date="${screencastInstance?.dateCreated}"/>
            </g:if>
        </p>

    </header>

    <div class="desc">
        <p><wiki:shorten key="${'screencast_' + screencastInstance.id}" wikiText="${screencastInstance?.description}" length="250"/>
            <g:if test="${screencastInstance?.description?.length() > 250}">
                <a href="/screencast/${screencastInstance?.id}">(read more)</a>
            </g:if>
        </p>
    </div>
</article>

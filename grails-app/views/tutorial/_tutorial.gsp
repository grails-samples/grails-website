<article class="tutorial">
    <a href="#" class="like ${!tutorialInstance?.isNew ?: 'with-ribbon'}">+${tutorialInstance?.popularity?.netLiked}</a>
    <div>
        <h3><a href="/tutorial/${tutorialInstance?.id}">${tutorialInstance?.title?.encodeAsHTML()}</a></h3>

        <p class="tags">
            <g:each in="${tutorialInstance?.tags}" var="tag">
                <a href="#" class="btn blueLight">${tag}</a>
            </g:each>
        </p>

        <p><wiki:shorten text="${tutorialInstance?.description?.encodeAsHTML()}" length="250"/>
            <g:if test="${tutorialInstance?.description?.length() > 250}">
                <a href="/tutorial/${tutorialInstance?.id}">read more</a>
            </g:if>
        </p>

        <p class="link">
            <a href="${tutorialInstance.url.encodeAsHTML()}" target="_blank">${tutorialInstance?.url?.encodeAsHTML()}</a>
        </p>
    </div>
    <g:if test="${tutorialInstance?.isNew}">
        <span class="status status-new">New</span>
    </g:if>
</article>
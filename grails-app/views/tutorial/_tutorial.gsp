<article class="item">
    <header>
        <h3>
            <a href="/tutorial/${tutorialInstance?.id}">
                ${tutorialInstance?.title?.encodeAsHTML()}
                <g:if test="${tutorialInstance?.isNew}">
                    <g:img dir="img/page" file="new.png" />
                </g:if>
            </a>
        </h3>
        <p class="meta">
            <g:message code="tutorial.tags" />:
            <g:if test="${tutorialInstance.tags.size() > 0}">
                <g:each in="${tutorialInstance.tags}" var="tag" status="i"><g:if test="${i > 0}">, </g:if><g:link action="tagged" params="[tag:tag]">${tag}</g:link></g:each>
            </g:if>
            <g:else>
                <g:message code="tutorial.tags.empty" />
            </g:else>
            <br/>
            <g:message code="tutorial.submitted.by" />${tutorialInstance?.submittedBy?.login}
            <g:if test="${tutorialInstance?.dateCreated}">
                <prettytime:display date="${tutorialInstance?.dateCreated}"/>
            </g:if>
        </p>

    </header>

    <div class="desc">
        <p>
            <wiki:shorten key="${'tutorial_' + tutorialInstance.id}" wikiText="${tutorialInstance?.description}" length="250"/>
            <g:if test="${tutorialInstance?.description?.length() > 250}">
                <a href="/tutorial/${tutorialInstance?.id}">read more</a>
            </g:if>
        </p>
    </div>

</article>

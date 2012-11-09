<section class="box">
    <article>
        <header>
            <h3>
                <a href="/tutorial/${tutorialInstance?.id}">${tutorialInstance?.title?.encodeAsHTML()}</a>
            </h3>
            <p class="meta">
                Tags :
                <g:if test="${tutorialInstance.tags.size() > 0}">
                    <g:each in="${tutorialInstance.tags}" var="tag" status="i"><g:if test="${i > 0}">, </g:if><g:link action="tagged" params="[tag:tag]">${tag}</g:link></g:each>
                </g:if>
                <g:else>
                    none
                </g:else>
                <br/>
                submitted by <a href="#">${tutorialInstance?.submittedBy?.login}</a>
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
        <g:if test="${tutorialInstance?.isNew}">
            <span class="status status-new">New</span>
        </g:if>
    </article>
</section>

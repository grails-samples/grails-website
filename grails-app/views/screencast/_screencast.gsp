<section class="plugin">
    <article>
        <header>
            <h3 style="padding: 5px 0;">
                <a href="/screencast/${screencastInstance?.id}">${screencastInstance?.title?.encodeAsHTML()}</a>
            </h3>
            <p class="meta">
                Tags :
                <g:if test="${screencastInstance.tags.size() > 0}">
                    <g:each in="${screencastInstance.tags}" var="tag" status="i"><g:if test="${i > 0}">, </g:if><a href="#">${tag}</a></g:each>
                </g:if>
                <g:else>
                    none
                </g:else>
                <br/>
                submitted by <a href="#">${screencastInstance?.submittedBy?.login}</a>
                <prettytime:display date="${screencastInstance?.dateCreated}"/>
            </p>

        </header>

        <div class="desc">
            <p><wiki:shorten text="${screencastInstance?.description?.encodeAsHTML()}" length="250"/>
                <g:if test="${screencastInstance?.description?.length() > 250}">
                    <a href="/tutorial/${screencastInstance?.id}">read more</a>
                </g:if>
            </p>
        </div>
        <g:if test="${screencastInstance?.isNew}">
            <span class="status status-new">New</span>
        </g:if>
    </article>
</section>
<section>
    <h3>Best Grails plugins downloads</h3>
    <p>Grails has a number of plugins available for it that extend its capability. Check out the <a href="/plugins">Plugins page</a> for more info on available plugins and how they can be installed.</p>
    <div class="plugins-list" id="plugins-list">
        <ul>

            <g:each in="${pluginList}" var="${plugin}" status="i">
                <li class="${ i % 2 == 0 ? 'odd' : 'even'}">
                    <h3><g:link controller="plugin" action="plugin" id="${plugin?.name?.encodeAsHTML()}">${plugin?.title?.encodeAsHTML()}</g:link></h3>
                    <g:if test="${plugin?.title?.encodeAsHTML()}">
                        <p>supported by SpringSource</p>
                    </g:if>
                    <p class="rating">
                        <span class="star-100"></span>
                        <span class="star-100"></span>
                        <span class="star-100"></span>
                        <span class="star-50"></span>
                        <span class="star-0"></span>
                        <span class="note">999</span>
                    </p>
                    <p class="used">
                        Used in <strong>~68%</strong> of apps
                    </p>
                </li>
            </g:each>

        </ul>
        <div class="buttonbars"><a href="/plugins" class="btn btn-large blueLight">See all plugins</a></div>
    </div>
</section>
<div class="control-group ${hasErrors(bean: downloadInstance, field: 'softwareName', 'error')}">
    <label class="control-label" for="softwareName">Software Name</label>
    <div class="controls">
        <g:textField name="softwareName" value="${downloadInstance.softwareName}" class="input-xxlarge" />
    </div>
</div>

<div class="control-group ${hasErrors(bean: downloadInstance, field: 'softwareVersion', 'error')}">
    <label class="control-label" for="softwareVersion">URL</label>
    <div class="controls">
        <g:textField name="softwareVersion" value="${downloadInstance.softwareVersion}" class="input-xxlarge" />
    </div>
</div>

<div class="control-group ${hasErrors(bean: downloadInstance, field: 'releaseDate', 'error')}">
    <label class="control-label" for="releaseDate">Release Date</label>
    <div class="controls">
        <joda:dateTimePicker name="releaseDate" value="${downloadInstance.releaseDate}"/>
    </div>
</div>

<div class="control-group">
    <label class="control-label" for="latestRelease">Latest Release?</label>
    <div class="controls">
        <label class="checkbox">
            <g:checkBox name="latestRelease" value="${downloadInstance.latestRelease}"/>
            If this is checked, this download will appear as the current version of Grails
        </label>
    </div>
</div>

<div class="control-group">
    <label class="control-label" for="betaRelease">Beta Release?</label>
    <div class="controls">
        <label class="checkbox">
            <g:checkBox name="betaRelease" value="${downloadInstance.betaRelease}"/>
            If this is checked, this download will appear as a 'Beta' in the downloads page
        </label>
    </div>
</div>
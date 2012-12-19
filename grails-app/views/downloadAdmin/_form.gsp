
<div class="control-group ${hasErrors(bean: releaseInfo, field: 'softwareVersion', 'error')}">
    <label class="control-label" for="softwareVersion">Version</label>
    <div class="controls">
        <g:textField name="softwareVersion" value="${releaseInfo.softwareVersion}" class="input-xxlarge" />
    </div>
</div>

<div class="control-group ${hasErrors(bean: releaseInfo, field: 'binaryZip', 'error')}">
    <label class="control-label" for="binaryZip">Binary Zip URL</label>
    <div class="controls">
        <g:textField name="binaryZip" value="${releaseInfo.binaryZip}" class="input-xxlarge" />
    </div>
</div>

<div class="control-group ${hasErrors(bean: releaseInfo, field: 'documentationZip', 'error')}">
    <label class="control-label" for="documentationZip">Documentation Zip URL</label>
    <div class="controls">
        <g:textField name="documentationZip" value="${releaseInfo.documentationZip}" class="input-xxlarge" />
    </div>
</div>

<div class="control-group">
    <label class="control-label" for="latestRelease">Latest Release?</label>
    <div class="controls">
        <label class="checkbox">
            <g:checkBox name="latestRelease" value="${releaseInfo.latestRelease}"/>
            If this is checked, this download will appear as the current version of Grails
        </label>
    </div>
</div>

<div class="control-group">
    <label class="control-label" for="betaRelease">Beta Release?</label>
    <div class="controls">
        <label class="checkbox">
            <g:checkBox name="betaRelease" value="${releaseInfo.betaRelease}"/>
            If this is checked, this download will appear as a 'Beta' in the downloads page
        </label>
    </div>
</div>
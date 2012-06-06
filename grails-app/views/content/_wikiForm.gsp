<flash:message flash="${flash}" bean="${wikiPage}"/>

<g:form class="wiki-form content-form" name="wiki-form" url="[action: 'saveWikiPage', id: wikiPage.title]"
        method="post">
    <g:hiddenField name="title" value="${wikiPage?.title}"/>
    <input type="hidden" name="version" value="${wikiPage?.version}"/>
    <fieldset>

        <div class="control-group ${hasErrors(bean: wikiPage, field: 'body', 'error')}">
            <div class="controls">
                <g:textArea cols="30" rows="20" id="wikiPageBody" name="body" required="required"
                            value="${wikiPage?.body}" class="input-fullsize"/>
            </div>
        </div>

        <div class="form-actions">
            <span class="pull-right">
                <a href="/${wikiPage.title}" class="btn">Cancel</a>
            </span>
            <g:submitButton name="submit" value="Save Changes" class="btn"/>
            <a class="btn preview">Preview</a>
        </div>
    </fieldset>

</g:form>

<r:script>
    $(function () {
        var myCodeMirror = CodeMirror.fromTextArea(document.getElementById('wikiPageBody'), {
            lineNumbers: true,
            wordWrap: true,
            lineWrapping: true,
            gutter: true,
            fixedGutter: true,
            autofocus: true
        });
        $('.preview').click(function() {
            $.ajax({
                type    : "POST",
                cache   : false,
                url     : "/previewWikiPage",
                data    : { body: myCodeMirror.getValue() },
                success : function(data) {
                    $.fancybox(data, {
                        maxWidth    : 710,
                        maxHeight   : 600,
                        fitToView   : false,
                        width       : '70%',
                        height      : '70%',
                        autoSize    : false,
                        closeClick  : false,
                        openEffect  : 'fade',
                        closeEffect : 'fade'
                    });
                }
            });
            return false;
        });
    });
</r:script>

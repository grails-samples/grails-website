<asset:script>
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
</asset:script>
$(document).ready(function() {
    var editorTextArea = $("textarea.wiki");

    var editor = CodeMirror.fromTextArea(editorTextArea[0], {
        lineNumbers: true,
        wordWrap: true,
        lineWrapping: true,
        gutter: true,
        fixedGutter: true,
        autofocus: true
    });

    // store editor so it can be accessed later by image upload
    $(editorTextArea).data(editor, "editor");

    $('.preview').click(function() {
        $.ajax({
            type    : "POST",
            cache   : false,
            url     : "/previewWikiPage",
            data    : { body: editor.getValue() },
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
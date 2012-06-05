<head>
    <title>Edit ${wikiPage?.title}</title>
    <meta content="master" name="layout"/>
    <r:require modules="content, codeMirror, fancyBox"/>
    <style type="text/css">
    .CodeMirror {
        border: 1px solid #eee;
        width: 663px;
    }

    .CodeMirror-scroll {
        height: auto;
        overflow-y: hidden;
        overflow-x: auto;
        width: 100%;
    }
    </style>
    <r:script>
        $(function () {
            var myCodeMirror = CodeMirror.fromTextArea(document.getElementById('wikiPageBody'), {
                lineNumbers: false,
                wordWrap: true
            });
            $('.preview').click(function() {
                $.ajax({
                    type    : "POST",
                    cache   : false,
                    url     : "/preview/${wikiPage?.id}",
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
</head>

<body>

<div id="content" class="content-aside" role="main">

    <g:render template="sideNav"/>

    <div id="main">
        <article>
            <g:render template="wikiForm"/>
        </article>
    </div>
</div>

</body>
</html>

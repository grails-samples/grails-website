<r:script type="text/javascript">
    var dmp = new diff_match_patch();

    function hideCommentPost() {
        YAHOO.util.Dom.addClass('postComment', 'hidden');
    }
    function showCommentPost() {
        YAHOO.util.Dom.removeClass('postComment', 'hidden');
    }

    function showDiff() {


        var text1 = myYUI.get("text1").innerHTML
        var text2 = myYUI.get("text2").innerHTML

        var d = dmp.diff_main(text1, text2);
        dmp.diff_cleanupSemantic(d);
        var ds = dmp.diff_prettyHtml(d);

        myYUI.get('diffOutputDiv').innerHTML = ds;

        myYUI.appear('diffOutputDiv')
    }

    function hidePreview() {
        myYUI.fade('previewContainer')
    }
    function showPreview() {
        myYUI.appear('previewContainer')
    }
</r:script>

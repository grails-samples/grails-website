<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
      <title>Message</title>
      <r:require modules="upload"/>
      <style type="text/css">
        #message {
            font-size: 0.9em;
            text-align: left;
            width: 95%;
        }

      </style>
      <asset:deferredScripts/>
  </head>

  <body>
    <div id="body">
        <div id="message" class="message">
            ${message}
        </div>
        <script type="text/javascript">
        var elementId = "message"

        function hideFrame() {
            var dialog = window.parent.document.getElementById('${pageId}Dialog');
            var iframe = window.parent.document.getElementById('${pageId}Iframe');
            if (dialog != null) {
                myYUI.fade(dialog, 0, 0);
                 <g:if test="${frameSrc}">
                if (iframe != null) iframe.src = "${frameSrc.encodeAsJavaScript()}";
                </g:if>
            }
        }

        function localFade() {
            var anim = new YAHOO.util.Anim(
                elementId,
                { opacity: {from: 1, to: 0 } },
                1
            )
            anim.onComplete.subscribe(function() {
                YAHOO.util.Dom.setStyle(elementId, "display", "none");
                hideFrame();
            });
            anim.animate();
        }
        </script>
    </div>
    <asset:deferredScripts/>
  </body>
</html>

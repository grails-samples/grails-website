<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
      <title>Upload dialog</title>

      <r:require modules="upload"/>
      <asset:deferredScripts/>

      <style type="text/css">
        body {
            background:none;
            text-align: left;
        }

      </style>
  </head>

  <body>
      <div id="uploadDialogDiv" >
          <g:render template="/common/messages" model="${pageScope.getVariables()}" />
          <g:uploadForm name="uploadForm" url="[controller:'content', action:'uploadImage', id:category]" onsubmit="myYUI.appear('progressDiv', 0, 0);myYUI.fade('uploadDialogDiv', 0, 0);">
              <input type="file" name="image" />
              <g:submitButton name="upload" value="upload" />
          </g:uploadForm>
      </div>
      <div id="progressDiv" style="display:none;">
          <img src="${createLinkTo(dir: 'images', file: 'spinner.gif')}" alt="Please wait.." /> Please wait...
      </div>
      <g:render template="/common/messages_effects" model="${pageScope.getVariables()}" />
      <asset:deferredScripts/>
  </body>
</html>

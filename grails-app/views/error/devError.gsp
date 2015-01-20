<!doctype html>
<html>
<head>
  <title>Grails Runtime Exception</title>
  <meta name="layout" content="master">
  <r:require modules="errors"/>
  <asset:deferredScripts />
</head>
<body>
  <g:renderException exception="${exception}" />
  <asset:deferredScripts />
</body>
</html>

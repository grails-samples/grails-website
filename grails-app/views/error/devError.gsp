<!doctype html>
<html>
<head>
  <title>Grails Runtime Exception</title>
  <meta name="layout" content="master">
  <r:require modules="errors"/>
  <r:layoutResources />
</head>
<body>
  <g:renderException exception="${exception}" />
  <r:layoutResources />
</body>
</html>

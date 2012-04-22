<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta content="master" name="layout"/>
    <r:require modules="auth"/>
</head>
<body>

<div class="socialConnect">
  <s
    %{--<a href="#" class="facebook">Facebook connect</a>--}%
    <oauth:connect class="twitter">Twitter connect</oauth:connect>
</div>
<div id="content" class="content-form-small" role="main">
    <g:render template="loginForm" model="${pageScope.variables}"/>
</div>

</body>
</html>

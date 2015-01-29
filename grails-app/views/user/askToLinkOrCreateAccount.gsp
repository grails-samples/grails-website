
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title>Login Page</title>
  <meta content="masterv2" name="layout" />
  <style type="text/css">
/*
#content-container {
    height:700px;
}
*/

#existingAccountForm {
    float: left;
}

#newAccountForm {
    float: right;
}

#footer {
    clear: both;
}

fieldset {
    border: solid black 1px;
    margin: 3em;
    padding: 0 3em 2em 3em;
    width: 20em;
}

fieldset legend {
    margin-bottom: 0;
    padding: 0.5em 0.5em;
    width: auto;
}

fieldset p.first {
    margin-top: 0.5em;
}

div.center {
    font-size: 1.3em;
    padding-top: 10em;
    text-align: center;
}
  </style>
      
</head>
<body>
  <div id="contentPane">
    <g:hasErrors bean="${bean}">
      <g:renderErrors bean="${bean}"/>
    </g:hasErrors>
    <g:form id="existingAccountForm" class="userForm" url="[controller: 'user', action: 'linkAccount']">
      <fieldset>
        <legend>Link to an existing account</legend>
        <p class="first">
          <span class="label"><label for="login">Username:</label></span>
          <g:textField class="textInput" name="login" value="${login}"/>
        </p>
        <p>
          <span class="label"><label for="password">Password:</label></span>
          <g:field type="password" name="password" />
        </p>
        <g:if test="${targetUri}">
        <g:hiddenField name="targetUri" value="${targetUri}"/>
        </g:if>
        <div class="formButtons">
          <g:submitButton name="Submit" value="Login" />
        </div>
      </fieldset>
    </g:form>
    <g:form id="newAccountForm" class="userForm" url="[controller: 'user', action: 'createAccount']">
      <fieldset>
        <legend>Create a new account</legend>
        <p class="first">
          <span class="label"><label for="login">Username:</label></span>
          <g:textField class="textInput" name="login" value="${login}"/>
        </p>
        <p>
          <span class="label"><label for="email">Email:</label></span>
          <g:textField class="textInput" name="email" />
        </p>
        <g:if test="${targetUri}">
        <g:hiddenField name="targetUri" value="${targetUri}"/>
        </g:if>
        <div class="formButtons">
          <g:submitButton name="Create"/>
        </div>
      </fieldset>
    </g:form>
    <div class="center">OR</div>
  </div>
</body>
</html>

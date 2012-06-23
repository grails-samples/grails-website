
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title>Login Page</title>
  <meta content="master" name="layout" />
  <style type="text/css">
#content-container {
    height:700px;
}

  </style>
      
</head>
<body>
  <div id="contentPane">
    <g:hasErrors bean="${bean}">
      <g:renderErrors bean="${bean}"/>
    </g:hasErrors>
    <g:form id="existingAccountForm" class="userForm" url="[controller: 'user', action: 'linkAccount']">
      <h2>Link to an existing account</h2>
      <div class="inputForm">
        <p>
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
      </div>
      <div class="formButtons">
        <g:submitButton name="Submit" value="Login" />
      </div>
    </g:form>
    <g:form id="newAccountForm" class="userForm" url="[controller: 'user', action: 'createAccount']">
      <h2>Create a new account</h2>
      <div class="inputForm">
        <p>
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
      </div>
      <div class="formButtons">
        <g:submitButton name="Create"/>
      </div>
    </g:form>
  </div>
</body>
</html>

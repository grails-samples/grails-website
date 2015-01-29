<html>
<head>
    <meta name="layout" content="masterv2">
</head>

<body>

<div id="content" class="content-form-small" role="main">
    <h2>Forgot Password?</h2>

    <p>Enter the username or email address you registered with and a new password will be emailed to you.</p>

    <g:if test="${flash.message}">
        <div class="alert alert-info">
            ${flash.message}
        </div>
    </g:if>

    <g:form action="passwordReminder" class="userForm">
        <p>
            <input class="text" id="login" name="login" style="width: 21em;" value="${formData?.login}" tabindex="1" type="text" placeholder="Username or Email" />
        </p>


        <p>
            <input class="btn blueLight" tabindex="2" type="submit" value="Send Password" /> <a href="/login" class="btn blueLight">Back to Login</a>
        </p>

    </g:form>
</div>

</body>
</html>

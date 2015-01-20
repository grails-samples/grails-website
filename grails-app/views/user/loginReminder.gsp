<html>
<head>
    <meta name="layout" content="master">
</head>

<body>

<div id="content" class="content-form-small" role="main">
    <h2>Forgot Username?</h2>

    <p>Enter the email address you registered with and a your Username will be emailed to you.</p>

    <g:if test="${flash.message}">
        <div class="alert alert-info">
            ${flash.message}
        </div>
    </g:if>

    <g:form action="loginReminder" class="userForm">
        <p>
            <input class="text" id="email" name="email" style="width: 21em;" value="${formData?.login}" tabindex="1" type="text" placeholder="Email" />
        </p>


        <p>
            <input class="btn blueLight" tabindex="2" type="submit" value="Send Username" /> <a href="/login" class="btn blueLight">Back to Login</a>
        </p>

    </g:form>
</div>

</body>
</html>


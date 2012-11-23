<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta content="master" name="layout"/>
</head>

<body>
<div id="content" class="content-form-small" role="main">
    <h1>${user?.login}'s Profile</h1>

    <flash:message flash="${flash}" bean="${userInfo}"/>

    <div id="profileForm" class="userForm">
        <g:form name="register" url="[controller: 'user', action: 'profile']">
            <div class="inputForm">
                <p><span class="label"><label for="name">Name:</label></span>
                    <g:textField name="name" value="${userInfo?.name}" style="width: 21em;" ></g:textField>
                </p>
            </div>

            <div class="inputForm">
                <p><span class="label"><label for="email">Email address:</label></span>
                    <g:textField name="email" value="${userInfo?.email}" style="width: 21em;" ></g:textField>
                </p>
            </div>

            <div class="inputForm">
                <p><span class="label"><label for="password">Change Password:</label></span>
                    <g:passwordField name="password" style="width: 21em;" ></g:passwordField>
                </p>
            </div>

            <div class="inputForm">
                <p>
                    <span class="label"><label for="email">Receive E-mail Updates for Content Changes?:</label>
                    </span> <g:checkBox name="emailSubscribed" value="${userInfo?.emailSubscribed}"/>
                </p>
            </div>

            <div class="formButtons" style="margin-top:50px;">
                <g:submitButton name="Submit" value="Update"/>
            </div>
        </g:form>
    </div>

</div>
</body>
</html>

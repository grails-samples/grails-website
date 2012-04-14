<g:render template="/common/messages" model="${pageScope.getVariables() + [bean:user]}" />

<g:form name="login" controller='user' action="login">
    <g:hiddenField name="originalURI" value="${originalURI ?: formData?.originalURI}" />

    <h2>Log in</h2>
    <p>
        <label for="login_field">Login or Email</label>
        <input class="text" id="login_field" name="login" tabindex="1" type="text" />
    </p>

    <p>
        <label for="password">Password <a href="/reminder">(forgot password)</a></label>
        <input class="text" id="password" name="password" tabindex="2" type="password" value="" />
    </p>
    <p>
        <input class="btn blueLight" tabindex="3" type="submit" value="Log in" />
        <a href="/register" class="btn blueLight">Create an account</a>
    </p>
</g:form>
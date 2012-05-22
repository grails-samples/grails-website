<g:render template="/common/messages" model="${pageScope.getVariables() + [bean:user]}" />

<g:set var="formBody">
    <g:hiddenField name="originalURI" value="${originalURI}" />

    <h2>Register</h2>
    <p>
        <label for="login">Login (username)</label>
        <g:textField name="login" class="text" value="${params.login}" />
    </p>

    <p>
        <label for="password">Password</label>
        <g:field type="password" name="password" class="text" value="" />
    </p>
    <p>
        <label for="password">Password (confirm)</label>
        <g:field type="password" name="password2" class="text" value="${params.password2}" />
    </p>
    <p>
        <label for="email">Email Address</label>
        <g:textField name="email" value="${params.email}" class="text" />
    </p>
    <p>
        <label for="emailSubscribed">
            <g:checkBox name="emailSubscribed" id="emailSubscribed" value="${false}" style=""/>
            Receive E-mail Updates?
        </label>
    </p>
    <p>
        <input class="btn blueLight" type="submit" value="Create an account" />
        <a href="/login" class="btn blueLight">Log in</a>
    </p>

</g:set>

<g:if test="${true == async}">
    <g:formRemote name="register" url="[controller:'user',action:'register']" update="contentPane">
        ${formBody}
    </g:formRemote>
</g:if>
<g:else>
    <g:form name="register" url="[controller:'user',action:'register']" update="contentPane">
        ${formBody}
    </g:form>
</g:else>

<ul id="loginLinks" class="topLinks nav pills">
    <shiro:isLoggedIn>
        <li><a href="/logout">Logout</a></li>
        <li class="spacing">&nbsp;</li>
        <li class="current-user"><g:link uri="/profile"><b>${user?.login}</b> (${user?.email})</g:link></li>
    </shiro:isLoggedIn>
    <shiro:isNotLoggedIn>
        <li><a href="/login?targetUri=${request.forwardURI}?${request.queryString}" class="login">Login</a></li>
        <li class="spacing">&nbsp;</li>
        <li><a href="/register">Create Account</a></li>
    </shiro:isNotLoggedIn>
</ul> 
<shiro:isLoggedIn>
  <div id="statusbox">Welcome <strong><shiro:principal /></strong><br/><g:link controller="user" action="profile">Profile</g:link> | <g:link controller="user" action="logout">Logout</g:link></div>    
 </shiro:isLoggedIn>
 <shiro:isNotLoggedIn>
  <div id="statusbox"><a href="/login?originalURI=${request.forwardURI.encodeAsURL()}">Sign in</a> to edit and +1 items.</div>
 </shiro:isNotLoggedIn>

<%@page defaultCodec="HTML"%>
<html>
<head>
    <title>Tutorials</title>
</head>
<body>
    <div class="body">
        <p class="no-top">Learn how to use Grails by following these tutorials.</p>
        <h2>Getting Started with Grails (screencasts)</h2>
        <ul>
            <li><g:link controller="screencast" action="show" id="22">Part 1</g:link></li>
            <li><g:link controller="screencast" action="show" id="23">Part 2</g:link></li>
            <li><g:link controller="screencast" action="show" id="28">Part 3</g:link></li>
        </ul>
        <div><g:link action="list" params="[category: 'featured']" class="big-link">More tutorials</g:link></div>
        <p>The old tutorials wiki page is <g:link controller="content" action="index" id="Tutorials">still available</g:link>.</p>
    </div>
</body>
</html>

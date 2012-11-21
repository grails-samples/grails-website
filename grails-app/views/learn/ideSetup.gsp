<head>
    <meta content="master" name="layout"/>
    <title>Setting up an IDE for Grails</title>
    <r:require modules="learn"/>
</head>

<body>

<div id="content" class="content-aside" role="main">

    <g:render template="sideNav" />

    <section id="main" class="wiki">
        <article>
            <h2>IDE Setup</h2>

            <h3>IntelliJ IDEA</h3>
            <p><a href="http://www.jetbrains.com/idea" target="_blank">IntelliJ IDEA</a> and the <a href="http://www.jetbrains.net/confluence/display/GRVY/Groovy+Home" target="_blank">JetGroovy</a> plugin offer good support for <a href="http://www.jetbrains.com/idea/features/groovy_grails.html" target="_blank">Groovy and Grails</a> developers. Refer to the section on Groovy and Grails support on the JetBrains website for a feature overview.</p>
            <p>To integrate Grails with IntelliJ run the following command to generate appropriate project files:</p>
            <pre><code>grails integrate-with --intellij</code></pre>

            <h3>Eclipse</h3>
            <p>We recommend that users of <a href="http://www.eclipse.org/" target="_blank">Eclipse</a> looking to develop Grails application take a look at <a href="http://www.springsource.com/products/sts" target="_blank">SpringSource Tool Suite</a>, which offers built in support for Grails including automatic classpath management, a GSP editor and quick access to Grails commands. See the <a href="http://www.grails.org/STS+Integration">STS Integration</a> page for an overview.</p>

            <h3>NetBeans</h3>
            <p><a href="http://www.netbeans.org" target="_blank">NetBeans</a> provides a Groovy/Grails plugin that automatically recognizes Grails projects and provides the ability to run Grails applications in the IDE, code completion and integration with the Glassfish server. For an overview of features see the <a href="http://www.grails.org/NetBeans+Integration">NetBeans Integration</a> guide on the Grails website which was written by the NetBeans team.</p>

            <h3>TextMate</h3>
            <p>Since Grails' focus is on simplicity it is often possible to utilize more simple editors and <a href="http://macromates.com" target="_blank">TextMate</a> on the Mac has an excellent Groovy/Grails bundle available from the <a href="http://wiki.macromates.com/Main/SubversionCheckout" target="_blank">TextMate bundles SVN</a>.</p>
            <p>To integrate Grails with TextMate run the following command to generate appropriate project files:</p>
            <pre><code>grails integrate-with --textmate</code></pre>
            <p>Alternatively TextMate can easily open any project with its command line integration by issuing the following command from the root of your project:</p>
            <pre><code>mate .</code></pre>

            <h3>Sublime Text 2</h3>
            <p>Coming soon...</p>

        </article>
    </section>

</div>

</body>

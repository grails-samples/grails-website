<head>
    <meta content="master" name="layout"/>
    <title>Installing Grails</title>
    <r:require modules="learn"/>
</head>

<body>

<div id="content" class="content-aside" role="main">

    <g:render template="sideNav" />

    <section id="main" class="wiki">
        <article>
            <h2>Installation from Download</h2>

            <h3>Prerequisites</h3>
            <p>Before you can start using Grails you will need to install a Java SDK (not just a JRE) and set the <code>JAVA_HOME</code> environment variable to the location of that SDK. The minimum required version of the SDK depends on which version of Grails you are using:</p>
            <ul>
                <li>Java SDK 1.4+ for Grails 1.0.x and 1.1.x</li>
                <li>Java SDK 1.5+ for Grails 1.2 or greater</li>
            </ul>

            <h3>Steps</h3>
            <ul>
                <li><a href="/Download">Download</a> the latest Grails <a href="/Releases">release</a></li>
                <li>Extract the archive into an appropriate location; typically <code>C:\grails</code> on Windows or <code>~/grails</code> on Unix</li>
                <li>Create a <code>GRAILS_HOME</code> environment variable that points to the path where you extracted the archive (eg C:\grails on Windows or ~/grails on Unix)</li>
                <li>If you have not set the <code>JAVA_HOME</code> environment variable yet, create <code>JAVA_HOME</code> environment variable that points to the path where you have installed Java</li>
                <li>Append a reference to the "bin" directory within the Grails directory to your PATH variable (eg <code>%GRAILS_HOME%\bin</code> on Windows or <code>$GRAILS_HOME/bin</code> on Unix). Note that, for Windows, both <code>PATH</code> and <code>GRAILS_HOME</code> must be defined at the same environment variable level (eg. 'System variables') rather than across environment variable levels (eg. PATH under 'System variables' and <code>GRAILS_HOME</code> under 'User variables')</li>
                <li>Type "grails" at the command line, if a help message is displayed you are ready to <a href="/Quick+Start">start using Grails</a>!</li>
                <li>If you get an error message, try to chmod +x the grails script inside the bin directory.</li>
            </ul>

            <h2>Installation from Git (The Version Control Repository)</h2>

            <h3>Prerequisites</h3>
            <p>In order to start using Grails from Git you need to have the following:</p>
            <ul>
                <li>An installation of Java 1.5 or higher and have set your JAVA_HOME variable to the install location</li>
                <li>A Git client</li>
            </ul>

            <h3>Steps</h3>
            <ul>
                <li>Check out Grails from the <a href="http://github.com/grails/grails-core/tree/master">Git repository</a>
                    <ul>
                        <li>by running: git clone git://github.com/grails/grails-core.git</li>
                    </ul>
                </li>
                <li>Set the <code>GRAILS_HOME</code> environment variable to <code>CHECKOUT_LOCATION/grails-core</code></li>
                <li>Add the <code>$GRAILS_HOME/bin</code> directory to your <code>PATH</code> environment variable</li>
            </ul>
            <p>What happens next depends on which branch you are working on.</p>

            <h3>For the 1.3.x, 1.4.x and the 'master' branch:</h3>
            <ul>
                <li>Go to the <code>GRAILS_HOME</code> directory and run: <code>./gradlew libs</code></li>
                <li>Then from the <code>GRAILS_HOME</code> directory run: <code>./gradlew install</code></li>
                <li>That's it! You can now <a href="/Quick+Start">start developing in Grails</a> using your custom copy of Grails!</li>
            </ul>

            <h3>For the 1.2.x branch:</h3>
            <ul>
                <li>
                    If you don't have Ant 1.7 (or later) installed, you will need to
                    <ul>
                        <li>set ANT_HOME to <code>$GRAILS_HOME/ant</code></li>
                        <li>add $ANT_HOME/bin to your PATH environment variable</li>
                        <li>you may need to give the scripts in $ANT_HOME/bin execute permissions</li>
                    </ul>
                </li>
                <li>Increase the java memory to 1024M by setting <code>ANT_OPTS=-Xmx1024M</code></li>
                <li>Go to the GRAILS_HOME directory and type <code>ant jar</code> to build Grails</li>
                <li>That's it! You can now start developing in Grails using your custom copy of Grails!</li>
            </ul>

        </article>
    </section>

</div>

</body>

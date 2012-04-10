<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
    <meta content="master" name="layout"/>
</head>

<body>

<div id="content" class="content-aside" role="main">

    <g:render template="sideNav"/>

    <section id="main">
        <article>

            <h2>How to Contribute</h2>

            <p>We're always on the lookout for people who can contribute to Grails. There are four main areas of activity: raising issues, improving the documentation, contributing to the Grails codebase, and developing plugins.</p>

            <p>In addition to the information given below, there are some screencasts on contributing to Grails - check them out!</p>

            <h3>Raising Issues</h3>

            <p>All Grails issues are stored in a JIRA instance. You have to create an account before you can start raising issues, but once that's done you're good to go.</p>

            <p>Here are a few tips that will help us process your issues:</p>

            <ul>
                <li>Do a quick search beforehand to check whether someone else has already raised the issue. You can use the quick search box in the top righthand corner, but if you do, start your query with "grails" so that it only searches the Grails project.</li>
                <li>Try to include as much pertinent information as you can in the summary. "Pages are displayed wrong" is not helpful. "Stylesheet in GSP layout is not being applied" is much better.</li>
                <li>If you can't categorise your issue with one of the available components, don't worry - just leave it empty.</li>
                <li>Provide a reproducible example with instructions on how to reproduce the problem - running grails bug-report within a project is the best way of providing a full sample project</li>
                <li>Even better, provide some test cases in the project if you can. Integration or functional tests are usually the most appropriate. This will make it much easier for us to diagnose and fix the issue.</li>
            </ul>

            <p>Although issues are much more likely to be tackled if they have reproducible examples attached, it's better to raise one without a sample project than not raise one at all.</p>

            <h3>Reviewing Issues</h3>

            <p>There are quite a few old issues in JIRA, some of which may no longer be valid. The core team can't track down these alone, so a very simple contribution that you can make is to verify one or two issues occasionally.</p>

            <p>Which issues need verification? A shared JIRA filter will display all issues that haven't been resolved and haven't been reviewed by someone else in the last 6 months. Just pick one or two of them and check whether they are still relevant.</p>

            <p>Once you've verified an issue, simply edit it and set the "Last Reviewed" field to today. If you think the issue can be closed, then also check the "Flagged" field and add a short comment explaining why. Once those changes are saved, the issue will disappear from the results of the above filter. If you've flagged it, the core team will review and close if it really is no longer relevant.</p>

            <p>One last thing: you can easily set the above filter as a favourite on this JIRA screen so that it appears in the "Issues" drop down. Just click on the star next to a filter to make it a favourite.</p>

            <h3>Documentation</h3>

            <p>Grails documentation currently falls into two categories: the user guide and the wiki (what you're looking at right now). To update the wiki or add new pages, you just need to register and then log in - here's a description of the wiki syntax that we use.</p>

            <p>Providing patches for the user guide is more involved. You first need to get hold of the source from GitHub. How you do that depends on how significant a contribution you plan to make.</p>

            <p>You will probably want to start by getting a read-only clone of the repository from which you can create patches to attach to JIRA issues.</p>

            <p>If you know you're likely to contribute frequently, then create your own fork of the grails-doc repository and send pull requests to the development team.</p>

            <blockquote>Whichever approach you take, it's a good idea to check the Developer Guidelines for information on how to use git.</blockquote>

            <h3>Coding</h3>

            <p>If you want to get involved in the development of Grails then these documents should help you get started:</p>

            <ul>
                <li>Developer Guidelines</li>
                <li>Setting up the development environment</li>
                <li>Inside Grails</li>
            </ul>

            <blockquote>Before we accept a non-trivial patch or pull request we will need you to sign the contributor's agreement. We will let you know if this is necessary for your particular contributions. Signing the contributor's agreement does not grant anyone commit rights to the main repository, but it does mean that we can accept your contributions, and you will get an author credit if we do. Active contributors might be asked to join the core team, and given the ability to merge pull requests.</blockquote>

            <p>The source code for the Grails Core project is hosted on GitHub, as are the other main Grails projects, such as the documentation, sample applications, and functional test suite. If you'd like to fix bugs or implement features in Core, you have two options:</p>

            <ul>
                <li>Provide a patch and attach it to a JIRA issue.</li>
                <li>Fork the repository and send pull requests for your changes.</li>
            </ul>

            <p>The first option is best if you only provide the occasional fix and those fixes tend to be small. Otherwise, fork the repository! Also, make sure that you work on the appropriate branch: at the moment 'master' contains the Grails 1.4.x codebase and '1.3.x' contains the 1.3.x codebase. If changes can be applied to both versions of Grails, then commit the change in one branch and cherry-pick it into the other.</p>

            <p>We are also keen on help with expanding the Grails functional test suite. These are crucial for ensuring that projects work as expected from version to version. Contributing is done in exactly the same way as it is for Grails core, although learn how the test suite works (+) before diving into the code!</p>

            <h3>Plugins</h3>

            <p>If you want to contribute to existing plugins or distribute your own, then follow the steps described at the end of Creating Plugins.</p>

        </article>
    </section>

</div>

</body>
</html>
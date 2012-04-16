<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
    <meta content="master" name="layout"/>
    <r:require modules="learn"/>
</head>

<body>

<div id="content" class="content-aside" role="main">

    <g:render template="sideNav" />

    <section id="main" class="websites">

        <g:each in="${tutorials}" var="tutorial" status="idx">
            <article class="tutorial">
                <a href="#" class="like">+1</a>
                <div>
                    <h3><g:link url="${tutorial.url}">${tutorial.title}</g:link></h3>

                    <p class="tags">
                        <g:each in="${tutorial.tags}" var="tag">
                            <a href="#" class="btn blueLight">${tag}</a>
                        </g:each>
                    </p>

                    <p>
                        ${tutorial.description}
                        <g:link url="${tutorial.url}">read more</g:link>
                    </p>
                </div>
            </article>
        </g:each>
    </section>

</div>

</body>
</html>
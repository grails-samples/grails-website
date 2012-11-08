<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8"/>
    <meta content="master" name="layout"/>
    <r:require modules="community"/>
    <style type="text/css">
        .actionIcon {
            float:left;
            position:relative;
            right:20px;
            bottom:10px;
        }
    </style>
</head>

<body>

<div id="content" class="content-aside" role="main">

    <g:render template="/community/sideNav"/>

    <div id="main" class="websites">
        <section>
            <g:render template="webSiteLarge" model="${webSiteInstance}"/>
        </section>
    </div>
</div>

</body>
</html>
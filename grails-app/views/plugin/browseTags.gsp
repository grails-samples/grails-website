<head>
    <title>Plugins</title>
    <meta content="pluginNav" name="layout"/>

    <rateable:resources />
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'ratings.css')}"/>

</head>
<body>
    <h2>Plugin tags</h2>
    <tags:tagCloud tags="${tags}" action="browseByTag" idProperty="tagName"/>
</body>
</html>



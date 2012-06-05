<html>
<head>
    <gui:resources components='expandablePanel'/>
    <title>Grails.org Error</title>
    <meta content="master" name="layout" />
    <g:external dir="css" file="errors.css"/>
    <style type="text/css">
	body {
	    font-family: Lucida Grande, Lucida, sans-serif;
	    font-size: 12pt;
	}

        .message {
                border: 1px solid black;
                padding: 5px;
                background-color:#E9E9E9;
        }
        .stack {
                border: 1px solid black;
                padding: 5px;	  		
                overflow:auto;
                height: 300px;
        }
        .snippet {
                padding: 5px;
                background-color:white;
                border:1px solid black;
                margin:3px;
                font-family:courier;
        }
    </style>
  </head>
  
  <body>
    <h1>An Error has occurred</h1>
    <p>We're sorry, but there has been a problem rendering the page you've requested. This incident has been logged, and will be looked into soon.</p>

  <div class='yui-skin-sam'>
      <gui:expandablePanel title="Error Details" bounce="false">${exception.message}</gui:expandablePanel>
  </div>
  </body>
</html>

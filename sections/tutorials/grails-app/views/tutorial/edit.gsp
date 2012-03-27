<head>
    <title><g:message code="tutorial.edit.title" default="Edit Tutorial"></g:message></title>
</head>
<body>
    <div class="artifactControls">
        <span class="menuButton"><g:link class="list" action="list">Tutorial List</g:link></span>
    </div>
    <div class="body">
        <g:renderErrors bean="${tutorial}"></g:renderErrors>
        <div id="artifactForm" class="artifactForm">
        
            <g:uploadForm name="uploadArtifactForm" action="update" id="${tutorial.id}">
                <label for="tutorial.title">Title:</label> 
                <g:textField name="tutorial.title" value="${tutorial.title}" /> 
                <label for="tutorial.description">Description:</label> 
                <g:textArea name="tutorial.description">${tutorial.description}</g:textArea> 
                <label for="tutorial.url">URL:</label> 
                <g:textField name="tutorial.url" value="${tutorial.url}" /> 

                <shiro:hasPermission permission="tutorial:feature">
                    <label for="featured">Featured:</label>
                    <g:checkBox name="featured" value="${tutorial.featured}" />
                </shiro:hasPermission>
                
                <label for="tags">Tags:</label> 
                <g:textField name="tags" value="${tutorial.tags.join(',')}" /> 
        
                <g:submitButton name="Update"></g:submitButton>				
            </g:uploadForm>
        </div>
    </div>
</body>

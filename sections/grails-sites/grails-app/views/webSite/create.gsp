<head>
    <title><g:message code="webSite.create.title" default="Create Web Site"></g:message></title>
</head>
<body>
    <div class="artifactControls">
        <span class="menuButton"><g:link class="list" action="list">Web Site List</g:link></span>
    </div>
    <div class="body">
        <g:renderErrors bean="${webSite}"></g:renderErrors>
        <div id="artifactForm" class="artifactForm">

            <g:uploadForm name="uploadArtifactForm" url="[action:'save']">
                <label for="title">Title:</label> 
                <g:textField name="title" value="${webSite.title}" /> 
                <label for="description">Description:</label> 
                <g:textArea name="description">${webSite.description}</g:textArea> 
                <label for="url">URL:</label> 
                <g:textField name="url" value="${webSite.url}" /> 

                <shiro:hasPermission permission="webSite:feature">
                    <label for="featured">Featured:</label>
                    <g:checkBox name="featured" value="${webSite.featured}" />
                </shiro:hasPermission>

                <label for="tags">Tags:</label> 
                <g:textField name="tags" value="${params.tags}" /> 
                <div class="hint">Examples: startup, nosql, high traffic</div>

                <div id="previewUpload">
                <label for="preview">Preview image:</label>
                <input type="file" name="preview" />
                </div>

                <g:submitButton name="Submit"></g:submitButton>
            </g:uploadForm>				
        </div>
    </div>
</body>

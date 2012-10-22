<div class="image">

    <a href="#">
        <img name="${wikiImage.name}" src="${createLink(controller: "content", action: "showImage", params: [path: wikiImage.name, size: "small"])}"  />
    </a>

    <p><g:message code="wikiImage.add.description" args="${[wikiImage.name]}" /></p>
</div>
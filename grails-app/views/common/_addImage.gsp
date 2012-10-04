<div class="image">

    <a href="#">
        <img name="${wikiImage.name}" src="${createLink(controller: "content", action: "showImage", params: [path: wikiImage.name, size: "small"])}"  />
    </a>

    <p>Click on the image to add it to your content, or use the syntax <strong>!${wikiImage.name}!</strong></p>
</div>
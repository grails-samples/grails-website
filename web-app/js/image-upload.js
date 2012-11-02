$(document).ready(function() {
    var container = $("#images-container");
    var errorBox = $(container).find(".error");
    var progressBox = $(container).find(".progress");
    var imagePrefix = $(container).find("input[name='image.prefix']").val();
    var spinner = $(container).find(".spinner");

    var params = {prefix: imagePrefix}

    // image upload
    var uploader = new qq.FileUploaderBasic({
        button: $('.upload-image')[0],
        encoding: 'multipart',
        inputName: 'image',
        params: params,
        action: '/upload',
        multiple: false,
        onSubmit: function(id, fileName) {
            $(errorBox).hide();
            $(spinner).show();
        },
        onComplete: function(id, fileName, responseJSON) {
            if (responseJSON.success) {
                renderAddImage(responseJSON.id);
            }
        },
        showMessage: function(message){
            $(spinner).hide();
            $(errorBox).show().html(message);
        },
        onError: function(id, fileName, errorReason) {
            $(this).showMessage(errorReason);
        },
        onProgress: function(id, fileName, uploadedBytes, totalBytes) {
            var percentComplete = Math.round((uploadedBytes / totalBytes) * 100);
            $(progressBox).show().html("Uploading " + fileName + "... " + percentComplete + "% complete");

            if (percentComplete == 100) {
                setTimeout(function() {$(progressBox).fadeOut('slow');}, 1000);
            }

        }
    });

    // Change photo-name when hovering over image
    $("#images-container #images").on('hover', ".image", function() {
        var imageName = $(this).find("img").attr("name");
        updateImageName(imageName);
    });


    // Add image to wiki content
    $("#images-container #images").on('click', ".image", function(event) {
        event.preventDefault();
        var imageName = $(this).find("img").attr("name");
        var wikiImage = "!" + imageName + "!";

        var editor = $("textarea#body").data(editor);
        editor.replaceSelection(wikiImage, "end");
    });

});

function updateImageName(imageName) {
    var wikiImage = "!" + imageName + "!";
    $("#images-container .image-name").html(wikiImage);
}

function renderAddImage(id) {
    var data = {id: id};

    var container = $("#images");
    var spinner = $("#images-container .spinner");
    var descriptionBox = $("#images-container .description");

    $(spinner).show();

    $.ajax({
        url: "/addImage",
        data: data,
        success: function(response) {
            $(spinner).hide();
            $(container).append(response);
            var imageName = $(container).find("img").attr("name");
            updateImageName(imageName);
            descriptionBox.show();
        }
    });
}
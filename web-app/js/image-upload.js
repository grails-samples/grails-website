$(document).ready(function() {
    var errorBox = $("#images-container .error");
    var progressBox = $("#images-container .progress");
    var imagePrefix = $("#images-container input[name='image.prefix']").val();
    var spinner =  $("#images-container .spinner");

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

    // Add image to wiki content
    $("#images-container #images").on('click', "a", function(event) {
        event.preventDefault();
        var imageName = $(this).find("img").attr("name");
        var wikiImage = "!" + imageName + "!";

        var editor = $("textarea#body").data(editor);
        editor.replaceSelection(wikiImage, "end");
    });

});

function renderAddImage(id) {
    var data = {id: id};

    var container = $("#images");
    var spinner = $("#images-container .spinner");

    $(spinner).show();

    $.ajax({
        url: "/addImage",
        data: data,
        success: function(response) {
            $(spinner).hide();
            $(container).append(response);
        }
    });
}
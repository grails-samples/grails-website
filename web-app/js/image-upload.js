$(document).ready(function() {
    // image upload
    var uploader = new qq.FileUploaderBasic({
        button: $('#upload-image')[0],
        encoding: 'multipart',
        inputName: 'image',
        action: '/upload',
        onComplete: function(id, fileName, responseJSON) {
            if (responseJSON.success) {
                renderAddImage(responseJSON.id);
            }
        },
        onProgress: function(id, fileName, uploadedBytes, totalBytes) {
            var percentComplete = Math.round((uploadedBytes / totalBytes) * 100);
        }
    });

    // Add image to content
    $("#images").on('click', "a", function(event) {
        event.preventDefault();
        var imageName = $(this).find("img").attr("name");
        var wikiImage = "!" + imageName + "!";

        var editor = $("textarea#body").data(editor);
        editor.replaceSelection(wikiImage, "end");
    });
});


function renderAddImage(id) {
    var data = {id: id};

    $.ajax({
        url: "/addImage",
        data: data,
        success: function(response) {
            $("#images").append(response);

        }
    });

}
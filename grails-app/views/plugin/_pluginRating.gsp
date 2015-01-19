
<div id="ratingCount${plugin.id}" class="ratingCount">${plugin.ratingCount} vote${plugin.ratingCount > 1 ? 's':''}</div>
<div id="pluginRating${plugin.id}" class="rating"></div>

<asset:script>

    $('#pluginRating${plugin.id}').raty({path:'/img', readOnly:${plugin.userRating(request.user) ? true : false}, score:${plugin.avgRating ?: 0}, half:true, halfShow:true, click: function(score, evt) {

        <g:if test="${request.user}">
           $.ajax( { url:"${createLink(controller:"rateable", action:'rate', id:plugin.id)}", type:"POST", 
                     data: {rating: score, type:"plugin"}
            }) 

            var count = $('#ratingCount${plugin.id}').text()
            $('#ratingCount${plugin.id}').text(parseInt(count)+1)
            $('#pluginRating${plugin.id}').raty('readOnly', true)
        </g:if>
        <g:else>
            window.location = "${createLink(controller:"user", action:"login", params:[targetUri:request.forwardURI])}";
        </g:else>
    }})
</asset:script>
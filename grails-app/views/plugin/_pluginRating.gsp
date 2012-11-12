<div id="pluginRating${plugin.id}" class="rating"></div>
<div class="ratingCount">${plugin.ratingCount}</div>
<r:script>
    $('#pluginRating${plugin.id}').raty({path:'/img',readOnly:${request.user == null}, score:${plugin.avgRating ?: 0}, click: function(score, evt) {
       $.ajax( { url:"${createLink(controller:"rateable", action:'rate', id:plugin.id)}", type:"POST", 
                 data: {rating: score, type:"plugin"}
        }) 
    }})
</r:script>
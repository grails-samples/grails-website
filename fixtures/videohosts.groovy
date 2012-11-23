import org.grails.learn.screencasts.*

fixture {
	build {
		youtube(VideoHost, name:"YouTube", embedTemplate:'''\
<object width="${width}" height="${height}">
  <param name="movie" value="http://www.youtube.com/v/${videoId}?fs=1&amp;hl=en_US&amp;hd=1"></param>
  <param name="allowFullScreen" value="true"></param>
  <param name="allowscriptaccess" value="always"></param>
  <embed src="http://www.youtube.com/v/${videoId}?fs=1&amp;hl=en_US&amp;hd=1" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="640" height="385"></embed>
</object>
			''')
		vimeo(VideoHost, name:"Vimeo", embedTemplate:'''\
<iframe src="http://player.vimeo.com/video/${videoId}" width="${width}" height="${height}" frameborder="0"></iframe>
			''')
	}
}
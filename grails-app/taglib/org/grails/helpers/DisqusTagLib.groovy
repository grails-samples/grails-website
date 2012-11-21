package org.grails.helpers

class DisqusTagLib {
  static namespace = "disqus"

  def grailsApplication
  def grailsLinkGenerator

  def comments = { attrs ->
    def settings = grailsApplication.config.grails.plugins.disqus
    def shortname = attrs['shortname'] ? attrs['shortname'].toString() : settings.shortname

    if(!shortname) {
      out << "Disqus can't be used because shortname is not configured."
      return
    }

    def identifier
    if(attrs.identifier) {
      identifier = attrs.identifier
    } else if(settings.identifier instanceof Closure) {
      identifier = settings.identifier(attrs.bean)
    } else if(attrs.bean) {
      def bean = attrs.bean
      def name = bean.class.name
      def id = bean.metaClass.properties.find { it.name == "id" } ? bean.id : bean.hashCode()
      identifier = "${name}#${id}"
    }
    def url
    if(attrs.url) {
      url = attrs.url
    } else if(settings.url instanceof Closure) {
      url = settings.url()
    } else {
      url = grailsLinkGenerator.link(uri: request.forwardURI, absolute: true)
    }

    def content = '''<div id="disqus_thread"></div>
    <script type="text/javascript">'''
    content += "var disqus_shortname = '${shortname}';"
    if(identifier) content += "var disqus_identifier = '${identifier}';"
    if(url) content += "var disqus_url = '${url}';"

    content += '''(function() {
        var dsq = document.createElement('script'); dsq.type = 'text/javascript'; dsq.async = true;
        dsq.src = 'http://' + disqus_shortname + '.disqus.com/embed.js';
        (document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(dsq);
    })();'''
    content += '</script>'

    // Message to show if javascript is disabled
    def noscript = settings.noscript
    if(noscript) content += "<noscript>${noscript}</noscript>"

    // Message about disqus usage
    def powered = settings.powered
    if(powered) content += '<a href="http://disqus.com" class="dsq-brlink">blog comments powered by <span class="logo-disqus">Disqus</span></a>'

    out << content
  }


}

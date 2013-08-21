import grails.util.Environment

class UrlMappings {
    static mappings = {

        if (Environment.current == Environment.DEVELOPMENT ||
            Environment.current == Environment.TEST) {
            '/greenmail'(controller: 'greenmail', action: 'list')
        }

        "/Home?"(controller: "content", action: "homePage")
        "/$id"(controller: "content", action: "index") {
            constraints {
                id notEqual: "dbconsole"
            }
        }

        "/ggts"(controller: "product", action: "legacyggts")
        "/products/$action"(controller: "product")
        "/products/ggts/welcome"(controller: "product", action: "ggtsWelcome")

        "/start"(controller: "learn", action: "gettingStarted")

        def populateVersion = {
            pluginVersion = {
                def version = params.version
                if (version) {
                    // In the case of the RELEASE_* tag, we can just use
                    // the version number it provides.
                    return params.version?.replace('_','.')
                }
                else {
                    // For LATEST_RELEASE tags, we have to work out the
                    // version from the filename.
                    def fn = params.fullName
                    def pluginName = params.plugin
                    if (!fn.startsWith(pluginName) && fn.startsWith("grails-")) {
                        // This is the plugin package (zip) or its associated
                        // checksums, because the packages have a 'grails-'
                        // prefix in their names.
                        fn = fn[7..-1]
                        params.fullName = fn
                    }

                    // The full name may be no more than the plugin name, i.e.
                    // without the version. Only an invalid URL would be like this
                    // so just return null and let the URL matching return a 404.
                    // Otherwise extract the version number from the filename. Note
                    // that this may include a '-plugin' suffix in the case of the
                    // XML plugin descriptor, but that's fine as the action handles
                    // that.
                    return isPluginNameWithVersion(fn, pluginName) ? fn.substring(getVersionPrefix(pluginName).size()) : null
                }
            }
        }

        // These mimic the old Subversion-based Central Plugin Repository URLs, for
        // clients that still use those.
        "/plugins/.plugin-meta/plugins-list.xml"(controller:"repository", action:"list")
        "/plugins/.plugin-meta"(controller:"repository", action:"pluginMeta")
        "/plugins/grails-$plugin/tags/RELEASE_$version/$fullName.${type}"(controller:"repository", action:"artifact", populateVersion)
        "/plugins/grails-$plugin/tags/LATEST_RELEASE/$fullName.${type}"(controller:"repository", action:"artifact", populateVersion)
        "/plugins/grails-$plugin/tags/LATEST_RELEASE"(controller:"repository", action:"listLatest")

        "/api/v1.0/downloads"(controller: "download", action: "apiList")
        "/api/v1.0/download/$version"(controller: "download", action: "apiShow")

        "/api/v1.0/publish"(controller:"repository", action:"publish")
        "/api/v1.0/publish/$plugin/$version"(controller:"repository", action:"publish")
        "/api/v1.0/plugins/$category?"(controller: "plugin", action: "apiList")
        "/api/v1.0/plugin/$name/$version"(controller: "plugin") {
            action = [GET: "apiShow", PUT: "apiUpdate"]
            parseRequest = true
        }
        "/api/v1.0/plugin/$name"(controller: "plugin") {
            action = [GET: "apiShow", PUT: "apiUpdate"]
            parseRequest = true
        }
        "/api/v1.0/plugin/status/$name/$version"(controller: "pendingRelease", action: "status")

        "/download"(controller: "download", action: "index")
        "/download/ubuntu"(controller: "download", action: "ubuntu")
        "/downloads"(controller: "download", action: "index")

        // Legacy download links.
        "/Download"(controller: "download", action: "legacyHome")
        "/download/file"(controller: "download", action: "downloadFile")
        "/download/url"(controller: "download", action: "showUrl")
        "/download/archive/$id"(controller: "download", action: "archive")

        "/wiki/latest"(controller: "content", action: "latest")
        "/auth/$action"(controller: "auth")


        /* ========================= PLUGINS ======================= */
        "/plugins"(controller: "plugin", action: "list")
        "/plugins/feed"(controller: "plugin", action: "feed")
        "/plugins/submitPlugin"(controller: "plugin", action: "submitPlugin")
        "/plugins/tag/$tag"(controller: "plugin", action: "list")
        "/plugin/edit/$id"(controller: "plugin", action:"editPlugin")
        "/plugin/update/$id"(controller:"plugin", action:"updatePlugin")
        "/plugin/$id"(controller: "plugin") {
            // The PUT is legacy, but can't add it as a separate mapping.
            action = [ GET: "show", PUT: "apiUpdate" ]
            parseRequest = true

            constraints {
                id notEqual: "tag"
            }
        }
        "/plugins/$id"(controller: "plugin") {
            // The PUT is legacy, but can't add it as a separate mapping.
            action = [ GET: "show", PUT: "apiUpdate" ]
            parseRequest = true

            constraints {
                id notEqual: "tag"
            }
        }        
//        "/plugins/tag/"(controller: "plugin", action: "list") // Fix for possible bad path
//        "/plugins/filter/"(controller: "plugin", action: "list") // Fix for possible bad path
        "/plugins/pending"(controller: "plugin", action: "pendingPlugins")
        "/plugins/pending/$id"(controller: "plugin", action: "showPendingPlugin")
        "/plugins/search"(controller: "plugin", action: "search")

        // Legacy plugin links.
        "/Plugins"(controller: "plugin", action: "legacyHome")
//        "/plugins"(controller: "plugin", action: "home")
//        "/plugins/forum"(controller: "plugin", action: "forum")
//        "/plugin/home"(controller: "plugin", action:"home")
//        "/plugin/search"(controller: "plugin", action:"search")
//        "/plugin/list"(controller: "plugin", action:"list")
//        "/plugin/create"(controller: "plugin", action:"createPlugin")
//        "/plugin/delete/$name"(controller: "plugin", action:"deletePlugin")

//        "/plugin/saveTab/$id"(controller: "plugin", action: "saveTab")
//        "/plugin/addTag/$id"(controller: "plugin", action:"addTag")
//        "/plugin/removeTag/$id"(controller: "plugin", action:"removeTag")
//        "/plugin/showTag"(controller: 'plugin', action:'showTag')
//        "/plugin/postComment/$id"(controller: "plugin", action:"postComment")
//        "/plugin/latest"(controller: "plugin", action: "latest")
//        "/plugin/category/all"(controller: "plugin", action: "browseByName")
//        "/plugin/category/$category"(controller: "plugin", action: "home")
//        "/plugin/showComment/$id"(controller: "plugin", action: "showComment")
//        "/plugins/category/$category"(controller: "plugin", action: "home")
//        "/plugins/tag/$tagName"(controller: "plugin", action: "browseByTag")
//        "/plugins/tags"(controller: "plugin", action: "browseTags")

        "/plugin/addTag"(controller:"plugin", action:"addTag") 
        "/plugin/removeTag"(controller:"plugin", action:"removeTag") 
        "/wikiImage/$path**"(controller: "content", action: "showImage")
        "/content/postComment/$id"(controller: "content", action:"postComment")

        "/blog/delete/$id"(controller: 'blogEntry', action:'delete')
        "/blog"(controller:"blog", action:"list")
        "/Grails+Screencasts"(controller: "content", action: "screencastLegacy")

        "/rateable/rate/$id"(controller: "rateable", action:"rate")
        "/tag/autoCompleteNames"(controller:'tag', action:'autoCompleteNames')

        "/search"(controller: "content", action: "search")
        "/upload/$id?"(controller: "content", action: "uploadImage")
        "/addImage/$id?"(controller: "content", action: "addImage")

        "/register"(controller: "user", action: "register")
        "/user/create"(controller: "user", action: "createAccount")
        "/oauth/success"(controller: "shiroOAuth", action: "onSuccess")
        "/oauth/linkaccount"(controller: "user", action: "askToLinkOrCreateAccount")
        "/oauth/save/linkaccount"(controller: "user", action: "linkAccount")
        "/oauth/save/createaccount"(controller: "user", action: "createAccount")
        "/oauth/$action?"(controller: "oauth")
        "/login"(controller: "user", action: "login")
        "/unauthorized"(controller: "user", action: "unauthorized")
        "/reminder"(controller: "user", action: "passwordReminder")
        "/loginReminder"(controller: "user", action: "loginReminder")
        "/profile"(controller: "user", action: "profile")
        "/logout"(controller: "user", action: "logout")
        "/edit/$id"(controller: "content", action: "editWikiPage")
        "/save/$id"(controller: "content", action: "saveWikiPage")
        "/previewWikiPage"(controller: "content", action: "previewWikiPage")
        "/create/$id"(controller: "content", action: "createWikiPage")
        "/info/$id"(controller: "content", action: "infoWikiPage")
        "/markup/$id"(controller: "content", action: "markupWikiPage")
        "/version/$id/$number"(controller: "content", action: "showWikiVersion") {
            constraints { number matches: /\d+/ }
        }
        "/rollback/$id/$number"(controller: "content", action: "rollbackWikiVersion")
        "/diff/$id/$number/$diff"(controller: "content", action: "diffWikiVersion")
        "/previous/$id/$number"(controller: "content", action: "previousWikiVersion")


        /* ========================= LEARN ======================= */
        "/learn"(controller:"learn", action:"gettingStarted")
        "/learn/IDE_setup"(controller:"learn", action:"ideSetup")
        "/learn/installation"(controller:"learn", action:"installation")
        "/learn/quickStart"(controller:"learn", action:"quickStart")

        /* ========================= COMMUNITY ======================= */
        "/community"(controller: "community", action: "index")
        "/community/contribute"(controller: "community", action: "contribute")
        "/community/mailingList"(controller: "community", action: "mailingList")
        "/community/twitter"(controller: "community", action: "twitter")
        "/news"(controller:"newsItem", action:"index")
        "/news/add"(controller:"newsItem", action:"create")
        "/news/$id"(controller:"newsItem", action:"show")
        "/news/edit/$id"(controller:"newsItem", action:"edit")
        "/blog/view/$author/$title"(controller: "newsItem", action: "legacyShow")

        "/screencasts"(controller: "screencast", action: "list")
        "/screencasts/add"(controller: "screencast", action: "create")
        "/screencasts/save"(controller: "screencast", action: "save")
        "/screencast/search"(controller: "screencast", action: "search")
        "/screencast/tag/$tag"(controller:"screencast", action:"tagged")
        "/screencast/$id"(controller: "screencast", action: "show")
        "/screencast/edit/$id"(controller: "screencast", action: "edit")

        "/Testimonials"(controller: "testimonial", action: "legacyHome")
        "/testimonials"(controller: "testimonial", action: "list")
        "/testimonials/add"(controller: "testimonial", action: "create")
        "/testimonials/save"(controller: "testimonial", action: "save")
        "/testimonials/edit"(controller: "testimonial", action: "edit")
        "/testimonials/update"(controller: "testimonial", action: "update")
        "/testimonials/$id"(controller: "testimonial", action: "show")

//        "/screencasts"(controller:"screencast", action:"list")
//        "/screencasts/tags"(controller:"screencast", action:"browseTags")
//        "/screencasts/tags/$tag"(controller:"screencast", action:"search")
//        "/screencast/save"(controller:"screencast", action:"save")
//        "/screencast/search"(controller:"screencast", action:"search")
//        "/screencast/update"(controller:"screencast", action:"update")
//        "/screencast/edit/$id"(controller:"screencast", action:"edit") {
//            constraints { id matches: /\d+/ }
//        }
        "/screencast/feed"(controller:"screencast", action:"feed")
        "/screencasts/feed"(controller:"screencast", action:"feed")
//        "/screencast/add"(controller:"screencast", action:"create")
//        "/screencast/show/$id"(controller:"screencast", action:"show") {
//            constraints { id matches: /\d+/ }
//        }
        "/comment/add"(controller:"commentable", action:"add")

        "/websites"(controller: "webSite", action: "list")
        "/websites/$category"(controller: "webSite", action: "list")
        "/websites/add"(controller: "webSite", action: "create")
        "/websites/save"(controller: "webSite", action: "save")
        "/websites/search"(controller:"webSite", action:"search")
        "/websites/tags"(controller:"webSite", action:"browseTags")
        "/websites/feed"(controller:"webSite", action:"feed")
        "/website/search"(controller:"webSite", action:"search")
        "/website/$id"(controller: "webSite", action: "show")
        "/website/edit/$id"(controller: "webSite", action: "edit")
        "/website/update/$id"(controller: "webSite", action: "update")

        "/dynamicImage/${imageId}-${size}.${type}"(controller: "dbContainerImage", action: "index")

        "/Tutorials"(controller: "tutorial", action: "legacyHome")
        "/tutorials"(controller: "tutorial", action: "list")
        "/tutorials/add"(controller: "tutorial", action: "create")
        "/tutorials/save"(controller: "tutorial", action: "save")
        "/tutorial/search"(controller:"tutorial", action:"search")
        "/tutorial/$id"(controller: "tutorial", action: "show")
        "/tutorials/tag/$tag"(controller:"tutorial", action:"tagged")


        "/tutorial/edit/$id"(controller: "tutorial", action: "edit")
//        "/tutorials/$category"(controller: "tutorial", action: "list")
//        "/tutorials/search"(controller:"tutorial", action:"search")
//        "/tutorials/tags"(controller:"tutorial", action:"browseTags")
//        "/tutorials/feed"(controller:"tutorial", action:"feed")
//        "/tutorial/edit/$id"(controller: "tutorial", action: "edit")
//        "/tutorial/update/$id"(controller: "tutorial", action: "update")

        "/social/like"(controller: "likeDislike", action: "like")
        "/social/unlike"(controller: "likeDislike", action: "unlike")

        "/admin/$controller/$action?/$id?"()
        "/admin"(controller: "admin", action: "index")

        "/health"(controller: "health", action: "index")

        if (Environment.current == Environment.TEST) {
            "/test/fixtures/$action"(controller: "fixtures")

            // URL for plugin descriptors
            "/test/plugins/$name/$file"(controller: "fixtures", action: "pluginData") {
                constraints {
                    file(matches: /.+-plugin.xml/)
                }
            }

            // URL for plugin POMs
            "/test/plugins/$name/$file"(controller: "fixtures", action: "pomData") {
                constraints {
                    file(matches: /.+\.pom/)
                }
            }
        }

        "404"(controller: "error", action: "notFound")
        if (Environment.current == Environment.PRODUCTION) {
            "500"(controller: "error", action: "serverError")
        }
        else {
            "500"(controller: "error", action: "devError")
        }
    }

    private static boolean isPluginNameWithVersion(String fullName, String pluginName) {
        if (fullName == pluginName) return false
        else {
            def bitBeforeVersion = getVersionPrefix(pluginName)
            if (fullName.startsWith(bitBeforeVersion) && fullName.size() > bitBeforeVersion.size()) return true
            else return false
        }
    }

    private static String getVersionPrefix(String pluginName) { return pluginName + '-' }
}

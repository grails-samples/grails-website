import grails.util.Environment

class UrlMappings {
    static excludes = ["/images/**", "/css/**", "/js/**", "/img/**", "/font/**", "/fonts/**", "/*.html"]

    static mappings = {

        if (Environment.current == Environment.DEVELOPMENT ||
            Environment.current == Environment.TEST) {
            '/greenmail'(controller: 'greenmail', action: 'list')
        }

        "/wiki/Home"(redirect:[url:"https://grails.org", permanent:true])
        "/wiki/Roadmap"(redirect: [url: 'https://github.com/grails/grails-core/wiki/Roadmap', permanent: true])
        "/wiki/$id"(redirect:[url:"https://grails.org", permanent:true])
        "/wiki"(redirect:[url:"https://grails.org", permanent:true])
        "/wiki/version/Grails%20Development%20Firms/50"(redirect:[url:"https://grails.org/support.html", permanent:true])

        //"/ggts"(controller: "product", action: "legacyggts")
        //"/products/$action"(controller: "product")
        //"/products/ggts/welcome"(controller: "product", action: "ggtsWelcome")

        "/start"(redirect:[url:"http://grails.github.io/grails-doc/latest/guide/gettingStarted.html", permanent:true])

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
        "/plugins/grails-$plugin/tags/RELEASE_$version/$fullName(.${type})"(controller:"repository", action:"artifact", populateVersion)
        "/plugins/grails-$plugin/tags/LATEST_RELEASE/$fullName(.${type})"(controller:"repository", action:"artifact", populateVersion)
        "/plugins/grails-$plugin/tags/LATEST_RELEASE"(controller:"repository", action:"listLatest")

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

        "/download"(redirect: '/download.html')
        "/download/ubuntu"(redirect: '/download.html')
        "/downloads"(redirect: '/download.html')

        // Legacy download links.
        "/Download"(redirect: '/download.html')
        "/download/file"(redirect: '/download.html')
        "/download/url"(redirect: '/download.html')
        "/download/archive/$id"(redirect: '/download.html')

        "/wiki/latest"(controller: "content", action: "latest")
        "/auth/$action"(controller: "auth")


        /* ========================= PLUGINS ======================= */
        "/Plugins"(redirect: '/plugins')
        "/plugins/tag"(redirect: '/plugins')
        name pluginList: "/plugins"(controller: "plugin", action: "list")
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

        "/plugins/pending"(controller: "plugin", action: "pendingPlugins")
        "/plugins/pending/$id"(controller: "plugin", action: "showPendingPlugin")
        "/plugins/search"(controller: "plugin", action: "search")

        // Legacy plugin links.
        "/Plugins"(controller: "plugin", action: "legacyHome")

        "/plugin/addTag"(controller:"plugin", action:"addTag")
        "/plugin/removeTag"(controller:"plugin", action:"removeTag")
        "/wiki/wikiImage/$path**"(controller: "content", action: "showImage")
        "/wiki/content/postComment/$id"(controller: "content", action:"postComment")

        "/blog"(redirect: [url: 'http://grailsblog.objectcomputing.com', permanent: true])
        "/Grails+Screencasts"(redirect:[url: 'http://guides.grails.org', permanent:true])
        "/wiki/Grails+Screencasts"(redirect:[url: 'http://guides.grails.org', permanent:true])

        "/rateable/rate/$id"(controller: "rateable", action:"rate")
        "/tag/autoCompleteNames"(controller:'tag', action:'autoCompleteNames')

        "/wiki/search"(controller: "content", action: "search")
        "/wiki/upload/$id?"(controller: "content", action: "uploadImage")
        "/wiki/addImage/$id?"(controller: "content", action: "addImage")

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
        "/wiki/edit/$id"(controller: "content", action: "editWikiPage")
        "/wiki/save/$id"(controller: "content", action: "saveWikiPage")
        "/wiki/preview"(controller: "content", action: "previewWikiPage")
        "/wiki//create/$id"(controller: "content", action: "createWikiPage")
        "/wiki/info/$id"(controller: "content", action: "infoWikiPage")
        "/wiki/markup/$id"(controller: "content", action: "markupWikiPage")
        "/wiki/version/$id/$number"(controller: "content", action: "showWikiVersion") {
            constraints { number matches: /\d+/ }
        }
        "/wiki/rollback/$id/$number"(controller: "content", action: "rollbackWikiVersion")
        "/wiki/diff/$id/$number/$diff"(controller: "content", action: "diffWikiVersion")
        "/wiki/previous/$id/$number"(controller: "content", action: "previousWikiVersion")


        /* ========================= LEARN ======================= */
        "/learn"(redirect:[url:"http://grails.github.io/grails-doc/latest/guide/gettingStarted.html", permanent:true])
        "/learn/IDE_setup"(redirect:[url:"http://grails.github.io/grails-doc/latest/guide/gettingStarted.html#ide", permanent:true])
        "/learn/installation"(redirect:[url:"http://grails.github.io/grails-doc/latest/guide/gettingStarted.html#downloadingAndInstalling", permanent:true])
        "/learn/quickStart"(redirect:[url:"http://grails.github.io/grails-doc/latest/guide/gettingStarted.html#creatingAnApplication", permanent:true])

        /* ========================= COMMUNITY ======================= */
        "/community"(redirect:[url:"/community.html", permanent:true])
        "/community/contribute"(redirect:[url:"/contribute.html", permanent:true])
        "/community/mailingList"(redirect:[url:"/mailing-lists.html", permanent:true])
        "/community/twitter"(redirect:[url:"/community.html", permanent:true])
        "/news"(redirect: [url: 'http://grailsblog.objectcomputing.com', permanent: true])
        "/news/$id"(redirect: [url: 'http://grailsblog.objectcomputing.com', permanent: true])
        "/blog/view/$author/$title"(redirect: [url: 'http://grailsblog.objectcomputing.com', permanent: true])

        "/screencasts"(redirect:[url: 'http://guides.grails.org', permanent:true])
        "/screencast/search"(redirect:[url: 'http://guides.grails.org', permanent:true])
        "/screencast/tag/$tag"(redirect:[url: 'http://guides.grails.org', permanent:true])
        "/screencast/$id"(redirect:[url: 'http://guides.grails.org', permanent:true])

        "/Testimonials"(redirect:[url:"https://grails.org/#they-use-groovy", permanent:true])
        "/testimonials"(redirect:[url:"https://grails.org/#they-use-groovy", permanent:true])
        "/testimonials/$id"(redirect:[url:"https://grails.org/#they-use-groovy", permanent:true])

        "/comment/add"(controller:"commentable", action:"add")

        "/websites"(redirect:[url:"https://grails.org/#they-use-groovy", permanent:true])
        "/websites/$category"(redirect:[url:"https://grails.org/#they-use-groovy", permanent:true])
        "/website/$id"(redirect:[url:"https://grails.org/#they-use-groovy", permanent:true])

        "/dynamicImage/${imageId}-${size}(.${type})"(controller: "dbContainerImage", action: "index")

        '/Tutorials'(redirect:[url: 'http://guides.grails.org', permanent:true])
        '/tutorials'(redirect:[url: 'http://guides.grails.org', permanent:true])
        '/tutorial/search'(redirect:[url: 'http://guides.grails.org', permanent:true])
        '/tutorial/$id'(redirect:[url: 'http://guides.grails.org', permanent:true])
        '/tutorials/tag/$tag'(redirect:[url: 'http://guides.grails.org', permanent:true])


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

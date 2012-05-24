import grails.util.Environment;

class UrlMappings {
    static mappings = {
        "/Home?"(controller: "content", action: "homePage")
        "/$id"(controller: "content", action: "index") {
            constraints {
                id notEqual: "dbconsole"
            }
        }

        "/start"(controller: "content", action: "gettingStarted")

        def populateVersion = {
            pluginVersion = {
                try {
                    def fn = params.fullName
                    if(fn.contains('-')) {
                        if(fn.startsWith("grails-")) {
                            fn = fn[7..-1]
                            params.fullName = fn
                        }
                        return fn[params.plugin.size()+1..-1]                         
                    }
                    else {
                        return params.version?.replace('_','.')
                    }
                }
                catch(e) {
                    params.version?.replace('_','.')
                }                
            }
        }

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

        "/download"(controller: "download", action: "latest")
        "/downloads"(controller: "download", action: "latest")

//        "/Download"(controller: "download", action: "latest")
//        "/download/file"(controller: "download", action: "downloadFile")
//        "/download/url"(controller: "download", action: "showUrl")
//        "/download/archive/$id"(controller: "download", action: "archive")
        "/wiki/latest"(controller: "content", action: "latest")
        "/auth/$action"(controller: "auth")


        /* ========================= PLUGINS ======================= */
        "/plugins/.plugin-meta/plugins-list.xml"(controller:"repository", action:"list")
        "/plugins/.plugin-meta"(controller:"repository", action:"pluginMeta")
        "/plugins/grails-$plugin/tags/RELEASE_$version/$fullName.${type}"(controller:"repository", action:"artifact", populateVersion)
        "/plugins/grails-$plugin/tags/LATEST_RELEASE/$fullName.${type}"(controller:"repository", action:"artifact", populateVersion)
        "/plugins/grails-$plugin/tags/LATEST_RELEASE"(controller:"repository", action:"listLatest")
        "/plugins"(controller: "plugin", action: "list")
        "/plugins/submitPlugin"(controller: "plugin", action: "submitPlugin")
        "/plugins/filter/$filter"(controller: "plugin", action: "list")
        "/plugins/tag/$tag"(controller: "plugin", action: "listByTag")
        "/plugins/$id"(controller: "plugin", action: "plugin") {
            constraints {
                id(notEqual: "tag")
            }
        }
        "/plugins/tag/"(controller: "plugin", action: "list") // Fix for possible bad path
        "/plugins/filter/"(controller: "plugin", action: "list") // Fix for possible bad path
        "/plugins/pending"(controller: "plugin", action: "pendingPlugins")
        "/plugins/pending/$id"(controller: "plugin", action: "showPendingPlugin")
        "/plugins/search"(controller: "plugin", action: "search")



//        "/Plugins"(controller: "plugin", action: "legacyHome")
//        "/plugins"(controller: "plugin", action: "home")
//        "/plugins/forum"(controller: "plugin", action: "forum")
//        "/plugin/$name"(controller: "plugin") {
//            action = [ GET: "show", PUT: "apiUpdate" ]
//            parseRequest = true
//        }
//        "/plugin/home"(controller: "plugin", action:"home")
//        "/plugin/search"(controller: "plugin", action:"search")
//        "/plugin/list"(controller: "plugin", action:"list")
//        "/plugin/create"(controller: "plugin", action:"createPlugin")
//        "/plugin/delete/$name"(controller: "plugin", action:"deletePlugin")
//        "/plugin/edit/$id"(controller: "plugin", action:"editPlugin")
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

        name pluginTab: "/plugin/$id/$action/$_ul" {
            controller = "pluginTab"
        }

        "/wikiImage/$path**"(controller: "content", action: "showImage")
        "/content/postComment/$id"(controller: "content", action:"postComment")

        "/blog/delete/$id"(controller: 'blogEntry', action:'delete')
        "/blog"(controller:"blog", action:"list")
        "/Grails+Screencasts"(controller: "content", action: "screencastLegacy")

        "/rateable/rate/$id"(controller: "rateable", action:"rate")
        "/tag/autoCompleteNames"(controller:'tag', action:'autoCompleteNames')

        "/search"(controller: "content", action: "search")
        "/upload/$id?"(controller: "content", action: "uploadImage")
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
        "/profile"(controller: "user", action: "profile")
        "/logout"(controller: "user", action: "logout")
        "/edit/$id"(controller: "content", action: "editWikiPage")
        "/save/$id"(controller: "content", action: "saveWikiPage")
        "/preview/$id"(controller: "content", action: "previewWikiPage")
        "/create/$id"(controller: "content", action: "createWikiPage")
        "/info/$id"(controller: "content", action: "infoWikiPage")
        "/markup/$id"(controller: "content", action: "markupWikiPage")
        "/version/$id/$number"(controller: "content", action: "showWikiVersion")
        "/rollback/$id/$number"(controller: "content", action: "rollbackWikiVersion")
        "/diff/$id/$number/$diff"(controller: "content", action: "diffWikiVersion")
        "/previous/$id/$number"(controller: "content", action: "previousWikiVersion")


        /* ========================= LEARN ======================= */
        "/learn"(controller:"learn", action:"gettingStarted")
        "/learn/IDE_setup"(controller:"learn", action:"ideSetup")
        "/learn/installation"(controller:"learn", action:"installation")
        "/learn/quickStart"(controller:"learn", action:"quickStart")
        "/learn/screencasts"(controller:"learn", action:"screencasts")
        "/learn/tutorials"(controller:"tutorial", action:"list")

        /* ========================= COMMUNITY ======================= */
        "/community"(controller: "community", action: "index")
        "/community/websites"(controller: "webSite", action: "list")
        "/community/testimonials"(controller: "community", action: "testimonials")
        "/community/contribute"(controller: "community", action: "contribute")
        "/community/mailingList"(controller: "community", action: "mailingList")
        "/community/twitter"(controller: "community", action: "twitter")

        "/screencasts"(controller:"screencast", action:"list")
        "/screencasts/tags"(controller:"screencast", action:"browseTags")
        "/screencasts/tags/$tag"(controller:"screencast", action:"search")
        "/screencast/save"(controller:"screencast", action:"save")
        "/screencast/search"(controller:"screencast", action:"search")
        "/screencast/update"(controller:"screencast", action:"update")
        "/screencast/edit/$id"(controller:"screencast", action:"edit")
        "/screencast/feed"(controller:"screencast", action:"feed")
        "/screencast/add"(controller:"screencast", action:"create")
        "/screencast/show/$id"(controller:"screencast", action:"show")
        "/comment/add"(controller:"commentable", action:"add")

        "/websites"(controller: "webSite", action: "list")
        "/websites/$category"(controller: "webSite", action: "list")
        "/websites/add"(controller: "webSite", action: "create")
        "/websites/save"(controller: "webSite", action: "save")
        "/websites/search"(controller:"webSite", action:"search")
        "/websites/tags"(controller:"webSite", action:"browseTags")
        "/websites/feed"(controller:"webSite", action:"feed")
        "/website/$id"(controller: "webSite", action: "show")
        "/website/edit/$id"(controller: "webSite", action: "edit")
        "/website/update/$id"(controller: "webSite", action: "update")

        "/dynamicImage/${imageId}-${size}.${type}"(controller: "dbContainerImage", action: "index")

        "/tutorials"(controller: "tutorial", action: "list")
        "/tutorials/add"(controller: "tutorial", action: "create")
        "/tutorials/save"(controller: "tutorial", action: "save")
        "/tutorial/$id"(controller: "tutorial", action: "show")
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
}

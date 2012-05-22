modules = {
    master {
        resource url: 'css/style.css'
        resource 'js/libs/bootstrap/bootstrap.js'
        resource 'js/libs/bootstrap/dropdown.js'
        resource 'js/script.js'
    }

    homepage {
        dependsOn 'master'
        resource 'js/twitter.js'
    }

    learn {
        dependsOn 'master'
    }

    community {
        dependsOn 'master'
    }

    download {
        dependsOn 'master'
    }

    plugin {
        dependsOn 'master'
    }

    auth {
        dependsOn 'master'
    }

    content {
        dependsOn 'master'
    }

    section {
        dependsOn 'content'
    }

    tutorial {
        dependsOn 'master'
    }



    // OLD

//    content {
//        resource url: 'css/content.css'
//    }
//    plugins {
//        dependsOn 'master'
//        resource url: 'css/new/plugins.css'
//    }
//    pluginInfo {
//        dependsOn 'master'
//        resource url: 'css/new/pluginInfo.css'
//    }
//    pluginNav {
//        dependsOn 'rateable'
//    }
//    common {
//        dependsOn 'yui-core'
//        resource url: 'js/common/yui-effects.js', disposition: 'head'
//        resource url: 'js/diff_match_patch.js', disposition: 'head'
//    }
//    pluginDetails {
//        dependsOn 'pluginInfo', 'content', 'common', 'rateable', 'grailsui-tabview', 'grailsui-dialog', 'grailsui-autocomplete'
//        ['tabview', 'plugins'].each { sheet ->
//            resource url: "css/${sheet}.css".toString()
//        }
//    }
//    subpagecss {
//        resource url: 'css/new/subpage.css'
//    }
//    subpage {
//        dependsOn 'master','content', 'common', 'subpagecss', 'yui-core', 'yui-connection', 'yui-animation'
//    }
//    section {
//        dependsOn 'master', 'content', 'subpagecss', 'jquery'
//        resource url: 'css/new/section.css'
//    }
//    upload {
//        dependsOn 'master', 'yui-animation', 'common'
//    }
}
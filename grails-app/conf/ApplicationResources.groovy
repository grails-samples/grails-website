modules = {
//    master {
//        resource url: 'css/style.css'
//    }
//    homepage {
//        dependsOn 'master'
//        resource 'css/new/homepage.css'
//        resource 'js/libs/jquery-1.7.1.min.js'
//        resource 'js/libs/modernizr-2.5.3-respond-1.1.0.min.js'
//        resource 'js/script.js'
//        resource 'js/script-ck.js'
//        resource 'js/twitter.js'
//        resource 'js/prototype/prototype.js'
//        resource 'js/prototype/scriptaculous.js'
//        resource 'js/prototype/effects.js'
//    }
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

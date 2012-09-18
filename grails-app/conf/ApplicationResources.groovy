modules = {
    master {
        dependsOn 'jquery'
        resource 'css/style.css'
        resource 'js/libs/bootstrap/bootstrap.js'
        resource url: 'js/libs/modernizr-2.5.3-respond-1.1.0.min.js', disposition: 'head'
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
        resource 'css/downloads.css'
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

    admin {
        resource 'js/libs/jquery-1.7.2.min.js'
        resource 'js/libs/bootstrap/bootstrap.js'
        resource 'css/bootstrap.css'
        resource 'css/bootstrap-responsive.css'
        resource 'css/admin.css'
    }

    codeMirror {
        resource 'js/libs/codemirror/codemirror.js'
        resource 'js/libs/codemirror/util/closetag.js'
        resource 'js/libs/codemirror/util/overlay.js'
        resource 'js/libs/codemirror/util/runmode.js'
        resource 'css/codemirror.css'
        resource 'js/libs/codemirror/mode/xml/xml.js'
        resource 'js/libs/codemirror/mode/javascript/javascript.js'
        resource 'js/libs/codemirror/mode/css/css.js'
        resource 'js/libs/codemirror/mode/htmlmixed/htmlmixed.js'
    }

    fancyBox {
        dependsOn 'jquery'
        resource 'fancybox/jquery.mousewheel-3.0.6.pack.js'
        resource 'fancybox/jquery.fancybox.css'
        resource 'fancybox/jquery.fancybox.js'
    }

    errors {
        dependsOn "master"
        resource url: "css/errors.css"
    }
    
    diffmatch {
        resource url: 'js/diff_match_patch.js', disposition: 'head'
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

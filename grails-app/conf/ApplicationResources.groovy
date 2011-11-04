modules = {
    master {
        resource url: 'css/new/master.css'
    }
    homepage {
        dependsOn 'master'
        resource 'css/new/homepage.css'
        resource 'js/prototype/prototype.js'
        resource 'js/prototype/scriptaculous.js'
        resource 'js/prototype/effects.js'
    }
    content {
        resource url: 'css/content.css'
    }
    plugins {
        dependsOn 'master'
        resource url: 'css/new/plugins.css'
    }
    pluginInfo {
        dependsOn 'master'
        resource url: 'css/new/pluginInfo.css'
    }
    common {
        resource url: 'js/common/yui-effects.js'
        resource url: 'js/diff_match_patch.js'
    }
    pluginDetails {
        dependsOn 'pluginInfo', 'content', 'common'
        ['tabview', 'plugins'].each { sheet ->
            resource url: "css/${sheet}.css".toString()
        }
    }
    subpagecss {
        resource url: 'css/new/subpage.css'
    }
    subpage {
        dependsOn 'master','content', 'subpagecss', 'yui-core', 'yui-connection', 'yui-animation', 'common'
    }
    section {
        dependsOn 'master', 'content', 'subpagecss', 'jquery'
        resource url: 'css/new/section.css'
    }
    upload {
        dependsOn 'yui-animation', 'common'
    }
}

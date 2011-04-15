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
    subpage {
        dependsOn 'master','content', 'common'
        resource url: 'css/new/subpage.css'
    }
}

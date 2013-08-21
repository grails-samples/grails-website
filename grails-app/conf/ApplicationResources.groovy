import grails.util.Metadata

modules = {

    masterStyles {
        if (isDevMode()) {
            resource url: 'less/style.less', attrs: [rel: 'stylesheet/less', type: 'css']
        }
        else {
            resource url: 'css/style.css'
        }
    }

    master {
        dependsOn 'jquery', 'masterStyles', 'fontAwesome'
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


    plugin {
        dependsOn 'master', 'raty', 'tagit'
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

    testimonial {
        dependsOn 'master'
    }

    admin {
        resource 'js/libs/jquery-1.7.2.min.js'
        resource 'js/libs/bootstrap/bootstrap.js'
        resource 'css/bootstrap.css'
        resource 'css/bootstrap-responsive.css'
        resource 'css/admin.css'
    }

    raty {
        resource "js/jquery.raty.min.js"
    }

    tagit {
        dependsOn "jquery-ui"
        resource "js/tag-it.js"
        resource "css/jquery.tagit.css"
        resource "css/tagit.ui-zendesk.css"
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

    fontAwesome {
        resource 'font-awesome/css/font-awesome.min.css'
    }

    errors {
        dependsOn "master"
        resource url: "css/errors.css"
    }
    
    diffmatch {
        resource url: 'js/diff_match_patch.js', disposition: 'head'
    }

    fileUploader {
        dependsOn "master"
        resource url: '/js/fileuploader.min.js'
    }

    imageUpload {
        dependsOn "fileUploader"
        resource url: '/js/image-upload.js'
    }

    wikiEditor {
        dependsOn: 'codeMirror'
        resource url: '/js/wiki-editor.js'
    }


}

/** Returns {@code true} if we're using run-app. */
boolean isDevMode() { !Metadata.current.isWarDeployed() }

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
        dependsOn 'jquery', 'masterStyles'
        resource 'js/libs/bootstrap/bootstrap.js'
        resource url: 'js/libs/modernizr-2.5.3-respond-1.1.0.min.js', disposition: 'head'
        resource 'js/script.js'
    }

    homepage {
        dependsOn 'master'
        resource 'js/twitter.js'
        if (isDevMode()) {
            resource url: 'less/homepage.less', attrs: [rel: 'stylesheet/less', type: 'css']
        }
        else {
            resource 'css/homepage.css'
        }        
    }

    learn {
        dependsOn 'master'
    }

    community {
        dependsOn 'master'
    }

    download {
        dependsOn 'master'

        if (isDevMode()) {
            resource url: 'less/downloads.less', attrs: [rel: 'stylesheet/less', type: 'css']
        }
        else {
            resource 'css/downloads.css'
        }
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

    search {
        dependsOn "master"
        if (isDevMode()) {
            resource url: 'less/search.less', attrs: [rel: 'stylesheet/less', type: 'css']
        }
        else {
            resource 'css/search.css'
        }        
    }

    fileUploader {
        dependsOn "master"
        resource url: '/js/fileuploader.min.js'
    }

    imageUpload {
        dependsOn "fileUploader"
        resource url: '/js/image-upload.js'
    }

}

/** Returns {@code true} if we're using run-app. */
boolean isDevMode() { !Metadata.current.isWarDeployed() }

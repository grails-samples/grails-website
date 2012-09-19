import org.lesscss.LessCompiler

eventCreateWarStart = { name, stagingDir ->
    compileLessCss name, stagingDir
}

void compileLessCss(name, stagingDir) {
    // Instantiate the LESS compiler
    def lessCompiler = new LessCompiler()

    // Or compile LESS input file to CSS output file
    event "StatusUpdate", [ "LESS CSS: compiling .less files ..." ]
    lessCompiler.compile(
            new File(stagingDir, "less/style.less"),
            new File(stagingDir, "css/style.css"))
    lessCompiler.compile(
            new File(stagingDir, "less/downloads.less"),
            new File(stagingDir, "css/downloads.css"))
    event "StatusUpdate", [ "LESS CSS: compilation done" ]
}

def classExcludePatterns = [
    'BuildConfig*',
    'grails/buildtestdata/**',
    'grails/plugin/fixtures/**',
    'grails/plugin/spock/**',
    'org/codehaus/groovy/grails/plugins/dbutil/**',
    'org/grails/test/**',
    'org/grails/tomcat/**',
    'BuildTestDataGrailsPlugin*',
    'DbUtilGrailsPlugin*',
    'FixturesGrailsPlugin*',
    'GebGrailsPlugin*',
    'SpockGrailsPlugin*',
    'TomcatGrailsPlugin*'
]

def resourceExcludePatterns = [
    'plugins/**/images/skin/**',
    'plugins/**/images/tree/**',
    'plugins/**/js/prototype/**',
    'plugins/**/js/yui/**',
    'plugins/**/js/yui-cms/**',
    'plugins/**/css/main.css',
    'plugins/**/images/favicon.ico',
    'plugins/**/images/grails_logo.jpg',
    'plugins/**/images/grails_logo.png',
    'plugins/**/images/leftnav_btm.png',
    'plugins/**/images/leftnav_midstretch.png',
    'plugins/**/images/leftnav_top.png',
    'plugins/**/images/spinner.gif',
    'plugins/**/images/springsource.png',
    'plugins/**/js/application.js'
]

def libExcludePatterns = [
    'hsqldb-*.jar',
    'mysql.jar',
    '*-javadoc.jar',
    '*-src.jar',
    '*-sources.jar'
]

/*
eventCreateWarStart = { warName, stagingDir ->
    return

    // Remove classes.
    ant.delete {
        fileset(dir: new File(stagingDir, "WEB-INF/classes").canonicalPath) {
            for (p in classExcludePatterns) {
                include name: p
            }
        }
    }

    // Now remove resources.
    ant.delete {
        fileset(dir: stagingDir.canonicalPath) {
            for (p in resourceExcludePatterns) {
                include name: p
            }
        }
    }

    // JARs.
    ant.delete {
        fileset(dir: new File(stagingDir, "WEB-INF/lib").canonicalPath) {
            for (p in libExcludePatterns) {
                include name: p
            }
        }
    }
}
*/

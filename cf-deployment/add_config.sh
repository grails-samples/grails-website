#!/bin/bash
cd "$( dirname "${BASH_SOURCE[0]}")"
if [ -d WEB-INF ]; then
   zip -r ../target/site-*.war WEB-INF
else
   mkdir -p WEB-INF/classes
   echo 'grails.serverURL="http://plugins-grails-org-dev.cfapps.io/"' > WEB-INF/classes/site-config.groovy
   echo 'edit WEB-INF/classes/site-config.groovy and re-run this script'
fi

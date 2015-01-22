# make sure ~/.grails-static-website directory exists since META-INF/context.xml references it
[ ! -d ~/.grails-static-website ] && mkdir ~/.grails-static-website

#export JAVA_HOME=/usr/lib/jvm/jdk1.7.0_60
export LC_ALL=en_US.UTF-8
export LANG=en_US.UTF-8
CATALINA_OPTS="$CATALINA_OPTS -server -noverify"
CATALINA_OPTS="$CATALINA_OPTS -XX:MaxPermSize=256M -Xms768M -Xmx768M" # tune heap size
CATALINA_OPTS="$CATALINA_OPTS -Djava.net.preferIPv4Stack=true" # disable IPv6 if you don't use it
# set default file encoding and locale
CATALINA_OPTS="$CATALINA_OPTS -Dfile.encoding=UTF-8 -Duser.language=en -Duser.country=US"
CATALINA_OPTS="$CATALINA_OPTS -Duser.timezone=CST" # set default timezone
CATALINA_OPTS="$CATALINA_OPTS -Dgrails.env=production" # set grails environment
# set timeouts for JVM URL handler
CATALINA_OPTS="$CATALINA_OPTS -Dsun.net.client.defaultConnectTimeout=10000 -Dsun.net.client.defaultReadTimeout=10000"
CATALINA_OPTS="$CATALINA_OPTS -Duser.dir=$CATALINA_HOME"
CATALINA_OPTS="$CATALINA_OPTS -Dinitial.admin.password=verysecure -Dload.fixtures=true"
export CATALINA_OPTS
export CATALINA_PID="$CATALINA_HOME/logs/tomcat.pid"


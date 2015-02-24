#!/bin/bash
if [ -z "$1" ]; then
  echo "usage: $0 [cf-appname]"
  cf apps
  exit 1
fi
cf files $1 app/.java-buildpack/tomcat/logs/stacktrace.log
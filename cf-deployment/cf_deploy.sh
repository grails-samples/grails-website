#!/bin/bash -x -e
cd "$( dirname "${BASH_SOURCE[0]}")"
cd ..
grails refresh-dependencies --non-interactive
grails compile --non-interactive
grails war --non-interactive
cd -
./add_config.sh
cd ..
cf target -s development
cf push

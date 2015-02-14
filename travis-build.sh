#!/bin/bash -xe
./grailsw refresh-dependencies --non-interactive
./grailsw compile --non-interactive
./grailsw test-app :unit --non-interactive
if [[ $TRAVIS_BRANCH == master && $TRAVIS_REPO_SLUG == "grails-samples/grails-website"
    && $TRAVIS_PULL_REQUEST == 'false' ]]; then
  DEPLOY_ARGS=""
  # push to production when tagged with tag starting with prod_
  if [[ $TRAVIS_TAG == prod_* ]]; then
    DEPLOY_ARGS="-Pprod"
    export CF_SPACE=production
  else
    export CF_SPACE=development
  fi
  ./grailsw prod war --non-interactive
  ./travis-decrypt-files.sh
  if [ -d "cf-deployment-$CF_SPACE" ]; then
    (cd "cf-deployment-$CF_SPACE" && zip -r ../target/grails-website.war * )
  fi
  gradle -b cf-deploy.gradle $DEPLOY_ARGS cfPush
fi

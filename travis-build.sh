#!/bin/bash -xe
# Travis CI build script that deploys to Pivotal Webservices CloudFoundry
#
# testing locally:
# TRAVIS_BRANCH=master TRAVIS_REPO_SLUG=grails-samples/grails-website TRAVIS_PULL_REQUEST=false ./travis-build.sh
#
deploy_to_cf() {
  CF_SPACE=$1
  if [ -d "cf-deployment-$CF_SPACE/webapp" ]; then
    (cd "cf-deployment-$CF_SPACE/webapp" && zip -r ../../target/grails-website.war * )
  fi
  if [[ $CF_SPACE == production ]]; then
    DEPLOY_ARGS="-Pprod"
  else
    DEPLOY_ARGS=""
  fi
  gradle_cf_deploy="gradle -b cf-deploy.gradle $DEPLOY_ARGS"
  $gradle_cf_deploy cfDeploy
  GIT_COMMIT_MSG="$(git log --format=%B --no-merges -n 1)"
  CF_DEPLOY_IN_COMMIT=1
  echo "$GIT_COMMIT_MSG" | grep '\[cf-deploy\]' || CF_DEPLOY_IN_COMMIT=0
  if [[ $CF_SPACE != production || $CF_DEPLOY_IN_COMMIT -eq 1 || $TRAVIS_TAG == *_activate ]]; then
      if [[ $CF_SPACE == production ]]; then
        # swap blue/green after successful deployment and undeploy other
        ./cf-swap-blue-green.sh $DEPLOY_ARGS
      fi
  else
    set +x
    echo "Using blue-green deployment. Deployed to either one. NOT ACTIVATED BY DEFAULT to the default route!"
    echo -e "You should manually swap the active route with this command in your local build environment:\n./cf-swap-blue-green.sh $DEPLOY_ARGS"
    echo "You must specify cfUsername and cfPassword in the cf-deploy.gradle.properties file in that case."
    echo "If you are unable to do this, push another commit with [cf-deploy] in the commit message and tag it with the production tag prod_"
  fi
}

./grailsw refresh-dependencies --non-interactive
./grailsw compile --non-interactive
./grailsw test-app :unit --non-interactive

if [[ ( $TRAVIS_BRANCH == master || $TRAVIS_TAG == prod_* ) && $TRAVIS_REPO_SLUG == "grails-samples/grails-website"
    && $TRAVIS_PULL_REQUEST == 'false' ]]; then
  ./grailsw war --non-interactive
  if [ -n "$CF_FILES_CRYPT_KEY" ]; then
    ./travis-decrypt-files.sh
  fi
  # push to production if commit is tagged with tag starting with prod_
  if [[ $TRAVIS_TAG == prod_* ]]; then
    deploy_to_cf production
  else
    deploy_to_cf development
  fi
fi

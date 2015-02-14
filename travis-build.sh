#!/bin/bash -xe
deploy_to_cf() {
  CF_SPACE=$1
  if [ -d "cf-deployment-$CF_SPACE" ]; then
    (cd "cf-deployment-$CF_SPACE" && zip -r ../target/grails-website.war * )
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
  if [[ $CF_SPACE != production || $CF_DEPLOY_IN_COMMIT -eq 1 ]]; then
    # swap blue/green after successful deployment and undeploy other
    $gradle_cf_deploy cfSwapDeployed cfUndeploy
  else
    set +x
    echo "Using blue-green deployment. Deployed to either one. NOT ACTIVATED BY DEFAULT to the default route!"
    echo -e "You should manually swap the active route with this command in your local build environment:\n$gradle_cf_deploy cfSwapDeployed cfUndeploy"
    echo "You must specify cfUsername and cfPassword in the cf-deploy.gradle.properties file in that case."
    echo "If you are unable to do this, push another commit with [cf-deploy] in the commit message and tag it with the production tag prod_"
  fi
}

./grailsw refresh-dependencies --non-interactive
./grailsw compile --non-interactive
./grailsw test-app :unit --non-interactive
if [[ ( $TRAVIS_BRANCH == master || $TRAVIS_TAG == prod_* ) && $TRAVIS_REPO_SLUG == "grails-samples/grails-website"
    && $TRAVIS_PULL_REQUEST == 'false' ]]; then
  DEPLOY_ARGS=""
  ./grailsw war --non-interactive
  # make backup of war file
  cp target/grails-website.war{,.orig}
  ./travis-decrypt-files.sh
  # always deploy to development first
  deploy_to_cf development
  # restore original war file after deployment
  cp target/grails-website.war{.orig,}
  # push to production if commit is tagged with tag starting with prod_
  if [[ $TRAVIS_TAG == prod_* ]]; then
    deploy_to_cf production
  fi
fi

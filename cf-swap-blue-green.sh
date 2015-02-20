#!/bin/bash
SCRIPTDIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
GRADLE_CMD="gradle -b $SCRIPTDIR/cf-deploy.gradle $@"
$GRADLE_CMD cfSwapDeployed && $GRADLE_CMD cfUpdateAliases

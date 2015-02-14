#!/bin/bash -e
cat cf-deploy.gradle.properties | ./travis/crypt_stream CF_FILES_CRYPT_KEY > cf-deploy.gradle.properties.gpg
tar zcvf - cf-deployment-production | ./travis/crypt_stream CF_FILES_CRYPT_KEY > cf-deploy-war.tar.gz.gpg

#!/bin/bash -e
cat cf-deploy.gradle.properties.gpg | ./travis/crypt_stream CF_FILES_CRYPT_KEY -d > cf-deploy.gradle.properties
cat cf-deploy-war.tar.gz.gpg | ./travis/crypt_stream CF_FILES_CRYPT_KEY -d | tar zxf -

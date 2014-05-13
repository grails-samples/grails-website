#!/bin/bash -xe
rm -rf *.zip
./grailsw refresh-dependencies --non-interactive
./grailsw compile --non-interactive
./grailsw test-app :unit --non-interactive
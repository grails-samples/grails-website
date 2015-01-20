#!/bin/bash
echo "remove web-app/.git and clean files from web-app directory"
rm -rf web-app/.git
cd web-app && git clean -fd
echo "Done."
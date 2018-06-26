#!/bin/sh
java -cp classes xax.tools.ManifestGenerator
/bin/rm -f xax.jar
jar cfm xax.jar resource/xax.manifest.mf -C classes . || exit 1
/bin/rm -f xaxservice.jar
jar cfm xaxservice.jar resource/xaxservice.manifest.mf -C classes . || exit 1

echo "jar files generated successfully"
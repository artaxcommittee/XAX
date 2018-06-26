#!/bin/sh
CP="conf/;classes/;lib/*;testlib/*"
SP="src/java/;test/java/"
TESTS="xax.crypto.Curve25519Test xax.crypto.ReedSolomonTest"

/bin/rm -f xax.jar
/bin/rm -rf classes
/bin/mkdir -p classes/

javac -encoding utf8 -sourcepath $SP -classpath $CP -d classes/ src/java/xax/*.java src/java/xax/*/*.java test/java/xax/*/*.java || exit 1

java -classpath $CP org.junit.runner.JUnitCore $TESTS


#!/bin/sh
CP=conf/:classes/:lib/*:testlib/*
SP=src/java/:test/java/

if [ $# -eq 0 ]; then
TESTS="xax.crypto.Curve25519Test xax.crypto.ReedSolomonTest xax.peer.HallmarkTest xax.TokenTest xax.FakeForgingTest
xax.FastForgingTest xax.ManualForgingTest"
else
TESTS=$@
fi

/bin/rm -f xax.jar
/bin/rm -rf classes
/bin/mkdir -p classes/

javac -encoding utf8 -sourcepath ${SP} -classpath ${CP} -d classes/ src/java/xax/*.java src/java/xax/*/*.java test/java/xax/*.java test/java/xax/*/*.java || exit 1

for TEST in ${TESTS} ; do
java -classpath ${CP} org.junit.runner.JUnitCore ${TEST} ;
done




#!/bin/sh
if [ -e ~/.xax/xax.pid ]; then
    PID=`cat ~/.xax/xax.pid`
    ps -p $PID > /dev/null
    STATUS=$?
    if [ $STATUS -eq 0 ]; then
        echo "XAX server already running"
        exit 1
    fi
fi
mkdir -p ~/.xax/
DIR=`dirname "$0"`
cd "${DIR}"
if [ -x jre/bin/java ]; then
    JAVA=./jre/bin/java
else
    JAVA=java
fi
nohup ${JAVA} -cp classes:lib/*:conf:addons/classes:addons/lib/* -Dxax.runtime.mode=desktop xax.XAX > /dev/null 2>&1 &
echo $! > ~/.xax/xax.pid
cd - > /dev/null

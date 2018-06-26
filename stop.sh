#!/bin/sh
if [ -e ~/.xax/xax.pid ]; then
    PID=`cat ~/.xax/xax.pid`
    ps -p $PID > /dev/null
    STATUS=$?
    echo "stopping"
    while [ $STATUS -eq 0 ]; do
        kill `cat ~/.xax/xax.pid` > /dev/null
        sleep 5
        ps -p $PID > /dev/null
        STATUS=$?
    done
    rm -f ~/.xax/xax.pid
    echo "XAX server stopped"
fi


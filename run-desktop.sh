#!/bin/sh
if [ -x jre/bin/java ]; then
    JAVA=./jre/bin/java
else
    JAVA=java
fi
${JAVA} -cp classes:lib/*:conf:addons/classes:addons/lib/* -Dxax.runtime.mode=desktop -Dxax.runtime.dirProvider=xax.env.DefaultDirProvider xax.XAX

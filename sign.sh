#!/bin/sh
java -cp "classes:lib/*:conf" xax.tools.SignTransactionJSON $@
exit $?

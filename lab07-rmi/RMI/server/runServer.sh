#!/bin/bash
java \
-Djava.rmi.server.codebase=file:/home/tomy/skul/ass77-labs/lab07-rmi/RMI/server/target/server-1.0-SNAPSHOT.jar \
-Djava.rmi.server.hostname=127.0.0.1 \
-Djava.security.policy=/home/tomy/skul/ass77-labs/lab07-rmi/RMI/server/target/classes/policy \
-jar \
/home/tomy/skul/ass77-labs/lab07-rmi/RMI/server/target/server-1.0-SNAPSHOT.jar
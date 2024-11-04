#!/bin/bash

echo "Deploying to Helios"

## Remove existing deployment
ssh -p 2222 s373440@se.ifmo.ru "rm -rf wildfly-33.0.2.Final/standalone/deployments/backend-1.0-SNAPSHOT.war"
# add new deployment
scp -P 2222 /home/vladimir/itmo-common/lab1/backend/build/libs/backend-1.0-SNAPSHOT.war s373440@se.ifmo.ru:wildfly-33.0.2.Final/standalone/deployments

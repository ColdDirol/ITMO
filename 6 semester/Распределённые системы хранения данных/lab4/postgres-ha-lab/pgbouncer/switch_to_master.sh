#!/bin/bash
docker stop pgbouncer
docker run -d --name pgbouncer_temp --network postgres-ha-lab_postgres_network --ip 172.20.0.20 -p 6432:5432 \
  -e DATABASE_URL="postgres://postgres:postgres123@postgres_master:5432/testdb" \
  -e POOL_MODE=transaction -e MAX_CLIENT_CONN=100 -e DEFAULT_POOL_SIZE=20 -e AUTH_TYPE=trust \
  edoburu/pgbouncer:latest
docker rm pgbouncer
docker rename pgbouncer_temp pgbouncer

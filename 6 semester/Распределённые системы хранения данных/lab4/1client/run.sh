#!/bin/bash

while true; do
  PGPASSWORD=postgres psql -h localhost -p 6432 -U postgres -d postgres -c "
    INSERT INTO first_data(data) VALUES ('client1: ' || now()::text);
    SELECT * FROM first_data;
  "
  sleep 10
done
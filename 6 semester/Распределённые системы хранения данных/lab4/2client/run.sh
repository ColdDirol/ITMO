#!/bin/bash

while true; do
  PGPASSWORD=postgres psql -h localhost -p 6432 -U postgres -d postgres -c "
    INSERT INTO second_data(data) VALUES ('client2: ' || now()::text);
    SELECT * FROM second_data;
  "
  sleep 10
done
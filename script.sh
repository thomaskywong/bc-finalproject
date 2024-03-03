#!/bin/bash

# Change to the first application directory
cd bc-crypto-coingecko/

# Run the first Spring Boot application in a separate terminal
start bash -c "mvn spring-boot:run; exec bash" &

# Change to the second application directory
cd ..
cd bc-stock-finnhub/

# Run the second Spring Boot application in a separate terminal
start bash -c "mvn spring-boot:run; exec bash" &

# back to root directory to standby
cd ..
#!/bin/bash

# Change to the first application directory
cd /github/bc-finalproject/bc-crypto-coingecko/

# Run the first Spring Boot application in a separate terminal
start bash -c "mvn spring-boot:run; exec bash" &

# Change to the second application directory
cd /github/bc-finalproject/bc-stock-finnhub/

# Run the second Spring Boot application in a separate terminal
start bash -c "mvn spring-boot:run; exec bash" &

# Run vue crypto
cd /github/bc-finalproject/bc-coingecko-vue/
start bash -c "yarn serve" &

# Run vue crypto and stock
cd /github/bc-finalproject/bc-product-vue/
start bash -c "yarn serve" &

# Run project 4
cd /github/bc-finalproject/bc-product-data/
mvn spring-boot:run
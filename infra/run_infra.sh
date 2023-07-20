#!/bin/bash

docker network create --attachable -d bridge techbankNet
docker-compose up -d
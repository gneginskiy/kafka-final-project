### Create Docker Network - techbankNet 
> docker network create --attachable -d bridge techbankNet

### To run infrastructure: 
> docker-compose up -d

#9. MongoDB

Run in Docker:
docker run -it -d --name mongo-container \
-p 27017:27017 --network techbankNet \
--restart always \
-v mongodb_data_container:/data/db \
mongo:latest

Download Client Tools – Robo 3T:
https://robomongo.org/download

#9. MySQL

Run in Docker:
docker run -it -d --name mysql-container \
-p 3306:3306 --network techbankNet \
-e MYSQL_ROOT_PASSWORD=techbankRootPsw \
--restart always \
-v mysql_data_container:/var/lib/mysql  \
mysql:latest

Client tools in Docker – Adminer:
docker run -it -d --name adminer \
-p 8080:8080 --network techbankNet \
 -e ADMINER_DEFAULT_SERVER=mysql-container \
--restart always adminer:latest




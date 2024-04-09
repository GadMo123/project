# Bootcamp Exercise

## Introduction
A continuous bootcamp project, that we're working on.


## Dependencies
You must have Docker installed on your system to build and run the containers.


## Tasks
**Part 1**
- Create new repository “boot-bootcamp” in your personal GitHub account
- Create new JAVA project
- Push your project to your github repository
- Use Gradle as a build tool
- Include HTTP Server library (choose as you wish)
- Your server should have a GET /boot-bootcamp endpoint

**Part 2**
- Run boot-bootcamp as a Docker container 
- Run 2 instances of boot-bootcamp behind a load-balancer. Use docker-compose

**Part 3**
- Integrate Jetty and JAX-RS (Jersey) as the REST API HTTP Server for your boot-bootcamp service

**Part 4**
- Make the server configurable
  Create a file `server.config` which will contain a json configuration file, as the following:
  ```
  {
      "port": 8080,
      "logMessage": "boot boot"
  }
  ```
  Create a class `ServerJerseyConfiguration`  that will hold the configuration parameters as private members
  Bind this class using guice, and use it in the appropriate places 

**Part 5**
- Add Elasticsearch Docker to your boot-bootcamp docker-compose
- Add 2 new endpoints to your server:
`POST /index --body '{"message": "boot camp first index"}'` which will index the received message to Elasticsearch, along with the `User-Agent` header value.  

In Elasticsearch your doc will look something like:  
```
{
    "source": {
                  "message": "boot camp first index",
                  "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X)"
              }
}
```

`GET /search?message=camp&header=Macintosh` which will return a list of all the documents matching the search
- Add an integration test

Note (Gad) - for Elastic v8 - we need to increase memory limit for the container to at least 262144 at the host machine:
```
$ wsl -d docker-desktop sh -c "sysctl -w vm.max_map_count=262144"
vm.max_map_count = 262144
```

**Part 6**
- Add kafka Docker to your boot-bootcamp docker-compose
- Change your /index endpoint behaviour: Instead of indexing the message directly to Elasticsearch, it will write the message to kafka
- Create a new micro-service called indexer that consumes messages from kafka and index them to Elasticsearch
- Ensure your existing integration test is GREEN without any changes in the test

**Part 7**
- Add mysql:5.6.34 to your docker-compose
- Create a new endpoint `POST /create-account --body {"accountName": "Kivid"}` which will be responded with `{ "id": 1, "name": "Kivid", "token": "YZZXfOLKfTJEMGgKknWaKOpURnvALnRi", "esIndexName": "logz-jopnbwmknooanqwzxgpybunufztysazs" }`
- Change your `POST /index` endpoint to accept a token, as `POST /index/YZZXfOLKfTJEMGgKknWaKOpURnvALnRi` . If a wrong token will be given, response will be 401 Unauthorized. The log will be stored in the account’s index, which will be the value of esIndexName
- Change your `GET /search` endpoint to accept a token, as `GET /search --header "X-ACCOUNT-TOKEN: YZZXfOLKfTJEMGgKknWaKOpURnvALnRi"` 
- Add test(s) to verify each account can index and query his own logs only.
- Add a new service called “accounts-service”
- The new service will be the one receiving the `POST /create-account` request 
- It will be the only service which has access to the MySQL database
- Everyone else (listener, indexer etc.) who wants to get an account details, will request it from accounts-service. For this, you will need accounts-service to expose some other endpoints (i.e `GET /account/token/YZZXfOLKfTJEMGgKknWaKOpURnvALnRi` responds with `{ "id": 1, "name": "Kivid", "token": "YZZXfOLKfTJEMGgKknWaKOpURnvALnRi", "esIndexName": "logz-jopnbwmknooanqwzxgpybunufztysazs" }` )

## Installation


Building the JAR file:

```
./gradlew shadowJar
```

Running the project:

```
./gradlew start
```

Running the tests:

```
./gradlew test
```

The number of instances can be changed in the `.env` file : REPLICAS=(num-of-instances)

The endpoint of the HAProxy, to view logs
[localhost:8080/logs](http://localhost:8080/logs)

## Rest API

To index a message into elastic search, use the curl command
```
curl -X POST 'http://localhost:8080/api/index' -H "Content-Type: application/json" -A "Mozilla/5.0 (Macintosh; Intel Mac OS X)" -d '{"message": "boot camp first index"}'
```

To search for messages, use the curl command
```
curl -X GET 'http://localhost:8080/api/search?message=boot&header=Macintosh'
```

## Usfull scripts for testing on local machine (everything is automated for hosting machine via docker-compose)


Local setup for Elasticsearch container (windows, docker desktop)
```
Configure Docker Elasticseach container memory:
wsl -d docker-desktop -u root
sysctl -w vm.max_map_count=262144
```

```
Eun docker Elasticsearch on local machine testing
# docker run --name es01 -p 9200:9200 -it -m 1GB docker.elastic.co/elasticsearch/elasticsearch:8.12.1
# docker exec -it es01 /usr/share/elasticsearch/bin/elasticsearch-reset-password -u elastic
# copy menually the new password to .env
```



```
Do Post Elasticsearch:
# $headers = @{"Content-Type" = "application/json"; "User-Agent" = "Mozilla/5.0 (Macintosh; Intel Mac OS X)"} 
```

```
Clean all docker images: 
# FOR /F "tokens=*" %i IN ('docker images -q') DO docker rmi %i
# docker rmi $(docker images -a -q)
```
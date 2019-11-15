## Movie Rating Service

### Local postgres

* mkdir -p $HOME/docker/volumes/postgres
* docker run --rm   --name pg-docker -e POSTGRES_PASSWORD=docker -d -p 5432:5432 -v $HOME/docker/volumes/postgres:/var/lib/postgresql/data  postgres

## Rating Service

* cd rating
* ./gradlew clean build docker --info --parallel

## Customer Service
* cd customer
* ./gradlew clean build docker --info --parallel



## To Run service
* ./gradlew up


## To Stop servie
* ./gradlew down

# The Hub App

* https://thehub.dk/
* https://thehub.se/
* https://thehub.fi/
* http://hub.no/

## Backend

```
cd docker; docker-compose up -d; cd ..
./gradlew check
./gradlew bootRun
http POST http://localhost:8080/api/jobs/sync
open http://localhost:8080/api/jobs
```

```
./gradlew generateSoftwarebergJooqSchemaSource
```

## Frontend

```
nvm install 10.8.0
yarn
yarn start
open http://localhost:3000
```

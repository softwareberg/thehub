# The Hub App

[![Build Status](https://travis-ci.org/codeloopeu/thehub.svg?branch=master)](https://travis-ci.org/codeloopeu/thehub)
[![codecov](https://codecov.io/gh/codeloopeu/thehub/branch/master/graph/badge.svg)](https://codecov.io/gh/codeloopeu/thehub)

* https://thehub.dk/
* https://thehub.se/
* https://thehub.fi/
* http://hub.no/

## Backend

```
cd docker; docker-compose up -d --force-recreate --build; cd ..
./gradlew check
./gradlew bootRun
http POST http://localhost:8080/api/jobs/sync
open http://localhost:8080/api/jobs
```

We use [jOOQ](https://www.jooq.org) for generating code that deals with SQL. If you change something related to databse you will need to regenerate this code. You can generate it explicitly:

```
rm -fr src/generated/java
./gradlew generateCodeloopJooqSchemaSource
```

Or you can define environmental variable and call any gradle task:

```
export GENERATE_SCHEMA=true
./gradlew check
```

Values can be `true`, `false` or variable is not defined at all (not generating). Other values are undefined behaviour.

## Frontend

```
nvm install 12.8.1
yarn
yarn start
open http://localhost:3000
```

## Known problems

Sometimes when exists empty folder `src/generated/java` jOOQ does not generate code at all. To fix this just remove the folder.

Right now gradle dependencies are a little messed up if you are generating code for database. jOOQ uses real database to generate source code. It means that database must be runnig with correct schema. jOOQ task is before compilation task. On the other hand there is flyway that applies schema to database. Unfortunately this task is after compilation task. So if you wipe out database and remove generated jOOQ code you are stuck. In this situation you have to manually run flyway migration (see below) and then generate jOOQ code.

## Troubleshooting

Sometimes backend refuses to start/compile even if source code is correct. When this happen try to run flyway migration manually. Maybe there is problem with postgres schema (conflict during migration) or maybe there is problem with order of dependencies (see above).

```
cd api/
flyway -url="jdbc:postgresql://127.0.0.1/codeloop" -user="codeloop" -password="codeloop" -locations="filesystem:src/main/resources/db/migration/" migrate
```

If there is a conflict the easiest/laziest thing you can do is to delete docker container with postgres and create new (empty) one.

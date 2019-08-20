# The Hub App

[![Build Status](https://travis-ci.org/codeloopeu/thehub.svg?branch=master)](https://travis-ci.org/codeloopeu/thehub)
[![codecov](https://codecov.io/gh/codeloopeu/thehub/branch/master/graph/badge.svg)](https://codecov.io/gh/codeloopeu/thehub)

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

We use [jOOQ](https://www.jooq.org) for generating code that deals with SQL. If you change something related to databse you will need to regenerate this code. You can generate it explicitly:

```
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

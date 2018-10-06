CREATE USER softwareberg WITH PASSWORD 'softwareberg';
ALTER USER softwareberg WITH SUPERUSER;

CREATE DATABASE softwareberg;
CREATE DATABASE softwareberg_test;

GRANT ALL PRIVILEGES ON DATABASE softwareberg TO softwareberg;
GRANT ALL PRIVILEGES ON DATABASE softwareberg_test TO softwareberg;

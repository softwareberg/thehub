CREATE TABLE domains (
  domain        VARCHAR(255)             NOT NULL PRIMARY KEY,
  date_created  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
  date_modified TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

CREATE TABLE locations (
  location      VARCHAR(255)             NOT NULL PRIMARY KEY,
  date_created  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
  date_modified TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

CREATE TABLE companies (
  comapny_id    VARCHAR(255)             NOT NULL PRIMARY KEY,
  name          VARCHAR(255)             NOT NULL,
  logo          VARCHAR(255)             NOT NULL,
  domain        VARCHAR(255)             NOT NULL,
  location      VARCHAR(255)             NOT NULL,
  date_created  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
  date_modified TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
  FOREIGN KEY (domain) REFERENCES domains (domain),
  FOREIGN KEY (location) REFERENCES locations (location)
);

CREATE TABLE positions_types (
  position_type VARCHAR(255)             NOT NULL PRIMARY KEY,
  date_created  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
  date_modified TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

CREATE TABLE job_keywords (
  tag           VARCHAR(255)             NOT NULL PRIMARY KEY,
  date_created  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
  date_modified TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

CREATE TABLE jobs (
  job_id        VARCHAR(255)             NOT NULL PRIMARY KEY,
  comapny_id    VARCHAR(255)             NOT NULL,
  title         VARCHAR(255)             NOT NULL,
  description   TEXT                     NOT NULL,
  position_type VARCHAR(255)             NOT NULL,
  date_created  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
  date_modified TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
  FOREIGN KEY (comapny_id) REFERENCES companies (comapny_id),
  FOREIGN KEY (position_type) REFERENCES positions_types (position_type)
);

CREATE TABLE job_job_keywords (
  tag           VARCHAR(255)             NOT NULL,
  job_id        VARCHAR(255)             NOT NULL,
  date_created  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
  date_modified TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
  PRIMARY KEY (tag, job_id),
  FOREIGN KEY (tag) REFERENCES job_keywords (tag),
  FOREIGN KEY (job_id) REFERENCES jobs (job_id)
);

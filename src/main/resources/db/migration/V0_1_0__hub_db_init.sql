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
  company_id    VARCHAR(255)             NOT NULL PRIMARY KEY,
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
  keyword       VARCHAR(255)             NOT NULL PRIMARY KEY,
  date_created  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
  date_modified TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

CREATE TABLE equities (
  equity        VARCHAR(255)             NOT NULL PRIMARY KEY,
  date_created  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
  date_modified TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

CREATE TABLE monthly_salaries (
  monthly_salary VARCHAR(255)             NOT NULL PRIMARY KEY,
  date_created   TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
  date_modified  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

CREATE TABLE jobs (
  job_id         VARCHAR(255)             NOT NULL PRIMARY KEY,
  company_id     VARCHAR(255)             NOT NULL,
  title          VARCHAR(255)             NOT NULL,
  monthly_salary VARCHAR(255),
  equity         VARCHAR(255),
  description    TEXT                     NOT NULL,
  position_type  VARCHAR(255)             NOT NULL,
  date_created   TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
  date_modified  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
  FOREIGN KEY (company_id) REFERENCES companies (company_id),
  FOREIGN KEY (position_type) REFERENCES positions_types (position_type),
  FOREIGN KEY (equity) REFERENCES equities (equity),
  FOREIGN KEY (monthly_salary) REFERENCES monthly_salaries (monthly_salary)
);

CREATE TABLE jobs_job_keywords (
  keyword       VARCHAR(255)             NOT NULL,
  job_id        VARCHAR(255)             NOT NULL,
  date_created  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
  date_modified TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
  PRIMARY KEY (keyword, job_id),
  FOREIGN KEY (keyword) REFERENCES job_keywords (keyword),
  FOREIGN KEY (job_id) REFERENCES jobs (job_id)
);

CREATE TABLE job_perks (
  job_perk_id   VARCHAR(255)             NOT NULL PRIMARY KEY,
  description   VARCHAR(255)             NOT NULL,
  date_created  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
  date_modified TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

CREATE TABLE jobs_job_perks (
  job_perk_id   VARCHAR(255)             NOT NULL,
  job_id        VARCHAR(255)             NOT NULL,
  date_created  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
  date_modified TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
  PRIMARY KEY (job_perk_id, job_id),
  FOREIGN KEY (job_perk_id) REFERENCES job_perks (job_perk_id),
  FOREIGN KEY (job_id) REFERENCES jobs (job_id)
);

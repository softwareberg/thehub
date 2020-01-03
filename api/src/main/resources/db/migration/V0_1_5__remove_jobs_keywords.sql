DROP TABLE jobs_job_perks;
DROP TABLE job_perks;

DROP TABLE jobs_job_keywords;
DROP TABLE job_keywords;

ALTER TABLE jobs DROP COLUMN position_type;
DROP TABLE positions_types;

ALTER TABLE companies DROP COLUMN domain;
DROP TABLE domains;

ALTER TABLE jobs DROP COLUMN poster;

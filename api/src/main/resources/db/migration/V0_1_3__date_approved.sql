ALTER TABLE jobs
ADD COLUMN approved_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT '1970-01-01 00:00:00+00';

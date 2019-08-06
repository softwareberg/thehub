import React from 'react';
import { useSelector } from 'react-redux';
import Job from './Job';
import useAllJobsData from '../hooks/useAllJobsData';
import useEmptyJobsDidMount from '../hooks/useEmptyJobsDidMount';

const AllJobs = () => {
  const jobs = useSelector(state => state.jobs);

  useEmptyJobsDidMount();
  useAllJobsData();

  return (
    <React.Fragment>
      <h1>All Jobs</h1>
      {jobs.map(job => <Job key={job.jobId} job={job} />)}
    </React.Fragment>
  );
};

export default AllJobs;

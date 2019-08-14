import React from 'react';
import { useSelector } from 'react-redux';
import useAllJobsData from 'hooks/useAllJobsData';
import useEmptyJobsDidMount from 'hooks/useEmptyJobsDidMount';
import Job from 'components/Job';
import Pagination from './Pagination';

const AllJobs = () => {
  const jobs = useSelector(state => state.jobs);
  const pagination = useSelector(state => state.pagination);
  const { page } = pagination;

  useEmptyJobsDidMount();
  useAllJobsData(page);

  return (
    <React.Fragment>
      <h1>All Jobs</h1>
      <Pagination />
      {jobs.map(job => <Job key={job.jobId} job={job} />)}
      {jobs.length > 0 && <Pagination />}
    </React.Fragment>
  );
};

export default AllJobs;

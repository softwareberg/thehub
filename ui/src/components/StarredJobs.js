import React from 'react';
import { useSelector } from 'react-redux';
import Job from 'components/Job';
import useStarredJobsData from 'hooks/useStarredJobsData';
import useEmptyJobsDidMount from 'hooks/useEmptyJobsDidMount';

const StarredJobs = () => {
  const jobs = useSelector(state => state.jobs);

  useEmptyJobsDidMount();
  useStarredJobsData();

  return (
    <React.Fragment>
      <h1>Starred Jobs</h1>
      {jobs.map(job => <Job key={job.jobId} job={job} />)}
    </React.Fragment>
  );
};

export default StarredJobs;

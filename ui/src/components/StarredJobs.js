import React, {useEffect, useState} from 'react';
import clearJobsAction from '../redux/actions/clearJobs';
import { useDispatch, useSelector } from 'react-redux';
import { fetchStarredJobs } from '../utils/api';
import Job from './Job';
import setJobsAction from '../redux/actions/setJobs';

const StarredJobs = () => {
  const jobs = useSelector(state => state.jobs);
  const dispatch = useDispatch();
  const [isDownloaded, setDownloaded] = useState(false);
  const [isDownloading, setDownloading] = useState(false);

  useEffect(() => {
    if (isDownloaded !== true && isDownloading !== true) {
      setDownloading(true);
      dispatch(clearJobsAction());
      fetchStarredJobs().then(jobs => {
        dispatch(setJobsAction(jobs));
        setDownloaded(true);
        setDownloading(false);
      });
    }
  });

  return (
    <React.Fragment>
      <h1>Starred Jobs</h1>
      {jobs.map(job => <Job key={job.jobId} job={job}/>)}
    </React.Fragment>
  );
};

export default StarredJobs;

import React, { useState, useEffect } from 'react';
import { useDispatch } from 'react-redux';
import clearJobsAction from '../redux/actions/clearJobs';
import { fetchJobs } from '../utils/api';
import setJobsAction from '../redux/actions/setJobs';

function useAllJobsData() {
  const dispatch = useDispatch();
  const [isDownloaded, setDownloaded] = useState(false);
  const [isDownloading, setDownloading] = useState(false);

  useEffect(() => {
    if (isDownloaded !== true && isDownloading !== true) {
      setDownloading(true);
      dispatch(clearJobsAction());
      fetchJobs().then((jobs) => {
        dispatch(setJobsAction(jobs));
        setDownloaded(true);
        setDownloading(false);
      });
    }
  }, [isDownloaded, isDownloading, dispatch]);
}

export default useAllJobsData;

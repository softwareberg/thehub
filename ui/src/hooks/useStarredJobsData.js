import React, {useEffect, useState} from 'react'
import clearJobsAction from '../redux/actions/clearJobs';
import { fetchStarredJobs } from '../utils/api';
import setJobsAction from '../redux/actions/setJobs';
import { useDispatch } from 'react-redux';

function useStarredJobsData() {
  const dispatch = useDispatch();
  const [isDownloaded, setDownloaded] = useState(false);
  const [isDownloading, setDownloading] = useState(false);

  useEffect(() => {
    if (isDownloaded !== true && isDownloading !== true) {
      setDownloading(true);
      dispatch(clearJobsAction());
      fetchStarredJobs().then((jobs) => {
        dispatch(setJobsAction(jobs));
        setDownloaded(true);
        setDownloading(false);
      });
    }
  }, [isDownloaded, isDownloading, dispatch]);
}

export default useStarredJobsData;

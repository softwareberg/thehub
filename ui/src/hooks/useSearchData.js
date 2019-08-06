import React, {useEffect, useState} from 'react'
import clearJobsAction from '../redux/actions/clearJobs';
import { findJobs } from '../utils/api';
import setJobsAction from '../redux/actions/setJobs';
import { useDispatch } from 'react-redux';

function useSearchData(query) {
  const [isDownloaded, setDownloaded] = useState(false);
  const [isDownloading, setDownloading] = useState(false);
  const dispatch = useDispatch();
  const [lastQuery, setLastQuery] = useState('');

  if (isDownloading !== true && lastQuery !== query) {
    setDownloaded(false);
    setLastQuery(query);
  }

  useEffect(() => {
    if (isDownloaded !== true && isDownloading !== true) {
      setDownloading(true);
      dispatch(clearJobsAction());
      if (lastQuery.length > 0) {
        findJobs(lastQuery)
          .then((jobs) => {
            dispatch(setJobsAction(jobs));
            setDownloaded(true);
            setDownloading(false);
          });
      }
    }
  }, [isDownloaded, isDownloading, dispatch, lastQuery]);
}

export default useSearchData;

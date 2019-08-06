import React, {useEffect, useState} from 'react'
import clearJobsAction from '../redux/actions/clearJobs';
import { findJobsByKeyword } from '../utils/api';
import setJobsAction from '../redux/actions/setJobs';
import { useDispatch } from 'react-redux';

function useSearchByKeywordData(query) {
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
        findJobsByKeyword(lastQuery)
          .then((jobs) => {
            dispatch(setJobsAction(jobs));
            setDownloaded(true);
            setDownloading(false);
          });
      }
    }
  }, [isDownloaded, isDownloading, dispatch, lastQuery]);
}

export default useSearchByKeywordData;

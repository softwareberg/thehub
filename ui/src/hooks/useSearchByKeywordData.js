import clearJobsAction from 'redux/actions/clearJobs';
import { findJobsByKeyword } from 'utils/api';
import setJobsAction from 'redux/actions/setJobs';
import { useDispatch } from 'react-redux';
import { useEffect, useState } from 'react';

function useSearchByKeywordData(query) {
  const dispatch = useDispatch();
  const [isDownloading, setDownloading] = useState(false);
  const [isDownloaded, setDownloaded] = useState(false);
  const [lastQuery, setLastQuery] = useState('');

  if (isDownloading !== true && lastQuery !== query) {
    setDownloaded(false);
    setLastQuery(query);
  }

  useEffect(() => {
    const showAlert = () => window.alert("Error occurred while downloading jobs!");
    async function fetchData() {
      if (isDownloaded !== true && isDownloading !== true) {
        setDownloading(true);
        dispatch(clearJobsAction());
        if (lastQuery.length > 0) {
          const jobs = await findJobsByKeyword(lastQuery)
            .catch(() => showAlert());
          dispatch(setJobsAction(jobs));
          setDownloaded(true);
          setDownloading(false);
        }
      }
    }
    fetchData();
  }, [isDownloaded, isDownloading, dispatch, lastQuery]);
}

export default useSearchByKeywordData;

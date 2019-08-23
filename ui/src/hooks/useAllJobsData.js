import clearJobsAction from 'redux/actions/clearJobs';
import { fetchJobs } from 'utils/api';
import setJobsAction from 'redux/actions/setJobs';
import { showAlert } from 'utils/commons';
import setPaginationAction from 'redux/actions/setPagination';
import { useDispatch } from 'react-redux';
import { useEffect, useState } from 'react';

function useAllJobsData(page) {
  const dispatch = useDispatch();
  const [isDownloading, setDownloading] = useState(false);
  const [isDownloaded, setDownloaded] = useState(false);
  const [lastQuery, setLastQuery] = useState(page);

  if (isDownloading !== true && lastQuery !== page) {
    setDownloaded(false);
    setLastQuery(page);
  }

  useEffect(() => {
    async function fetchData() {
      if (isDownloaded !== true && isDownloading !== true) {
        setDownloading(true);
        dispatch(clearJobsAction());
        const data = await fetchJobs(lastQuery)
          .catch(() => showAlert('Error occurred while downloading jobs!'));
        const { jobs, pagination } = data;
        dispatch(setJobsAction(jobs));
        dispatch(setPaginationAction(pagination));
        setDownloaded(true);
        setDownloading(false);
      }
    }
    fetchData();
  }, [isDownloaded, isDownloading, dispatch, lastQuery]);
}

export default useAllJobsData;

import clearJobsAction from 'redux/actions/clearJobs';
import { fetchJobs } from 'utils/api';
import setJobsAction from 'redux/actions/setJobs';
import { useDispatch } from 'react-redux';
import { useEffect, useState } from 'react';

function useAllJobsData() {
  const dispatch = useDispatch();
  const [isDownloading, setDownloading] = useState(false);
  const [isDownloaded, setDownloaded] = useState(false);

  useEffect(() => {
    const showAlert = () => window.alert("Error occurred while downloading jobs!");
    async function fetchData() {
      if (isDownloaded !== true && isDownloading !== true) {
        setDownloading(true);
        dispatch(clearJobsAction());
        const jobs = await fetchJobs()
          .catch(() => showAlert());
        dispatch(setJobsAction(jobs));
        setDownloaded(true);
        setDownloading(false);
      }
    }
    fetchData();
  }, [isDownloaded, isDownloading, dispatch]);
}

export default useAllJobsData;

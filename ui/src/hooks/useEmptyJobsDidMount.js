import clearJobsAction from 'redux/actions/clearJobs';
import { useDispatch } from 'react-redux';
import { useState } from 'react';

function useEmptyJobsDidMount() {
  const dispatch = useDispatch();
  const [isInitialized, setIsInitialized] = useState(false);

  if (isInitialized !== true) {
    dispatch(clearJobsAction());
    setIsInitialized(true);
  }
}

export default useEmptyJobsDidMount;

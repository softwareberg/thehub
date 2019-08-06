import React, { useState } from 'react';
import clearJobsAction from '../redux/actions/clearJobs';
import { useDispatch } from 'react-redux';

function useEmptyJobsDidMount() {
  const dispatch = useDispatch();
  const [isInitialized, setIsInitialized] = useState(false);

  if (isInitialized !== true) {
    dispatch(clearJobsAction());
    setIsInitialized(true);
  }
}

export default useEmptyJobsDidMount;

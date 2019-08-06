import React, { useState } from 'react';
import clearJobsAction from '../redux/actions/clearJobs';
import { useDispatch } from 'react-redux';

function useEmptyJobsDidMount() {
  const dispatch = useDispatch();
  const [initialized, setInitialized] = useState(false);

  if (initialized !== true) {
    dispatch(clearJobsAction());
    setInitialized(true);
  }
}

export default useEmptyJobsDidMount;

import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import clearJobsAction from '../redux/actions/clearJobs';

function useEmptyJobsDidMount() {
  const [initialized, setInitialized] = useState(false);
  const dispatch = useDispatch();

  if (initialized !== true) {
    dispatch(clearJobsAction());
    setInitialized(true);
  }
}

export default useEmptyJobsDidMount;

import {CLEAR_JOBS, DELETE_JOB, APPEND_JOBS} from '../actions';

const initialState = new Set();

const jobsIds = (state = initialState, action) => {
  let newState;
  switch (action.type) {
    case (APPEND_JOBS):
      newState = new Set(state);
      action.jobs.reduce((acc, job) => (acc.add(job.jobId)), newState);
      return newState;

    case (CLEAR_JOBS):
      return initialState;

    case (DELETE_JOB):
      newState = new Set(state);
      newState.delete(action.jobId);
      return newState;

    default:
      return state;
  }
};

export default jobsIds;

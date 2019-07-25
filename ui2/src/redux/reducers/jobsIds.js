import {CLEAR_JOBS, DELETE_JOB, APPEND_JOBS} from '../actions';
import {REDUX_JOBS_LIMIT} from '../conf';

// TODO DEBUG hardcoded jobs
const initialState = new Set(['product-manager-9', 'product-manager-99']);

const jobsIds = (state = initialState, action) => {
  let newState;
  switch (action.type) {
    case (APPEND_JOBS):
      if (state.size > REDUX_JOBS_LIMIT) {
        newState = initialState;
      } else {
        newState = new Set(state);
      }
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

import {CLEAR_JOBS, DELETE_JOB, SET_JOBS, SET_STAR, SET_UNWRAP} from '../actionsTypes';

const initialState = [];

const setUnwrap = (job, jobId, isUnwrapped) => {
  if (job.jobId === jobId) {
    return {
      ...job,
      description: {
        ...job.description,
        isUnwrapped: isUnwrapped
      }
    };
  }

  return job;
};

const setStar = (job, jobId, hasStar) => {
  if (job.jobId === jobId) {
    return {
      ...job,
      hasStar: hasStar
    };
  }

  return job;
};

const jobs = (state = initialState, action) => {
  switch (action.type) {
    case (SET_JOBS):
      return action.jobs;

    case (CLEAR_JOBS):
      return initialState;

    case (DELETE_JOB):
      return state.filter((job) => (job.jobId !== action.jobId));

    case (SET_UNWRAP):
      return state.map((job) => (setUnwrap(job, action.jobId, action.isUnwrapped)));

    case (SET_STAR):
      return state.map((job) => (setStar(job, action.jobId, action.hasStar)));

    default:
      return state;
  }
};

export default jobs;

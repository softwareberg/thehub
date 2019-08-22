import { SET_JOBS } from 'redux/actionsTypes';

export const setJobs = jobs => ({
  type: SET_JOBS,
  jobs: (jobs === undefined || jobs === null) ? [] : jobs
});
export default setJobs;

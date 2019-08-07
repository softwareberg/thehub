import { SET_JOBS } from 'redux/actionsTypes';

export const setJobs = jobs => ({
  type: SET_JOBS,
  jobs
});
export default setJobs;

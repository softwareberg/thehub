import { DELETE_JOB } from 'redux/actionsTypes';

export const deleteJob = jobId => ({
  type: DELETE_JOB,
  jobId
});
export default deleteJob;

import { DELETE_JOB } from '../actionsTypes';

export const deleteJob = jobId => ({
  type: DELETE_JOB,
  jobId
});
export default deleteJob;

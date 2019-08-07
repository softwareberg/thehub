import { SET_STAR } from 'redux/actionsTypes';

export const setStar = (jobId, hasStar) => ({
  type: SET_STAR,
  jobId,
  hasStar
});
export default setStar;

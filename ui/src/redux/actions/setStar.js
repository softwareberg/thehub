import { SET_STAR } from '../actionsTypes';

export const setStar = (jobId, hasStar) => ({
  type: SET_STAR,
  jobId,
  hasStar
});
export default setStar;

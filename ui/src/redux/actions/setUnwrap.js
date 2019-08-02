import { SET_UNWRAP } from '../actionsTypes';

export const setUnwrap = (jobId, isUnwrapped) => ({
  type: SET_UNWRAP,
  jobId,
  isUnwrapped
});
export default setUnwrap;

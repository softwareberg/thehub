import { SET_UNWRAP } from 'redux/actionsTypes';

export const setUnwrap = (jobId, isUnwrapped) => ({
  type: SET_UNWRAP,
  jobId,
  isUnwrapped
});
export default setUnwrap;

import { SET_PAGINATION } from 'redux/actionsTypes';

export const setPagination = pagination => ({
  type: SET_PAGINATION,
  pagination
});
export default setPagination;

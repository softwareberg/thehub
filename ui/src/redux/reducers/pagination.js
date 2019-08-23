import { CLEAR_PAGINATION, SET_PAGE, SET_PAGINATION } from 'redux/actionsTypes';

const initialState = {
  page: 1, // counting from 1
  totalPages: 1
};

const pagination = (state = initialState, action) => {
  switch (action.type) {
    case (SET_PAGINATION):
      return action.pagination;

    case (SET_PAGE):
      return {
        ...state,
        page: action.page
      };

    case (CLEAR_PAGINATION):
      return initialState;

    default:
      return state;
  }
};

export default pagination;

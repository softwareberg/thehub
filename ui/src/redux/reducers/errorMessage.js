import { SET_ERROR_MESSAGE, CLEAR_ERROR_MESSAGE } from 'redux/actionsTypes';

const initialState = {
  msg: "",
  counter: 0
};

const errorMessage = (state = initialState, action) => {
  switch (action.type) {
    case (SET_ERROR_MESSAGE):
      return {
        msg: action.errorMessage,
        counter: 1 - state.counter
      };

    case (CLEAR_ERROR_MESSAGE):
      return {
        ...initialState,
        counter: 1 - state.counter
      };

    default:
      return state;
  }
};

export default errorMessage;

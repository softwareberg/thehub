import {SET_X} from '../actions';

const initialState = true;

const x = (state = initialState, action) => {
  switch (action.type) {
    case (SET_X):
      return action.x;

    default:
      return state;
  }
};

export default x;

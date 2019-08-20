import { combineReducers } from 'redux';
import jobs from 'redux/reducers/jobs';
import errorMessage from 'redux/reducers/errorMessage';

export default combineReducers({
  errorMessage,
  jobs
});

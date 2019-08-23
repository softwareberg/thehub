import { combineReducers } from 'redux';
import jobs from 'redux/reducers/jobs';
import pagination from 'redux/reducers/pagination';

export default combineReducers({
  jobs,
  pagination
});

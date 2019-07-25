import {combineReducers} from 'redux'
import jobs from './reducers/jobs'
import jobsIds from './reducers/jobsIds';

export default combineReducers({
  jobs: jobs,
  jobsIds: jobsIds,
});

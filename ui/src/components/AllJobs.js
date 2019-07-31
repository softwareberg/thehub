import React, {Component} from 'react';
import {CLEAR_JOBS, SET_JOBS} from '../redux/actions';
import {connect} from 'react-redux';
import Job from './Job';
import {fetchJobs} from '../utils/api';

class AllJobs extends Component {
  componentDidMount() {
    this.props.dispatch({type: CLEAR_JOBS});
    this.downloadJobs();
  }

  render() {
    const jobs = this.props.jobs;
    return (
      <React.Fragment>
        <h1>All Jobs</h1>
        {jobs.map(job => <Job key={job.jobId} job={job}/>)}
      </React.Fragment>
    );
  }

  downloadJobs() {
    fetchJobs().then(jobs => {
      this.props.dispatch({
        type: SET_JOBS,
        jobs: jobs
      });
    })
  }
}

const mapStateToProps = state => ({jobs: state.jobs});

export default connect(mapStateToProps)(AllJobs);

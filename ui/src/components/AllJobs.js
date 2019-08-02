import React, {Component} from 'react';
import clearJobs from '../redux/actions/clearJobs';
import {connect} from 'react-redux';
import {fetchJobs} from '../utils/api';
import Job from './Job';
import setJobs from '../redux/actions/setJobs';

class AllJobs extends Component {
  componentDidMount() {
    this.props.dispatch(clearJobs());
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
      this.props.dispatch(setJobs(jobs));
    })
  }
}

const mapStateToProps = state => ({jobs: state.jobs});

export default connect(mapStateToProps)(AllJobs);

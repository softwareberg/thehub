import React, {Component} from 'react';
import {APPEND_JOBS} from '../redux/actions';
import {connect} from 'react-redux';
import Job from './Job';
import {fetchJobs} from '../utils/api';

class AllJobs extends Component {
  componentDidMount() {
    this.downloadJobs();
  }

  render() {
    const jobs = this.props.jobs;
    return (
      <React.Fragment>
        <h1>All Jobs</h1>
        {jobs.map((job) => <Job job={job}/>)}
      </React.Fragment>
    );
  }

  downloadJobs() {
    const jobsIds = this.props.jobsIds;
    fetchJobs().then((jobs) => {
      this.props.dispatch({
        type: APPEND_JOBS,
        jobs: jobs.filter((job) => (!jobsIds.has(job.jobId)))
      });
    })
  }
}

const mapStateToProps = (state) => ({
  jobs: state.jobs,
  jobsIds: state.jobsIds
});

export default connect(mapStateToProps)(AllJobs);

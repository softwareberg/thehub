import React, {Component} from 'react';
import {APPEND_JOBS} from '../redux/actions';
import {connect} from 'react-redux';
import {fetchStarredJobs} from '../utils/api';
import Job from './Job';

class StarredJobs extends Component {
  componentDidMount() {
    this.downloadJobs();
  }

  render() {
    const jobs = this.props.jobs;
    return (
      <React.Fragment>
        <h1>Starred Jobs</h1>
        {jobs.map((job) => <Job job={job}/>)}
      </React.Fragment>
    );
  }

  downloadJobs() {
    const jobsIds = this.props.jobsIds;
    fetchStarredJobs().then((jobs) => {
      this.props.dispatch({
        type: APPEND_JOBS,
        jobs: jobs.filter((job) => (!jobsIds.has(job.jobId)))
      });
    })
  }
}

const mapStateToProps = (state) => ({
  jobs: state.jobs.filter((job) => job.hasStar),
  jobsIds: state.jobsIds
});

export default connect(mapStateToProps)(StarredJobs);

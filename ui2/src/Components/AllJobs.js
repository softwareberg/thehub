import React, {Component} from 'react';
import {fetchJobs} from "../utils/api";
import {connect} from 'react-redux';
import {CLEAR_JOBS, SET_JOBS} from '../redux/actions';
import Job from './Job';

class AllJobs extends Component {
  refreshJobs() {
    this.props.dispatch({type: CLEAR_JOBS});

    fetchJobs().then((jobs) => {
      console.debug(jobs); // TODO DEBUG

      this.props.dispatch({
        type: SET_JOBS,
        jobs: jobs
      });
    })
  }

  componentDidMount() {
    this.refreshJobs();
  }

  render() {
    const jobs = this.props.jobs;
    const renderedJobs = jobs.map((job) => <Job job={job}/>);

    return (
      <React.Fragment>
        <h1>All Jobs</h1>
        {renderedJobs}
      </React.Fragment>
    );
  }
}

const mapStateToProps = (state) => ({
  jobs: state.jobs,
});

export default connect(mapStateToProps)(AllJobs);

import React from 'react';
import {connect} from 'react-redux';
import Job from './Job';

const AllJobs = ({jobs, ...props}) => (
  <React.Fragment>
    <header><h1>All Jobs</h1></header>
    {jobs.map((job) => <Job key={job.jobId} job={job}/>)}
  </React.Fragment>
);

const mapStateToProps = (state) => ({
  jobs: state.jobs,
});

export default connect(mapStateToProps)(AllJobs);

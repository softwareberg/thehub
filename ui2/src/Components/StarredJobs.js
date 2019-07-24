import React from 'react';
import {connect} from 'react-redux';
import Job from './Job';

const StarredJobs = ({jobs, ...props}) => (
  <React.Fragment>
    <header><h1>Starred Jobs</h1></header>
    {jobs.map((job) => <Job key={job.jobId} job={job}/>)}
  </React.Fragment>
);

const mapStateToProps = (state) => ({
  jobs: state.jobs.filter((job) => job.hasStar),
});

export default connect(mapStateToProps)(StarredJobs);

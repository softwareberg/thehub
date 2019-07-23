import React, {Component} from 'react';
import {fetchJobs} from "../utils/api";

class AllJobs extends Component {
  refreshJobs() {
    fetchJobs().then((jobs) => {
      // TODO save in redux
      console.debug(jobs)
    })
  }

  componentDidMount() {
    this.refreshJobs();
  }

  render() {
    return (
      <h1>All Jobs</h1>
    );
  }
}

export default AllJobs;

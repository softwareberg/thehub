import { startJob, fetchJobs, findJobsByKeyword, fetchStarredJobs, findJobs, deleteJob } from 'api';
import { location } from '@hyperapp/router';

export const actions = {
  jobs: {
    toggleStart: jobId => (state) => {
      const newState = Object.assign({}, state);
      const toggleStar = !newState.byId[jobId].hasStar;
      startJob(jobId, toggleStar);
      newState.byId[jobId].hasStar = toggleStar;
      return newState;
    },
    toggleDescriptionUnwrap: jobId => (state) => {
      const newState = Object.assign({}, state);
      newState.byId[jobId].description.isUnwrapped = !newState.byId[jobId].description.isUnwrapped;
      return newState;
    },
    deleteJob: jobId => (state) => {
      deleteJob(jobId);
      return {
        ...state,
        display: state.display.filter(currentJobId => currentJobId !== jobId)
      };
    },
    fetchJobs: () => (state, actions) => {
      fetchJobs()
        .then((jobs) => {
          actions.addJobs(jobs);
          actions.setDisplayJobs(jobs);
          return jobs;
        });
    },
    fetchStarred: () => (state, actions) => {
      fetchStarredJobs()
        .then(actions.addJobs);
    },
    searchByKeyword: keyword => (state, actions) => {
      findJobsByKeyword(keyword)
        .then((jobs) => {
          actions.addJobs(jobs);
          actions.setDisplayJobs(jobs);
          return jobs;
        });
    },
    search: q => (state, actions) => {
      findJobs(q)
        .then((jobs) => {
          actions.addJobs(jobs);
          actions.setDisplayJobs(jobs);
          return jobs;
        });
    },
    addJobs: jobs => (state) => {
      const newState = Object.assign({}, state);
      jobs.forEach((job) => {
        if (!newState.byId[job.jobId]) {
          newState.byId[job.jobId] = job;
        }
      });
      return newState;
    },
    setDisplayJobs: jobs => state => (
      {
        ...state,
        display: jobs.map(job => job.jobId)
      }
    ),
    resetDisplayJobs: () => state => (
      {
        ...state,
        display: []
      }
    )
  },
  location: location.actions,
  getState: () => state => state
};

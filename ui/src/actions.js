import { startJob, fetchJobs, findJobsByKeyword, fetchStarredJobs, findJobs } from 'api';
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
    deleteJob: jobId => () => (
      console.warn(`Not implemented ${jobId}`) // eslint-disable-line
    ),
    fetchJobs: () => (state, actions) => {
      fetchJobs()
        .then((jobs) => {
          actions.addJobs(jobs);
          actions.setJobsList(jobs);
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
          actions.setJobsSearch(jobs);
          return jobs;
        });
    },
    search: q => (state, actions) => {
      findJobs(q)
        .then((jobs) => {
          actions.addJobs(jobs);
          actions.setJobsSearch(jobs);
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
    setJobsSearch: jobs => state => (
      {
        ...state,
        search: jobs.map(job => job.jobId)
      }
    ),
    setJobsList: jobs => state => (
      {
        ...state,
        list: jobs.map(job => job.jobId)
      }
    ),
    resetSearchResult: () => state => (
      {
        ...state,
        search: []
      }
    )
  },
  location: location.actions,
  getState: () => state => state
};

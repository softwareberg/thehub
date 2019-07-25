import {CLEAR_JOBS, DELETE_JOB, APPEND_JOBS, SET_STAR, SET_UNWRAP} from '../actions';
import {REDUX_JOBS_LIMIT} from '../conf';

// TODO DEBUG hardcoded jobs
const initialState = [
  {
    jobId: 'product-manager-9',
    title: 'Product Manager',
    description: {
      isUnwrapped: false,
      value: 'We are looking for someone in Oslo who will help us with: Taking care of our customers Being the project lead on new products Informing the tech and marketing team on the customer\'s needs Assisting sales as a voice for the tech and product teams  Leading improvements on the website Performing market research on new ideas  Assisting wherever else needed, this is a start up after all  ;)'
    },
    hasStar: false,
    keywords: ['sustainability','project management','product management','food waste'],
    href: 'https://hub.no/jobs/product-manager-9',
  },
  {
    jobId: 'product-manager-99',
    title: 'Product Manager',
    description: {
      isUnwrapped: false,
      value: 'We are looking for someone in Oslo who will help us with: Taking care of our customers Being the project lead on new products Informing the tech and marketing team on the customer\'s needs Assisting sales as a voice for the tech and product teams  Leading improvements on the website Performing market research on new ideas  Assisting wherever else needed, this is a start up after all  ;)'
    },
    hasStar: false,
    keywords: ['sustainability','project management','product management','food waste'],
    href: 'https://hub.no/jobs/product-manager-9',
  }
];

const setUnwrap = (job, jobId, isUnwrapped) => {
  if (job.jobId === jobId) {
    return {
      ...job,
      description: {
        ...job.description,
        isUnwrapped: isUnwrapped
      }
    };
  }

  return job;
};

const setStar = (job, jobId, hasStar) => {
  if (job.jobId === jobId) {
    return {
      ...job,
      hasStar: hasStar
    };
  }

  return job;
};

const jobs = (state = initialState, action) => {
  switch (action.type) {
    case (APPEND_JOBS):
      if (state.size > REDUX_JOBS_LIMIT) {
        return action.jobs;
      } else {
        return [].concat(state, action.jobs);
      }

    case (CLEAR_JOBS):
      return initialState;

    case (DELETE_JOB):
      return state.filter((job) => (job.jobId !== action.jobId));

    case (SET_UNWRAP):
      return state.map((job) => (setUnwrap(job, action.jobId, action.isUnwrapped)));

    case (SET_STAR):
      return state.map((job) => (setStar(job, action.jobId, action.hasStar)));

    default:
      return state;
  }
};

export default jobs;

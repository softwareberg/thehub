import {CLEAR_JOBS, SET_JOBS} from '../actions';

// TODO DEBUG hardcoded job
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
  }
];

const jobs = (state = initialState, action) => {
  switch (action.type) {
    case (SET_JOBS):
      return action.jobs;

    case (CLEAR_JOBS):
      return initialState;

    default:
      return state;
  }
};

export default jobs;

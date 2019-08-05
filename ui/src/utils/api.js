const transformJob = ({ jobId, title, description, hasStar, keywords, href }) => ({
    jobId,
    title: title.replace(/&amp;/g, '&'),
    description: {
      isUnwrapped: false,
      value: description.replace(/&nbsp;/g, '').replace(/&amp;/g, '&'),
    },
    hasStar,
    keywords,
    href
  }
);

const prefix = '/api';

export const fetchJobs = () => (
  fetch(prefix + '/jobs?size=100')
    .then(response => response.json())
    .then(jobs => jobs.data.map(transformJob))
);

export const fetchStarredJobs = () => (
  fetch(prefix +'/jobs?hasStar=true')
    .then(response => response.json())
    .then(jobs => jobs.data.map(transformJob))
);

export const findJobsByKeyword = (keyword) => (
  fetch(prefix + `/jobs?keyword=${keyword}&size=100`)
    .then(response => response.json())
    .then(jobs => jobs.data.map(transformJob))
);

export const findJobs = (q) => (
  fetch(prefix + `/jobs?q=${q}&size=100`)
    .then(response => response.json())
    .then(jobs => jobs.data.map(transformJob))
);

export const deleteJob = (jobId) => (
  fetch(prefix + `/jobs/${jobId}`, {method: 'DELETE'})
);

export const startJob = (jobId, hasStar) => (
  fetch(prefix + `/jobs/${jobId}`, {
    method: 'PATCH',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({hasStar})
  })
);

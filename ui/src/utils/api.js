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

const fetchWithAbort = (url, signal = undefined, options = {}) => {
  if (signal !== undefined && signal !== null) {
    options = {
      ...options,
      signal
    }
  }

  return fetch(url, options);
};

export const fetchJobs = (signal = undefined) => (
  fetchWithAbort(prefix + '/jobs?size=100', signal)
    .then(response => response.json())
    .then(jobs => jobs.data.map(transformJob))
);

export const fetchStarredJobs = (signal = undefined) => (
  fetchWithAbort(prefix +'/jobs?hasStar=true', signal)
    .then(response => response.json())
    .then(jobs => jobs.data.map(transformJob))
);

export const findJobsByKeyword = (keyword, signal = undefined) => (
  fetchWithAbort(prefix + `/jobs?keyword=${keyword}&size=100`, signal)
    .then(response => response.json())
    .then(jobs => jobs.data.map(transformJob))
);

export const findJobs = (q, signal = undefined) => (
  fetchWithAbort(prefix + `/jobs?q=${q}&size=100`, signal)
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

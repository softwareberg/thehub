const transformJob = ({ jobId, title, description, hasStar, keywords, href, logo, poster }) => ({
  jobId,
  title: title.replace(/&amp;/g, '&'),
  description: {
    isUnwrapped: false,
    value: description.replace(/&nbsp;/g, '').replace(/&amp;/g, '&')
  },
  hasStar,
  keywords,
  href,
  logo,
  poster
}
);

const transformPagination = ({ number, totalPages }) => ({
  page: number,
  totalPages
});

const prefix = '/api';

const handleHttpError = (response) => {
  if (response.ok !== true) {
    throw new Error(`http response: ${response.status}`);
  }
  return response;
};

export const fetchJobs = (page = 1) => (
  fetch(`${prefix}/jobs?size=100&page=${page}`)
    .then(handleHttpError)
    .then(response => response.json())
    .then(jobs => ({
      jobs: jobs.data.map(transformJob),
      pagination: transformPagination(jobs)
    }))
);

export const fetchStarredJobs = () => (
  fetch(`${prefix}/jobs?hasStar=true`)
    .then(handleHttpError)
    .then(response => response.json())
    .then(jobs => jobs.data.map(transformJob))
);

export const findJobsByKeyword = keyword => (
  fetch(`${prefix}/jobs?keyword=${keyword}&size=100`)
    .then(handleHttpError)
    .then(response => response.json())
    .then(jobs => jobs.data.map(transformJob))
);

export const findJobs = q => (
  fetch(`${prefix}/jobs?q=${q}&size=100`)
    .then(handleHttpError)
    .then(response => response.json())
    .then(jobs => jobs.data.map(transformJob))
);

export const deleteJob = jobId => (
  fetch(`${prefix}/jobs/${jobId}`, { method: 'DELETE' })
    .then(handleHttpError)
);

export const starJob = (jobId, hasStar) => (
  fetch(`${prefix}/jobs/${jobId}`, {
    method: 'PATCH',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ hasStar })
  })
    .then(handleHttpError)
);

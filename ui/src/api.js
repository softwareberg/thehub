function transformJob({ jobId, title, description, hasStar, keywords, href }) {
  return {
    jobId,
    title: title.replace(/&amp;/g, '&'),
    description: {
      isUnwrapped: true,
      value: description.replace(/&nbsp;/g, '').replace(/&amp;/g, '&')
    },
    hasStar,
    keywords,
    href
  };
}

export function fetchJobs() {
  return fetch('/api/jobs?size=100')
    .then(response => response.json())
    .then(jobs => jobs.data.map(transformJob));
}

export function fetchStarredJobs() {
  return fetch('/api/jobs?hasStar=true')
    .then(response => response.json())
    .then(jobs => jobs.data.map(transformJob));
}

export function findJobsByKeyword(keyword) {
  return fetch(`/api/jobs?keyword=${keyword}&size=100`)
    .then(response => response.json())
    .then(jobs => jobs.data.map(transformJob));
}

export function findJobs(q) {
  return fetch(`/api/jobs?q=${q}&size=100`)
    .then(response => response.json())
    .then(jobs => jobs.data.map(transformJob));
}

export function deleteJob(jobId) {
  return fetch(`/api/jobs/${jobId}`, { method: 'DELETE' });
}

export function startJob(jobId, hasStar) {
  return fetch(`/api/jobs/${jobId}`, {
    method: 'PATCH',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ hasStar })
  });
}

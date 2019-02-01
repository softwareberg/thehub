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
  return fetch('/api/jobs?pageSize=100')
    .then(response => response.json())
    .then(jobs => jobs.data.map(transformJob));
}

export function fetchStarredJobs() {
  return fetch('/api/jobs?hasStar=true')
    .then(response => response.json())
    .then(jobs => jobs.data.map(transformJob));
}

export function findJobsByKeyword(keyword) {
  return fetch(`/api/jobs?keyword=${keyword}&pageSize=100`)
    .then(response => response.json())
    .then(jobs => jobs.data.map(transformJob));
}

export function findJobs(q) {
  return fetch(`/api/jobs?q=${q}&pageSize=100`)
    .then(response => response.json())
    .then(jobs => jobs.data.map(transformJob));
}

export function deleteJob() {
}

export function startJob(jobId, hasStar) {
  return fetch(`/api/jobs/${jobId}`, { method: 'PATCH', body: JSON.stringify({ hasStar }) });
}

import React, { useState, useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import useAllJobsData from 'hooks/useAllJobsData';
import useEmptyJobsDidMount from 'hooks/useEmptyJobsDidMount';
import Job from 'components/Job';
import Pagination from './Pagination';
import setPage from '../redux/actions/setPage';

const AllJobs = ({ history, match}) => {
  const jobs = useSelector(state => state.jobs);
  const pagination = useSelector(state => state.pagination);
  const [isRestoredPage, setIsRestoredPage] = useState(false);
  const { page } = pagination;
  const dispatch = useDispatch();

  useEffect(() => {
    if (match.params.page !== pagination.page) {
      if (!isRestoredPage) {
        dispatch(setPage(match.params.page));
      } else {
        history.push(`/all/${pagination.page}`);
      }
    }
    setIsRestoredPage(true);
  }, [isRestoredPage, match.params.page, pagination.page, dispatch]);

  useEmptyJobsDidMount();
  useAllJobsData(page);

  return (
    <React.Fragment>
      <h1>All Jobs</h1>
      <Pagination />
      {jobs.map(job => <Job key={job.jobId} job={job} />)}
      {jobs.length > 0 && <Pagination />}
    </React.Fragment>
  );
};

export default AllJobs;

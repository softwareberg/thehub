import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import Form from 'react-bootstrap/Form';
import clearJobsAction from '../redux/actions/clearJobs';
import { findJobs } from '../utils/api';
import Job from './Job';
import setJobsAction from '../redux/actions/setJobs';

const Search = ({ history, ...props }) => {
  const jobs = useSelector(state => state.jobs);
  const dispatch = useDispatch();
  const [inputText, setInputText] = useState(props.match.params.searchText ? decodeURIComponent(props.match.params.searchText) : '');
  const [searchText, setSearchText] = useState(props.match.params.searchText ? decodeURIComponent(props.match.params.searchText) : '');
  const [isDownloaded, setDownloaded] = useState(false);
  const [isDownloading, setDownloading] = useState(false);

  function handleChange(e) {
    const nextSearchText = e.target.value;
    setInputText(nextSearchText);
  }

  function handleEnter() {
    const nextSearchText = inputText;
    setSearchText(nextSearchText);
    history.push(`/search/${encodeURIComponent(nextSearchText)}`);
    dispatch(clearJobsAction());
    setDownloaded(false);
  }

  useEffect(() => {
    if (isDownloaded !== true && isDownloading !== true) {
      setDownloading(true);
      dispatch(clearJobsAction());
      if (searchText.length > 0) {
        findJobs(searchText)
          .then((jobs) => {
            dispatch(setJobsAction(jobs));
            setDownloaded(true);
            setDownloading(false);
          });
      }
    }
  }, [isDownloaded, isDownloading, dispatch, searchText]);

  return (
    <React.Fragment>
      <h1>Search Jobs</h1>
      <SearchInput
        inputText={inputText}
        handleChange={handleChange}
        handleEnter={handleEnter}
      />
      {jobs.map(job => <Job key={job.jobId} job={job} />)}
    </React.Fragment>
  );
};

const SearchInput = ({ inputText, handleChange, handleEnter, ...props }) => (
  <Form onSubmit={(e) => { e.preventDefault(); handleEnter(); }}>
    <Form.Control
      placeholder="Type and press enter..."
      autoFocus
      size="lg"
      type="text"
      value={inputText}
      onChange={handleChange}
      style={{ marginBottom: 8 }}
    />
  </Form>
);

export default Search;

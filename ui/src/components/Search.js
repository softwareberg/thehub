import React, {useEffect, useState} from 'react';
import clearJobsAction from '../redux/actions/clearJobs';
import { useDispatch, useSelector } from 'react-redux';
import {findJobs} from '../utils/api';
import Form from 'react-bootstrap/Form';
import Job from './Job';
import setJobsAction from '../redux/actions/setJobs';

const Search = ({history, ...props}) => {
  const jobs = useSelector(state => state.jobs);
  const dispatch = useDispatch();
  const [inputText, setInputText] = useState(this.props.match.params.searchText ? decodeURIComponent(this.props.match.params.searchText) : '');
  const [searchText, setSearchText] = useState(this.props.match.params.searchText ? decodeURIComponent(this.props.match.params.searchText) : '');
  const [isDownloaded, setDownloaded] = useState(false);

  function handleChange(e) {
    const nextSearchText = e.target.value;
    setInputText(nextSearchText);
  }

  function handleEnter() {
    const nextSearchText = inputText;
    setSearchText(nextSearchText);
    history.push('/search/' + encodeURIComponent(nextSearchText));
    dispatch(clearJobsAction());
    setDownloaded(false);
  }

  useEffect(() => {
    if (isDownloaded !== true && searchText.length > 0) {
      const controller = new AbortController();
      findJobs(searchText, controller.signal)
        .then(jobs => {
          dispatch(setJobsAction(jobs));
          setDownloaded(true);
        });
      return () => controller.abort();
    }
  });

  return (
    <React.Fragment>
      <h1>Search Jobs</h1>
      <SearchInput
        inputText={inputText}
        handleChange={handleChange}
        handleEnter={handleEnter}
      />
      {jobs.map(job => <Job key={job.jobId} job={job}/>)}
    </React.Fragment>
  );
};

const SearchInput = ({inputText, handleChange, handleEnter, ...props}) => (
  <Form onSubmit={e => {e.preventDefault(); handleEnter()}}>
    <Form.Control
      placeholder='Type and press enter...'
      autoFocus
      size="lg"
      type="text"
      value={inputText}
      onChange={handleChange}
      style={{marginBottom: 8}}
    />
  </Form>
);

export default Search;

import React, { useState } from 'react';
import { useSelector } from 'react-redux';
import Form from 'react-bootstrap/Form';
import Job from 'components/Job';
import useSearchByKeywordData from 'hooks/useSearchByKeywordData';
import useEmptyJobsDidMount from 'hooks/useEmptyJobsDidMount';

const SearchByKeyword = ({ history, match }) => {
  const jobs = useSelector(state => state.jobs);
  const [inputText, setInputText] = useState(match.params.searchText ? decodeURIComponent(match.params.searchText) : '');
  const [searchText, setSearchText] = useState(match.params.searchText ? decodeURIComponent(match.params.searchText) : '');

  useEmptyJobsDidMount();
  useSearchByKeywordData(searchText);

  function handleChange(e) {
    const nextSearchText = e.target.value;
    setInputText(nextSearchText);
  }

  function handleEnter() {
    const nextSearchText = inputText;
    setSearchText(nextSearchText);
    history.push(`/keywords/${encodeURIComponent(nextSearchText)}`);
  }

  return (
    <React.Fragment>
      <h1>Search Jobs by keyword</h1>
      <SearchInput
        inputText={inputText}
        handleChange={handleChange}
        handleEnter={handleEnter}
      />
      {jobs.map(job => <Job key={job.jobId} job={job} />)}
    </React.Fragment>
  );
};

const SearchInput = ({ inputText, handleChange, handleEnter }) => (
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

export default SearchByKeyword;

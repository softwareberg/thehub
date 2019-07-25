import React, {Component} from 'react';
import {APPEND_JOBS} from '../redux/actions';
import {connect} from 'react-redux';
import Form from 'react-bootstrap/Form';
import Job from './Job';
import {findJobsByKeyword} from '../utils/api';

class SearchByKeyword extends Component {
  componentDidMount() {
    this.downloadJobs(this.state.searchText);
  }

  constructor(props) {
    super(props);
    this.state = {
      inputText: this.props.match.params.searchText ? decodeURIComponent(this.props.match.params.searchText) : '',
      searchText: this.props.match.params.searchText ? decodeURIComponent(this.props.match.params.searchText) : ''
    }
  }

  render() {
    const jobs = this.props.jobs;
    const inputText = this.state.inputText;
    return (
      <React.Fragment>
        <h1>Search Jobs by keyword</h1>
        <SearchInput
          inputText={inputText}
          handleChange={this.handleChange.bind(this)}
          handleEnter={this.handleEnter.bind(this)}
        />
        {jobs.filter(this.criteria).map((job) => <Job job={job}/>)}
      </React.Fragment>
    );
  }

  criteria = (job) => (
    this.state.searchText.length > 0 && job.keywords.some((k) => (k.toLowerCase() === this.state.searchText.toLowerCase()))
  );

  downloadJobs(keyword) {
    const jobsIds = this.props.jobsIds;
    if (keyword.length > 0) {
      findJobsByKeyword(keyword).then((jobs) => {
        this.props.dispatch({
          type: APPEND_JOBS,
          jobs: jobs.filter((job) => (!jobsIds.has(job.jobId)))
        });
      })
    }
  }

  handleChange(e) {
    const nextSearchText = e.target.value;
    this.setState({inputText: nextSearchText});
  }

  handleEnter() {
    const nextSearchText = this.state.inputText;
    this.setState({searchText: nextSearchText});
    this.props.history.push('/keywords/' + encodeURIComponent(nextSearchText));
    this.downloadJobs(nextSearchText);
  }
}

const SearchInput = ({inputText, handleChange, handleEnter, ...props}) => (
  <Form onSubmit={(e) => {e.preventDefault(); handleEnter()}}>
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

const mapStateToProps = (state) => ({
  jobs: state.jobs,
  jobsIds: state.jobsIds
});

export default connect(mapStateToProps)(SearchByKeyword);

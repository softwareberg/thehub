import React, {Component} from 'react';
import clearJobs from '../redux/actions/clearJobs';
import {connect} from 'react-redux';
import {findJobsByKeyword} from '../utils/api';
import Form from 'react-bootstrap/Form';
import Job from './Job';
import setJobs from '../redux/actions/setJobs';

class SearchByKeyword extends Component {
  componentDidMount() {
    this.props.dispatch(clearJobs());
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
        {jobs.map(job => <Job key={job.jobId} job={job}/>)}
      </React.Fragment>
    );
  }

  downloadJobs(keyword) {
    if (keyword.length > 0) {
      findJobsByKeyword(keyword).then(jobs => {
        this.props.dispatch(setJobs(jobs));
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
    this.props.dispatch(clearJobs());
    this.downloadJobs(nextSearchText);
  }
}

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

const mapStateToProps = state => ({jobs: state.jobs});

export default connect(mapStateToProps)(SearchByKeyword);

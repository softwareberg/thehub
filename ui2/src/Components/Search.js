import React, {Component} from 'react';
import {connect} from 'react-redux';
import Form from 'react-bootstrap/Form';
import Job from './Job';

class Search extends Component {
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
        <h1>Search Jobs</h1>
        <SearchInput
          inputText={inputText}
          handleChange={this.handleChange.bind(this)}
          handleEnter={this.handleEnter.bind(this)}
        />
        {jobs.filter(this.criteria).map((job) => <Job key={job.jobId} job={job}/>)}
      </React.Fragment>
    );
  }

  criteria = (job) => (
    this.state.searchText.length >= 2 && job.description.value.includes(this.state.searchText)
  );

  handleChange(e) {
    const nextSearchText = e.target.value;
    this.setState({inputText: nextSearchText});
  }

  handleEnter() {
    const nextSearchText = this.state.inputText;
    this.setState({searchText: nextSearchText});
    this.props.history.push('/search/' + encodeURIComponent(nextSearchText));
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
});

export default connect(mapStateToProps)(Search);

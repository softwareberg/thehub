import React, {Component} from 'react';
import {connect} from 'react-redux';
import Job from './Job';

class Search extends Component {
  constructor(props) {
    super(props);
    this.state = {
      searchText: this.props.match.params.searchText ? decodeURIComponent(this.props.match.params.searchText) : ''
    }
  }

  render() {
    const jobs = this.props.jobs;
    const searchText = this.state.searchText;
    return (
      <React.Fragment>
        <header><h1>Search Jobs</h1></header>
        <SearchInput inputText={searchText} handleChange={this.handleChange.bind(this)}/>
        {jobs.filter(this.criteria).map((job) => <Job key={job.jobId} job={job}/>)}
      </React.Fragment>
    );
  }

  criteria = (job) => (
    this.state.searchText.length >= 2 && job.description.value.includes(this.state.searchText)
  );

  handleChange(e) {
    const newText = e.target.value;
    this.setState({searchText: newText});
    this.props.history.push('/search/' + encodeURIComponent(newText))
  }
}

const SearchInput = ({inputText, handleChange, ...props}) => (
  <React.Fragment>
    <input
      autoFocus
      value={inputText}
      onChange={handleChange}
    />
  </React.Fragment>
);

const mapStateToProps = (state) => ({
  jobs: state.jobs,
});

export default connect(mapStateToProps)(Search);

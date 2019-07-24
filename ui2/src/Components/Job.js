import React, {Component} from 'react';
import {connect} from 'react-redux';
import {SET_UNWRAP} from '../redux/actions';

class Job extends Component {
  constructor(props) {
    super(props);
    this.state = {jobId: props.job.jobId};
  }

  setUnwrap(isUnwrapped) {
    console.log(isUnwrapped);

    this.props.dispatch({
      type: SET_UNWRAP,
      jobId: this.state.jobId,
      isUnwrapped: isUnwrapped
    });
  }

  render() {
    const job = this.props.job;
    return (
      <React.Fragment>
        <div style={{border: 'solid', margin: 12, padding: 12}}>
          <small>{job.jobId}</small>
          <Star hasStar={job.hasStar}/>
          <Title title={job.title}/>
          <Description
            description={job.description.value}
            isUnwrapped={job.description.isUnwrapped}
            setUnwrap={this.setUnwrap.bind(this)}
          />
          <Keywords keywords={job.keywords}/>
          <Href href={job.href}/>
        </div>
      </React.Fragment>
    );
  }
}

const Star = ({hasStar, ...props}) => (
  <p>
    {hasStar && 'FAV'}
    {!hasStar && 'NOFAV'}
  </p>
);

const Title = ({title, ...props}) => (
  <h2>{title}</h2>
);

const Description = ({description, isUnwrapped, setUnwrap, ...props}) => {
  const renderedDescription = isUnwrapped ? description : description.substring(0, 300);
  const moreToggle = (<React.Fragment>... <a onClick={() => setUnwrap(true)}>more</a></React.Fragment>);
  const hideToggle = (<React.Fragment> <a onClick={() => setUnwrap(false)}>hide</a></React.Fragment>);
  return (
    <React.Fragment>
      {renderedDescription}
      {!isUnwrapped && moreToggle}
      {isUnwrapped && hideToggle}
    </React.Fragment>
  )
};

const Keywords = ({keywords, ...props}) => (
  <ul>
    {keywords.map((k) => (<li>{k}</li>))}
  </ul>
);

const Href = ({href, ...props}) => (
  <p>
    <a href={href}>{href}</a>
  </p>
);

const mapStateToProps = (state) => ({
  // Nothing.
});

export default connect(mapStateToProps)(Job);

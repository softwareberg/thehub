import React, {Component} from 'react';

class Job extends Component {
  render() {
    const job = this.props.job;

    return (
      <React.Fragment>
        <div style={{border: 'solid', margin: 12, padding: 12}}>
          <small>{job.jobId}</small>
          <Star hasStar={job.hasStar}/>
          <Title title={job.title}/>
          <Description description={job.description.value} isUnwrapped={job.description.isUnwrapped}/>
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

const Description = ({description, isUnwrapped, ...props}) => (
  <React.Fragment>
    {!isUnwrapped && description.substring(0, 300) + '... ' + 'more'}
    {isUnwrapped && description + ' hide'}
  </React.Fragment>
);

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

export default Job;

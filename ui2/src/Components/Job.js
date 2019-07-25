import React, {Component} from 'react';
import {DELETE_JOB, SET_STAR, SET_UNWRAP} from '../redux/actions';
import Badge from 'react-bootstrap/Badge';
import Card from 'react-bootstrap/Card';
import {connect} from 'react-redux';
import {deleteJob, startJob} from '../utils/api';
import StarRegular from '../assets/img/star-regular.svg'
import StarSolid from '../assets/img/star-solid.svg'

class Job extends Component {
  constructor(props) {
    super(props);
    this.state = {jobId: props.job.jobId};
  }

  render() {
    const job = this.props.job;
    return (
      <Card body style={{marginBottom: 8}}>
        <Title
          title={job.title}
          hasStar={job.hasStar}
          setStar={this.setStar.bind(this)}
        />
        <Description
          description={job.description.value}
          isUnwrapped={job.description.isUnwrapped}
          setUnwrap={this.setUnwrap.bind(this)}
        />
        <Keywords keywords={job.keywords}/>
        <Links
          href={job.href}
          deleteJob={this.deleteJob.bind(this)}
        />
      </Card>
    );
  }

  setUnwrap(isUnwrapped) {
    this.props.dispatch({
      type: SET_UNWRAP,
      jobId: this.state.jobId,
      isUnwrapped: isUnwrapped
    });
  }

  setStar(hasStar) {
    this.props.dispatch({
      type: SET_STAR,
      jobId: this.state.jobId,
      hasStar: hasStar
    });
    startJob(this.state.jobId, hasStar);  // TODO ignore or not to ignore?
  }

  deleteJob() {
    this.props.dispatch({
      type: DELETE_JOB,
      jobId: this.state.jobId,
    });
    deleteJob(this.state.jobId);  // TODO ignore or not to ignore?
  }
}

const Star = ({hasStar, setStar, ...props}) => (
  <img
    src={hasStar ? StarSolid : StarRegular}
    alt={hasStar ? "full star" : "empty star"}
    style={{
      height: '1em',
      cursor: 'pointer'
    }}
    onClick={() => setStar(!hasStar)}
  />
);

const Title = ({title, hasStar, setStar, ...props}) => (
  <Card.Title>
    <Star hasStar={hasStar} setStar={setStar}/>
    <span style={{marginLeft: '0.5ch'}}>{title}</span>
  </Card.Title>
);

const Description = ({description, isUnwrapped, setUnwrap, ...props}) => {
  const renderedDescription = isUnwrapped ? description : description.substring(0, 300);
  const moreToggle = (<span>... <a href='#more' onClick={(e) => {e.preventDefault();setUnwrap(true)}}>more</a></span>);
  const hideToggle = (<span> <a href='#hide' onClick={(e) => {e.preventDefault();setUnwrap(false)}}>hide</a></span>);
  return (
    <Card.Text style={{whiteSpace: 'pre-line'}}>
      {renderedDescription}
      {!isUnwrapped && moreToggle}
      {isUnwrapped && hideToggle}
    </Card.Text>
  )
};

const Keywords = ({keywords, ...props}) => (
  <ul>
    {keywords.map((k) => (
      <li key={k} style={{display: 'inline'}}>
        <Badge pill variant="light" style={{fontWeight: 'normal', backgroundColor: 'rgb(241, 241, 241)'}}>
          {k}
        </Badge>
      </li>
    ))}
  </ul>
);

const Links = ({href, deleteJob, ...props}) => (
  <React.Fragment>
    <Card.Link href={href} target="_blank" rel='noopener noreferrer'>Link</Card.Link>
    <Card.Link href='#delete' className='text-muted' onClick={(e) => {e.preventDefault(); deleteJob()}}>Delete</Card.Link>
  </React.Fragment>
);

const mapStateToProps = (state) => ({
  // Nothing.
});

export default connect(mapStateToProps)(Job);

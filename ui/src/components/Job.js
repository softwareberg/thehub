import React, {Component} from 'react';
import Badge from 'react-bootstrap/Badge';
import Card from 'react-bootstrap/Card';
import {connect} from 'react-redux';
import deleteJob from '../redux/actions/deleteJob';
import {deleteJob as deleteJobApi, startJob as startJobApi} from '../utils/api';
import StarRegular from '../assets/img/star-regular.svg'
import StarSolid from '../assets/img/star-solid.svg'
import setUnwrap from '../redux/actions/setUnwrap';
import setStar from '../redux/actions/setStar';

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
    this.props.dispatch(setUnwrap(this.state.jobId, isUnwrapped));
  }

  setStar(hasStar) {
    this.props.dispatch(setStar(this.state.jobId, hasStar));
    startJobApi(this.state.jobId, hasStar);  // TODO ignore or not to ignore?
  }

  deleteJob() {
    this.props.dispatch(deleteJob(this.state.jobId));
    deleteJobApi(this.state.jobId);  // TODO ignore or not to ignore?
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
  if (isUnwrapped) {
    return (
      <Card.Text style={{whiteSpace: 'pre-line'}}>
        {description}
        <span> <a href='#hide' onClick={(e) => {e.preventDefault();setUnwrap(false)}}>hide</a></span>
      </Card.Text>
    )
  } else {
    return (
      <Card.Text>
        {description.substring(0, 300)}
        <span>... <a href='#more' onClick={(e) => {e.preventDefault();setUnwrap(true)}}>more</a></span>
      </Card.Text>
    )
  }

};

const Keywords = ({keywords, ...props}) => (
  <ul>
    {keywords.map(k => (
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
    <Card.Link href='#delete' className='text-muted' onClick={e => {e.preventDefault(); deleteJob()}}>Delete</Card.Link>
  </React.Fragment>
);

const mapStateToProps = state => ({});

export default connect(mapStateToProps)(Job);

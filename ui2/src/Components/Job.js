import React, {Component} from 'react';
import {connect} from 'react-redux';
import {DELETE_JOB, SET_STAR, SET_UNWRAP} from '../redux/actions';
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
      <article key={this.props.job.jobId} style={{border: 'solid', margin: 12, padding: 12}}>
        <small>{job.jobId}</small>
        <header>
          <Star
            hasStar={job.hasStar}
              setStar={this.setStar.bind(this)}
          />
          <Title title={job.title}/>
        </header>
        <Description
          description={job.description.value}
          isUnwrapped={job.description.isUnwrapped}
          setUnwrap={this.setUnwrap.bind(this)}
        />
        <aside>
          <Keywords keywords={job.keywords}/>
          <Href href={job.href}/>
          <Delete deleteJob={this.deleteJob.bind(this)}/>
        </aside>
      </article>
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
  }

  deleteJob() {
    this.props.dispatch({
      type: DELETE_JOB,
      jobId: this.state.jobId,
    });
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

const Title = ({title, ...props}) => (
  <h2>{title}</h2>
);

const Description = ({description, isUnwrapped, setUnwrap, ...props}) => {
  const renderedDescription = isUnwrapped ? description : description.substring(0, 300);
  const moreToggle = (<span>... <a href='#more' onClick={(e) => {e.preventDefault();setUnwrap(true)}}>more</a></span>);
  const hideToggle = (<span> <a href='#hide' onClick={(e) => {e.preventDefault();setUnwrap(false)}}>hide</a></span>);
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
    {keywords.map((k) => <li key={k}>{k}</li>)}
  </ul>
);

const Href = ({href, ...props}) => (
  <p>
    <a href={href}>{href}</a>
  </p>
);

const Delete = ({deleteJob,...props}) => (
  <p>
    <a href='#delete' onClick={(e) => {e.preventDefault(); deleteJob()}}>Delete</a>
  </p>
);

const mapStateToProps = (state) => ({
  // Nothing.
});

export default connect(mapStateToProps)(Job);

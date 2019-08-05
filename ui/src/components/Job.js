import React from 'react';
import Badge from 'react-bootstrap/Badge';
import Card from 'react-bootstrap/Card';
import { useDispatch } from 'react-redux';
import deleteJobAction from '../redux/actions/deleteJob';
import { deleteJob as deleteJobApi, startJob as startJobApi } from '../utils/api';
import StarRegular from '../assets/img/star-regular.svg';
import StarSolid from '../assets/img/star-solid.svg';
import setUnwrapAction from '../redux/actions/setUnwrap';
import setStarAction from '../redux/actions/setStar';

const Job = ({ job, ...props }) => {
  const { jobId } = job;
  const dispatch = useDispatch();

  function setStar(hasStar) {
    dispatch(setStarAction(jobId, hasStar));
    startJobApi(jobId, hasStar); // TODO ignore or not to ignore?
  }

  function setUnwrap(isUnwrapped) {
    dispatch(setUnwrapAction(jobId, isUnwrapped));
  }

  function deleteJob() {
    dispatch(deleteJobAction(jobId));
    deleteJobApi(jobId); // TODO ignore or not to ignore?
  }

  return (
    <Card body style={{ marginBottom: 8 }}>
      <Title
        title={job.title}
        hasStar={job.hasStar}
        setStar={setStar}
      />
      <Description
        description={job.description.value}
        isUnwrapped={job.description.isUnwrapped}
        setUnwrap={setUnwrap}
      />
      <Keywords keywords={job.keywords} />
      <Links
        href={job.href}
        deleteJob={deleteJob}
      />
    </Card>
  );
};

const Star = ({ hasStar, setStar, ...props }) => (
  <img
    src={hasStar ? StarSolid : StarRegular}
    alt={hasStar ? 'full star' : 'empty star'}
    style={{
      height: '1em',
      cursor: 'pointer'
    }}
    onClick={() => setStar(!hasStar)}
  />
);

const Title = ({ title, hasStar, setStar }) => (
  <Card.Title>
    <Star hasStar={hasStar} setStar={setStar} />
    <span style={{ marginLeft: '0.5ch' }}>{title}</span>
  </Card.Title>
);

const Description = ({ description, isUnwrapped, setUnwrap }) => {
  if (isUnwrapped) {
    return (
      <Card.Text style={{ whiteSpace: 'pre-line' }}>
        {description}
        <span>
          {' '}
          <a href="#hide" onClick={(e) => { e.preventDefault(); setUnwrap(false); }}>hide</a>
        </span>
      </Card.Text>
    );
  }
  return (
    <Card.Text>
      {description.substring(0, 300)}
      <span>
...
        <a href="#more" onClick={(e) => { e.preventDefault(); setUnwrap(true); }}>more</a>
      </span>
    </Card.Text>
  );
};

const Keywords = ({ keywords }) => (
  <ul>
    {keywords.map(k => (
      <li key={k} style={{ display: 'inline' }}>
        <Badge pill variant="light" style={{ fontWeight: 'normal', backgroundColor: 'rgb(241, 241, 241)' }}>
          {k}
        </Badge>
      </li>
    ))}
  </ul>
);

const Links = ({ href, deleteJob }) => (
  <React.Fragment>
    <Card.Link href={href} target="_blank" rel="noopener noreferrer">Link</Card.Link>
    <Card.Link href="#delete" className="text-muted" onClick={(e) => { e.preventDefault(); deleteJob(); }}>Delete</Card.Link>
  </React.Fragment>
);

export default React.memo(Job);

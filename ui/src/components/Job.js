import React, { useState } from 'react';
import Badge from 'react-bootstrap/Badge';
import Card from 'react-bootstrap/Card';
import deleteJobAction from 'redux/actions/deleteJob';
import { deleteJob as deleteJobApi, starJob as starJobApi } from 'utils/api';
import setUnwrapAction from 'redux/actions/setUnwrap';
import setStarAction from 'redux/actions/setStar';
import { showAlert } from 'utils/commons';
import StarRegular from 'assets/img/star-regular.svg';
import StarSolid from 'assets/img/star-solid.svg';
import { useDispatch } from 'react-redux';
import useHover from 'hooks/useHover';

const Job = ({ job }) => (
  <Card body style={{ marginBottom: 8 }}>
    <Title
      hasStar={job.hasStar}
      jobId={job.jobId}
      title={job.title}
    />
    <Image
      logo={job.logo}
      poster={job.poster}
    />
    <Description
      description={job.description.value}
      isUnwrapped={job.description.isUnwrapped}
      jobId={job.jobId}
    />
    <Keywords keywords={job.keywords} />
    <Links
      href={job.href}
      jobId={job.jobId}
    />
  </Card>
);

const Image = ({ poster, logo }) => {
  const [logoOpacity, setLogoOpacity] = useState(1);
  const logoHover = useHover(isHover => (isHover ? setLogoOpacity(0.1) : setLogoOpacity(1)));

  return (
    <div
      style={{
        position: 'relative',
        display: 'inline-flex',
        justifyContent: 'center',
        width: '100%',
        height: 380
      }}
    >
      <Card.Img
        variant="top"
        src={poster}
        alt=""
        style={{
          objectFit: 'cover'
        }}
      />
      <picture
        style={{
          position: 'absolute',
          top: '50%',
          left: '50%',
          height: 150,
          width: 200,
          transform: 'translate(-50%, -50%)',
          display: 'flex',
          justifyContent: 'center',
          alignContent: 'center'
        }}
        {...logoHover}
      >
        <img
          src={logo}
          alt=""
          style={{
            opacity: logoOpacity,
            objectFit: 'contain'
          }}
        />
      </picture>
    </div>
  );
};

const Star = ({ jobId, hasStar }) => {
  const dispatch = useDispatch();

  function setStar(hasStar2) {
    dispatch(setStarAction(jobId, hasStar2));
    starJobApi(jobId, hasStar2)
      .catch(() => showAlert(`Error occurred while sending star!\n\n{jobId: ${jobId}, hasStar: ${hasStar}}`));
  }

  return (
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
};

const Title = ({ title, hasStar, jobId }) => (
  <Card.Title>
    <Star hasStar={hasStar} jobId={jobId} />
    <span style={{ marginLeft: '0.5ch' }}>{title}</span>
  </Card.Title>
);

const Description = ({ description, isUnwrapped, jobId }) => {
  const dispatch = useDispatch();

  function setUnwrap(isUnwrapped2) {
    dispatch(setUnwrapAction(jobId, isUnwrapped2));
  }

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

const Links = ({ href, jobId }) => {
  const dispatch = useDispatch();

  function deleteJob() {
    dispatch(deleteJobAction(jobId));
    deleteJobApi(jobId)
      .catch(() => showAlert(`Error occurred while deleting an offer!\n\n{jobId: ${jobId}}`));
  }

  return (
    <React.Fragment>
      <Card.Link href={href} target="_blank" rel="noopener noreferrer">Link</Card.Link>
      <Card.Link href="#delete" className="text-muted" onClick={(e) => { e.preventDefault(); deleteJob(); }}>Delete</Card.Link>
    </React.Fragment>
  );
};

export default React.memo(Job);

import { h } from 'hyperapp';

const Star = ({ solid, onClick }) => (
  <img
    src={solid ? 'img/star-solid.svg' : 'img/star-regular.svg'}
    alt="star"
    style={{
      height: '1em',
      cursor: 'pointer'
    }}
    onclick={onClick}
  />
);

const Keywords = ({ keywords }) => (
  <ul>
    {keywords.map(keyword => <Keyword keyword={keyword} />)}
  </ul>
);

const Keyword = ({ keyword }) => (
  <li
    className="badge badge-pill badge-light"
    style={{
      display: 'inline',
      backgroundColor: '#f1f1f1',
      fontWeight: 'normal'
    }}
  >
    {keyword}
  </li>
);

const Description = ({ description, onDescriptionUnwrap }) => {
  if (description.value.length > 300) {
    if (description.isUnwrapped) {
      return (
        <span>
          {description.value.substring(0, 300)}
          ...
          <a href="#more" class="ml-1" onclick={onDescriptionUnwrap}>more</a>
        </span>
      );
    }
    return (
      <span
        style={{
          whiteSpace: 'pre-line'
        }}
      >
        {description.value}
        <a href="#hide" className="ml-1" onclick={onDescriptionUnwrap}>hide</a>
      </span>
    );
  }
  return (<span>{description}</span>);
};

const Job = ({
  title,
  description,
  keywords,
  hasStar,
  href,
  onStarClick,
  onDescriptionUnwrap,
  onDelete
}) => (
  <div class="card">
    <div class="card-body">
      <h5 class="card-title">
        <Star solid={hasStar} onClick={onStarClick} />
        <span class="ml-1">{title}</span>
      </h5>
      <p class="card-text">
        <Description description={description} onDescriptionUnwrap={onDescriptionUnwrap} />
      </p>
      <Keywords keywords={keywords} />
      <a href={href} class="card-link" target="_blank" rel="noopener noreferrer">Link</a>
      <a href="#delete" class="card-link text-muted" onclick={onDelete}>Delete</a>
    </div>
  </div>
);

const Jobs = ({ jobs, onStarClick, onDelete, onDescriptionUnwrap }) => (
  <div>
    {jobs.map(({ jobId, title, description, keywords, hasStar, href }) => (
      <div class="mb-2">
        <Job
          key={jobId}
          title={title}
          description={description}
          keywords={keywords}
          href={href}
          hasStar={hasStar}
          onStarClick={() => onStarClick(jobId)}
          onDelete={() => onDelete(jobId)}
          onDescriptionUnwrap={() => onDescriptionUnwrap(jobId)}
        />
      </div>
    ))}
  </div>
);

export const AllJobs = () => (state, actions) => (
  <div oncreate={actions.jobs.fetchJobs}>
    <h1>All Jobs</h1>
    <Jobs
      jobs={state.jobs.list.map(job => state.jobs.byId[job])}
      onStarClick={jobId => actions.jobs.toggleStart(jobId)}
      onDelete={jobId => actions.jobs.deleteJob(jobId)}
      onDescriptionUnwrap={jobId => actions.jobs.toggleDescriptionUnwrap(jobId)}
    />
  </div>
);

export const StarredJobs = () => (state, actions) => (
  <div oncreate={actions.jobs.fetchStarred}>
    <h1>Starred Jobs</h1>
    <Jobs
      jobs={Object.values(state.jobs.byId).filter(job => job.hasStar)}
      onStarClick={jobId => actions.jobs.toggleStart(jobId)}
      onDelete={jobId => actions.jobs.deleteJob(jobId)}
      onDescriptionUnwrap={jobId => actions.jobs.toggleDescriptionUnwrap(jobId)}
    />
  </div>
);

const Search = ({ searchByKeyword }) => {
  function onEnterDown(event) {
    if (event.keyCode === 13) {
      searchByKeyword(event.target.value);
    }
  }

  return (
    <div className="input-group input-group-lg mb-2">
      <input
        type="text"
        className="form-control"
        onkeyup={onEnterDown}
      />
    </div>
  );
};

export const SearchJobs = () => (state, actions) => (
  <div oncreate={actions.jobs.resetSearchResult}>
    <h1>Search Jobs by keyword</h1>
    <Search searchByKeyword={actions.jobs.searchByKeyword} />
    <Jobs
      jobs={state.jobs.search.map(job => state.jobs.byId[job])}
      onStarClick={jobId => actions.jobs.toggleStart(jobId)}
      onDelete={jobId => actions.jobs.deleteJob(jobId)}
      onDescriptionUnwrap={jobId => actions.jobs.toggleDescriptionUnwrap(jobId)}
    />
  </div>
);

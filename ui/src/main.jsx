import { h, app } from 'hyperapp';
import { actions } from 'actions';
import { AllJobs, StarredJobs, SearchJobs } from 'jobs';
import { Link, Route, location } from '@hyperapp/router';
import { withLogger } from '@hyperapp/logger';

export default function main(state) {
  const view = () => (
    <div class="container">
      <div class="row">
        <div class="col-xs-12">
          <ul>
            <li>
              <Link to="/">Home</Link>
            </li>
            <li>
              <Link to="/all">All Jobs</Link>
            </li>
            <li>
              <Link to="/starred">Starred Jobs</Link>
            </li>
            <li>
              <Link to="/search">Search by keyword</Link>
            </li>
          </ul>
          <Route path="/" render={() => <h1>Hello!</h1>} />
          <Route path="/all" render={AllJobs} />
          <Route path="/starred" render={StarredJobs} />
          <Route path="/search" render={SearchJobs} />
        </div>
      </div>
    </div>
  );

  const appActions = withLogger(app)(state, actions, view, document.body);
  location.subscribe(appActions.location);
  return appActions;
}

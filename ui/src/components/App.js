import React from 'react';
import { Route, Switch, BrowserRouter as Router } from 'react-router-dom';
import AllJobs from 'components/AllJobs';
import Home from 'components/Home';
import Layout from 'components/Layout';
import NotFound from 'components/NotFound';
import Search from 'components/Search';
import SearchByKeyword from 'components/SearchByKeyword';
import StarredJobs from 'components/StarredJobs';
import withRedux from 'components/withReduxHelper';

const App = appProps => (
  <React.Fragment>
    <Router {...appProps}>
      <Switch>
        <Route path="/" exact component={props => <Layout component={Home} {...props} />} />
        <Route path="/all" exact component={props => <Layout component={AllJobs} {...props} />} />
        <Route path="/starred" exact component={props => <Layout component={StarredJobs} {...props} />} />
        <Route path="/search" exact component={props => <Layout component={Search} {...props} />} />
        <Route path="/search/:searchText" component={props => <Layout component={Search} {...props} />} />
        <Route path="/keywords" exact component={props => <Layout component={SearchByKeyword} {...props} />} />
        <Route path="/keywords/:searchText" component={props => <Layout component={SearchByKeyword} {...props} />} />
        <Route component={props => <Layout component={NotFound} {...props} />} />
      </Switch>
    </Router>
  </React.Fragment>
);

export default withRedux(App);

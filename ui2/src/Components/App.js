import React, {Component} from 'react';
import {Route, Switch, BrowserRouter as Router} from "react-router-dom";
import AllJobs from "./AllJobs";
import {connect} from 'react-redux';
import {fetchJobs} from '../utils/api';
import Home from "./Home";
import Layout from "./Layout";
import NotFound from "./NotFound";
import Search from "./Search";
import SearchByKeyword from "./SearchByKeyword";
import {SET_JOBS} from '../redux/actions';
import StarredJobs from "./StarredJobs";
import withRedux from "./withRedux";

class App extends Component {
  componentDidMount() {
    this.refreshJobs();
  }

  render = () => (
    <React.Fragment>
      <Router {...this.props}>
        <Switch>
          <Route path='/' exact component={(props) => <Layout component={Home} {...props}/>}/>
          <Route path='/all' exact component={(props) => <Layout component={AllJobs} {...props}/>}/>
          <Route path='/starred' exact component={(props) => <Layout component={StarredJobs} {...props}/>}/>
          <Route path='/search' exact component={(props) => <Layout component={Search} {...props}/>}/>
          <Route path='/search/:searchText' component={(props) => <Layout component={Search} {...props}/>}/>
          <Route path='/keywords' exact component={(props) => <Layout component={SearchByKeyword} {...props}/>}/>
          <Route path='/keywords/:searchText' component={(props) => <Layout component={SearchByKeyword} {...props}/>}/>
          <Route component={(props) => <Layout component={NotFound} {...props}/>}/>
        </Switch>
      </Router>
    </React.Fragment>
  );

  refreshJobs() {
    fetchJobs().then((jobs) => {
      // TODO DEBUG
      console.debug(jobs);

      this.props.dispatch({
        type: SET_JOBS,
        jobs: jobs
      });
    })
  }
}

const mapStateToProps = (state) => ({
  // Nothing.
});

export default withRedux(connect(mapStateToProps)(App));

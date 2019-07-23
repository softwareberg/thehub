import React from 'react';
import {connect} from "react-redux";
import {Route, Switch, BrowserRouter as Router} from "react-router-dom";
import AllJobs from "./AllJobs";
import Home from "./Home";
import Layout from "./Layout";
import NotFound from "./NotFound";
import Search from "./Search";
import SearchByKeyword from "./SearchByKeyword";
import StarredJobs from "./StarredJobs";
import withRedux from "./withRedux";

const App = (props) => (
  <React.Fragment>
    <Router>
      <Switch>
        <Route path='/' exact component={(props) => <Layout component={Home} {...props}/>}/>
        <Route path='/all' exact component={() => <Layout component={AllJobs} {...props}/>}/>
        <Route path='/starred' exact component={() => <Layout component={StarredJobs} {...props}/>}/>
        <Route path='/search' exact component={() => <Layout component={Search} {...props}/>}/>
        <Route path='/keywords' exact component={() => <Layout component={SearchByKeyword} {...props}/>}/>
        <Route component={() => <Layout component={NotFound} {...props}/>}/>
      </Switch>
    </Router>
  </React.Fragment>
);

const mapStateToProps = (state) => ({
  x: state.x,
});

export default withRedux(connect(mapStateToProps)(App));

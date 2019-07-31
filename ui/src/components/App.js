import React from 'react';
import {Route, Switch, BrowserRouter as Router} from "react-router-dom";
import AllJobs from "./AllJobs";
import Home from "./Home";
import Layout from "./Layout";
import NotFound from "./NotFound";
import Search from "./Search";
import SearchByKeyword from "./SearchByKeyword";
import StarredJobs from "./StarredJobs";
import withRedux from "./withRedux";

const App = ({...props}) => (
  <React.Fragment>
    <Router {...props}>
      <Switch>
        <Route path='/' exact component={props => <Layout component={Home} {...props}/>}/>
        <Route path='/all' exact component={props => <Layout component={AllJobs} {...props}/>}/>
        <Route path='/starred' exact component={props => <Layout component={StarredJobs} {...props}/>}/>
        <Route path='/search' exact component={props => <Layout component={Search} {...props}/>}/>
        <Route path='/search/:searchText' component={props => <Layout component={Search} {...props}/>}/>
        <Route path='/keywords' exact component={props => <Layout component={SearchByKeyword} {...props}/>}/>
        <Route path='/keywords/:searchText' component={props => <Layout component={SearchByKeyword} {...props}/>}/>
        <Route component={props => <Layout component={NotFound} {...props}/>}/>
      </Switch>
    </Router>
  </React.Fragment>
);

export default withRedux(App);

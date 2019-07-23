import React from 'react';
import {Route, Switch, BrowserRouter as Router} from "react-router-dom";
import AllJobs from "./AllJobs";
import Home from "./Home";
import NotFound from "./NotFound";
import Search from "./Search";
import SearchByKeyword from "./SearchByKeyword";
import StarredJobs from "./StarredJobs";

const App = (props) => (
  <React.Fragment>
    <Router>
      <Switch>
        <Route path='/' exact component={() => <Home {...props}/>}/>
        <Route path='/all' exact component={() => <AllJobs {...props}/>}/>
        <Route path='/starred' exact component={() => <StarredJobs {...props}/>}/>
        <Route path='/search' exact component={() => <Search {...props}/>}/>
        <Route path='/keywords' exact component={() => <SearchByKeyword {...props}/>}/>
        <Route component={() => <NotFound {...props}/>}/>
      </Switch>
    </Router>
  </React.Fragment>
);

export default App;

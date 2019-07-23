import React, {Component} from 'react';
import {Link} from "react-router-dom";

const Menu = (props) => (
  <React.Fragment>
    <ul>
      <li><Link to="/">Home</Link></li>
      <li><Link to="/all">All Jobs</Link></li>
      <li><Link to="/starred">Starred Jobs</Link></li>
      <li><Link to="/search">Search</Link></li>
      <li><Link to="/keywords">Search by keyword</Link></li>
    </ul>
  </React.Fragment>
);

export default Menu;

import React from 'react';
import {NavLink} from "react-router-dom";

const Menu = (props) => (
  <nav>
    <ul>
      <li><NavLink to="/">Home</NavLink></li>
      <li><NavLink to="/all">All Jobs</NavLink></li>
      <li><NavLink to="/starred">Starred Jobs</NavLink></li>
      <li><NavLink to="/search">Search</NavLink></li>
      <li><NavLink to="/keywords">Search by keyword</NavLink></li>
    </ul>
  </nav>
);

export default Menu;

import React from 'react';
import { Link } from 'react-router-dom';
import Nav from 'react-bootstrap/Nav';

const Menu = () => (
  <Nav as="ul" navbar>
    <Nav.Item as="li"><Link to="/">Home</Link></Nav.Item>
    <Nav.Item as="li"><Link to="/all">All Jobs</Link></Nav.Item>
    <Nav.Item as="li"><Link to="/starred">Starred Jobs</Link></Nav.Item>
    <Nav.Item as="li"><Link to="/search">Search</Link></Nav.Item>
    <Nav.Item as="li"><Link to="/keywords">Search by keyword</Link></Nav.Item>
  </Nav>
);

export default React.memo(Menu);

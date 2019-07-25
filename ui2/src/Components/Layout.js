import React from 'react';
import Container from 'react-bootstrap/Container';
import Menu from "./Menu";

const Layout = ({component: Component, ...props}) => (
  <Container>
    <Menu/>
    <main>
      <Component {...props}/>
    </main>
  </Container>
);

export default Layout;

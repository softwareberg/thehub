import React, { useRef, useEffect } from 'react';
import Container from 'react-bootstrap/Container';
import ErrorAlert from 'components/ErrorAlert';
import Menu from 'components/Menu';

const Layout = ({ component: Component, ...props }) => (
  <Container>
    <ErrorAlert />
    <Menu />
    <main>
      <Component {...props} />
    </main>
  </Container>
);

export default Layout;

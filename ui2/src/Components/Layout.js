import React from 'react';
import Menu from "./Menu";

const Layout = ({component: Component, ...props}) => (
  <React.Fragment>
    <Menu/>
    <Component {...props}/>
  </React.Fragment>
);

export default Layout;

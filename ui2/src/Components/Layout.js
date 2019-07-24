import React from 'react';
import Menu from "./Menu";

const Layout = ({component: Component, ...props}) => (
  <React.Fragment>
    <Menu/>
    <main>
      <Component {...props}/>
    </main>
  </React.Fragment>
);

export default Layout;

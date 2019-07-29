import React from 'react';
import {createStore} from 'redux';
import {Provider} from 'react-redux';
import reducer from '../redux/reducer'

const store = createStore(reducer);

function withRedux(Component) {
  function withRedux(props) {
    return (
      <Provider store={store}>
        <Component {...props}/>
      </Provider>
    );
  }

  return withRedux;
}

export default withRedux;

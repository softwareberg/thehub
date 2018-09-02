import $ from 'jquery';
import thehub from 'thehub';

require('css/main.scss');

window.jQuery = $;
window.$ = $;

const state = {
  count: 3
};

let actions = thehub(state);

if (module.hot) {
  module.hot.accept('thehub', () => {
    actions = thehub(actions.getState());
  });
}

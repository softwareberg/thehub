import $ from 'jquery';
import main from 'main';
import { state } from 'state';

require('css/main.scss');

window.jQuery = $;
window.$ = $;

let actions = main(state);

if (module.hot) {
  module.hot.accept('main', () => {
    actions = main(actions.getState());
  });
}

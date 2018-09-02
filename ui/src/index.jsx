import $ from 'jquery';
import { h, app } from 'hyperapp';

require('css/main.scss');

window.jQuery = $;
window.$ = $;

const state = {
  count: 0
};

const actions = {
  down: value => state => ({ count: state.count - value }),
  up: value => state => ({ count: state.count + value })
};

const view = (state, actions) => (
  <div>
    <h1>{state.count}</h1>
    <button type="button" onclick={() => actions.down(1)}>-</button>
    <button type="button" onclick={() => actions.up(1)}>+</button>
  </div>
);

app(state, actions, view, document.body);

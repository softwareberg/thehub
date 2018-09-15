import { h } from 'hyperapp';

export default () => (state, actions) => (
  <div>
    <h1>{state.count}</h1>
    <button type="button" onclick={() => actions.down(1)}>down</button>
    <button type="button" onclick={() => actions.up(1)}>up</button>
  </div>
);

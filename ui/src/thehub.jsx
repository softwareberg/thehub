import { h, app } from 'hyperapp';

export default function thehub(state) {
  const actions = {
    down: value => state => ({ count: state.count - value }),
    up: value => state => ({ count: state.count + value }),
    getState: () => state => state
  };

  const view = (state, actions) => (
    <div>
      <h1>{state.count}</h1>
      <button type="button" onclick={() => actions.down(1)}>down</button>
      <button type="button" onclick={() => actions.up(1)}>up</button>
    </div>
  );

  return app(state, actions, view, document.body);
}

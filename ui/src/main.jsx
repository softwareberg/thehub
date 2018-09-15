import { h, app } from 'hyperapp';
import { actions } from 'state';
import Counter from 'counter';
import Todo from 'todo';

export default function main(state) {
  const view = () => (
    <div class="container">
      <div class="row">
        <div class="col-xs-12">
          <Counter />
          <Todo />
        </div>
      </div>
    </div>
  );

  return app(state, actions, view, document.body);
}

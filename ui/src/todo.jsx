import { h } from 'hyperapp';

const TodoItem = ({ id, value, done, toggle }) => ( // eslint-disable-line
  <li
    style={{ textDecoration: `${done ? 'line-through' : ''}` }}
    onclick={() => toggle(id)}
  >
    {value}
  </li>
);

export default () => (state, actions) => (
  <div>
    <h1>Todo</h1>
    <ul>
      {state.todos.map(({ id, value, done }) => (
        <TodoItem id={id} value={value} done={done} toggle={actions.toggle} />
      ))}
    </ul>
  </div>
);

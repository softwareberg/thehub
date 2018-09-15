export const state = {
  count: 0,
  todos: [
    {
      id: 1,
      value: 'ok',
      done: false
    },
    {
      id: 2,
      value: 'ok - a',
      done: true
    }
  ]
};

export const actions = {
  down: value => state => ({ count: state.count - value }),
  up: value => state => ({ count: state.count + value }),
  toggle: id => state => ({ todos: state.todos.map(todo => { // eslint-disable-line
    if (todo.id === id) {
      todo.done = !todo.done;
    }
    return todo;
  })}), // eslint-disable-line
  getState: () => state => state
};

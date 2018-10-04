import { location } from '@hyperapp/router';

export const state = {
  location: location.state,
  jobs: {
    cache: {},
    byId: {},
    list: [],
    search: []
  }
};

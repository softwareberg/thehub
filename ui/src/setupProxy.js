const proxy = require('http-proxy-middleware');

module.exports = function fn(app) {
  app.use(proxy('/api', { target: 'http://localhost:8080/' }));
};

var express = require('express');
var routes = require('./routes.js');
var app = express();

// Load up the routes
app.use('/api', routes);

app.listen(${PORT}, function () {
  console.log('App listening on port 3001!')
})

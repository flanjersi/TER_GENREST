const FusekiHTTP = require('./libs/fusekiHTTP.js');

var express = require('express');
var routes = require('./routes.js');
var app = express();

// Load up the routes
app.use('/api', routes);

var fusekiHTTP = new FusekiHTTP();

fusekiHTTP.createDataset("genrest", "tdb", 'C:\\Users\\Jeremy\\WebstormProjects\\testJsonLDParsing\\data.jsonld')
    .then(function (res) {
		app.listen(${PORT}, function () {
		  console.log('App listening on port 3001!')
		})
	}
);


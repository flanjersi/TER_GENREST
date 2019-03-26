const FusekiHTTP = require('./libs/fusekiHTTP.js');
const descriptionAPI = require('./descriptionAPI.json');
var express = require('express');
var routes = require('./routes.js');
var app = express();

// Load up the routes
app.use('/api', routes);

app.get('', (req, resp) => {
	resp.send(descriptionAPI);
});

function sleep(ms){
	return new Promise(resolve=>{
		setTimeout(resolve,ms)
	})
}

sleep(10000).then(
	function () {
		var options = {
			home: './fuseki',
			url: 'http://localhost:3030/'
		}

		var fusekiHTTP = new FusekiHTTP();

		fusekiHTTP.createDataset("genrest", "tdb", 'data.jsonld')
			.then(function (res) {
					app.listen(3001, function () {
						console.log('App listening on port 3001!')
					})
				}
			);
	}
)




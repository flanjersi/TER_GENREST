const router = require('express').Router();

router.get('http://localhost:8090/terGENREST/api/users/3/projects/', (req, res) => {
  res.render('OK for get all projects');
	});

	

  router.get( 'http://localhost:8090/terGENREST/api/users/3/projects/3', (req, res) => {
  res.render('OK for get project with id '.3);
	});
	
  router.get( 'http://localhost:8090/terGENREST/api/users/3/projects/3/buildings/', (req, res) => {
  res.render('OK for all get building');
});
	


// Export the router
module.exports = router;


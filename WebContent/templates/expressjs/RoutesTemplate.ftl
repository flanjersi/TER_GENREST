const router = require('express').Router();

<#-- Get all projects -->
router.get('${urlProject}', (req, res) => {
  res.render('OK for get all projects');
	});

	
<#---------------------------- Specific element ----------------------------------------------------------->	

<#list idProjects as idProject>
  router.get( '${urlProject}${idProject}', (req, res) => {
  res.render('OK for get project with id '.${idProject});
	});
	
  router.get( '${urlProject}${idProject}/buildings/', (req, res) => {
  res.render('OK for all get building');
});
	
</#list>


// Export the router
module.exports = router;

<#--


-->
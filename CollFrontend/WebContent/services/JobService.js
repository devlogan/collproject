app.factory('JobService',function($http)
		{
	
	var jobService={};
	const SERVER_URL="http://localhost:8574/CollMiddleware";
	
	jobService.addJob=function(job)
	{
		return $http.post(SERVER_URL+"/addJob",job);
	}
	
	jobService.getAllJobs=function()
	{
	
		return $http.get(SERVER_URL+"/getAllJobs");
	}
	
	return jobService;
	
	
})
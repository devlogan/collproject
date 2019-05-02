app.controller("blogCtrl",function($scope,$http,$rootScope,$cookieStore,$location,BlogService){

	console.log('I m Blog Controller');
	$scope.blog={"blogName":"","blogContent":"","email":"","username":"","date":"","status":"","likes":"","dislikes":""};


	$scope.addBlog=function(){

		alert("hello");
		console.log($scope.blog);
		$http.post("http://localhost:8574/CollMiddleware/addBlog",$scope.blog)
		.then(
				function(response){

					$scope.blog=response.data;
					$rootScope.currentBlog=response.data;
					$location.path("userHome");

				},function(response){

					alert("Cannot be added");
					$scope.error=response.data;
				}

		)
	}



			$scope.add=function(){}


});
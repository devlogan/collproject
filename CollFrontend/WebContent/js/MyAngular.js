var app = angular.module("app", ["ngRoute","ngCookies"]);
app.config(function($routeProvider) {
	$routeProvider
	.when("/", {
		templateUrl : "main.html"
	})
	.when("/aboutUs", {
		templateUrl : "AboutUs.htm"
	})
	.when("/login",{
		templateUrl:"user\\Login.html",
		controller:"userCtrl"
	})
	.when("/signUp", {
		templateUrl : "user\\SignUp.htm",
		controller:"userCtrl"
	})
	.when("/userHome",{
		templateUrl:"user\\UserHome.html",
		controller:"userCtrl"
	})
	.when("/logout",{
		templateUrl:"user\\Logout.html",
		controller:"userCtrl"
	})
	.when("/addBlog",{
		templateUrl:"blog\\AddBlog.html",
		controller:"blogCtrl"
	})
	.when("/viewProfile",{
		templateUrl:"user\\Profile.html",
		controller:"userCtrl"
	})
	.when("/viewBlogs",{
		templateUrl:"blog\\Blogs.html",
		controller:"blogOperCtrl"
	})
	.when("/allBlogs",{
		templateUrl:"blog\\AllBlogs.html",
		controller:"blogOperCtrl"
	})
	.when("/addJob",{
		templateUrl:"jobs\\JobForm.html",
		controller:"JobCtrl"
	})
	.when("/viewJobs",{
		templateUrl:"jobs\\AllJobs.html",
		controller:"JobCtrl"
	})
	.when("/blogDetails/:blogsId",{
		templateUrl:"blog\\BlogDetails.html",
		controller:"blogOperCtrl"
	}).when("/myBlogs",{
		templateUrl:"blog\\MyBlogs.html",
		controller:"blogOperCtrl"
	}).when("/suggestedUsers",{
		templateUrl:"friend\\SuggestedUsers.html",
		controller:"FriendCtrl"
	}).when("/pendingRequests",{
		templateUrl:"friend\\PendingRequests.html",
		controller:"FriendCtrl"
	}).when("/listOfFriends",{
		templateUrl:"friend\\FriendList.html",
		controller:"FriendCtrl"
	}).when("/test",{
		templateUrl:"user\\test.html",
		controller:"userCtrl"
	}).when("/chat",{
		templateUrl:"chat\\Chat.html",
		controller:"ChatCtrl"
	})
	;


});



app.run(function($rootScope,$cookieStore,$http){

	console.log('run function of app module')
	console.log($rootScope.currentUser);

	/*if($cookieStore.get('userDetails')!=undefined && rootScope.currentUser==undefined){
		console.log("logging user automatically");
		$http.post("http://localhost:8574/CollMiddleware/login",$cookieStore.get('userDetails'));
		/*	$rootScope.currentUser={};
	}
	else{

		console.log("annonymous user");

	}*/
	if($rootScope.currentUser==undefined){
		$rootScope.currentUser=$cookieStore.get('userDetails');
	}
	if($rootScope.notification==undefined){
		$rootScope.notification=$cookieStore.get('notificationDetails');
		$rootScope.notificationCount=$cookieStore.get('notificationCounts');
	}
	else{

		console.log($rootscope.currentUser.email);
		console.log($rootScope.currentUser.role);
	}


});

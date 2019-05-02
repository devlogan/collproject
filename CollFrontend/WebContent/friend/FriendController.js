app.controller('FriendCtrl',function($scope,FriendService,$location){

	console.log("I am in Friend Controller");


	$scope.getSuggestedUsers=function(){
		FriendService.getSuggestedUsers()
		.then(
				function(response){
					console.log("retrieved suggested users");
					$scope.suggestedUsers=response.data;
					console.log($scope.suggestedUsers);

				},function(response){

					if(response.status==401)
						$location.path('/login');
				}

		)
	}

	$scope.addFriend=function(toId){
		FriendService.addFriend(toId)
		.then(
				function(response){
					$scope.getSuggestedUsers();
				},
				function(response){
					if(response.status==401)
						$location.path('/login');
				})
	}

	$scope.getPendingRequests=function(){
		FriendService.getPendingRequests().then(
				function(response){
					$scope.pendingRequests=response.data //Array of Friend Object[{fromId,toId,status,friendId},{}]
					console.log($scope.pendingRequests);
				},

				function(response){
					if(response.status==401)
						$location.path('/login')
				})
	}

	$scope.acceptRequest=function(pendingRequest){
		FriendService.acceptRequest(pendingRequest).then(
				function(response){

					$scope.getPendingRequests();
				},
				function(response){
					if(response.status==401)
						$location.path('/login')
				})
	}

	$scope.deleteRequest=function(pendingRequest){
		FriendService.deleteRequest(pendingRequest).then(
				function(response){

					$scope.getPendingRequests();
				},
				function(response){
					if(response.status==401)
						$location.path('/login')
				})
	}

	$scope.getAllFriends=function(){
		FriendService.getAllFriends().then(
				function(response){
					console.log("fetched friend list");
					$scope.friends=response.data;
				},function(response){
					if(response.status==401)
						$location.path('/login')
				})
	}




})
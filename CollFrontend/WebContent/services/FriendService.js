app.factory('FriendService',function($http){

	const SERVER_URL="http://localhost:8574/CollMiddleware";
	var friendService={};


	friendService.getSuggestedUsers=function(){
		return $http.get(SERVER_URL + "/suggestedUsers")
	}


	friendService.addFriend=function(toId){
		return $http.post(SERVER_URL + "/addFriend",toId)
	}

	friendService.getPendingRequests=function(){
		return $http.get(SERVER_URL + "/pendingRequests")
	}

	friendService.acceptRequest=function(pendingRequest){
		return $http.put(SERVER_URL + "/acceptRequest",pendingRequest)
	}

	friendService.deleteRequest=function(pendingRequest){
		return $http.put(SERVER_URL +"/deleteRequest",pendingRequest)
	}


	friendService.getAllFriends=function(){
		return $http.get(SERVER_URL + "/listOfFriends")
	}
	return friendService;




})
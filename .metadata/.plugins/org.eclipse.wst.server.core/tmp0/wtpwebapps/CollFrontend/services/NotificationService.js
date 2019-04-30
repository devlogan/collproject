app.factory('NotificationService',function($http){

	var notificationService={};
	const SERVER_URL="http://localhost:8574/CollMiddleware";

	notificationService.getMyNotifications=function(){
		return $http.get(SERVER_URL+"/getAllNotification");
	}

	notificationService.updateNotification=function(notId){
		return $http.get(SERVER_URL+"/updateNotification?notificationId="+notId);
	}

	return notificationService;
})

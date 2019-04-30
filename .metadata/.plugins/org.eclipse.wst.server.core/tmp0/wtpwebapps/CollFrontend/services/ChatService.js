app.filter('reverse', function() {
	return function(items) {
		return items.slice().reverse();
	};
});

app.directive('ngFocus', function() {
	return function(scope, element, attrs) {
		element.bind('click', function() {
			$('.' + attrs.ngFocus)[0].focus();
		});
	};
});

app.factory('ChatService',function($rootScope){

	var socket=new SockJS("/CollMiddleware/chatmodule")
	var stompClient=Stomp.over(socket);

	stompClient.connect('','',function(frame){
		alert('in connect with service')
		$rootScope.$broadcast('sockConnected',frame)
	})
	return{
		stompClient:stompClient
	}
})
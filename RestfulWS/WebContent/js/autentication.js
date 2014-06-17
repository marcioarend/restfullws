var myApp = angular.module('myAuth', []);

myApp.constant('AUTH_EVENTS', {
	loginSuccess : 'auth-login-success',
	loginFailed : 'auth-login-failed',
	logoutSuccess : 'auth-logout-success',
	sessionTimeout : 'auth-session-timeout',
	notAuthenticated : 'auth-not-authenticated',
	notAuthorized : 'auth-not-authorized'
});

myApp.constant('USER_ROLES', {
	all : '*',
	admin : 'admin',
	editor : 'editor',
	guest : 'guest'
});

myApp.factory('LoginInfo',function (){
	functions = {};
	var infoLogin = {};
		functions.setLoginInfos  = function (Login){
			infoLogin = Login;
		};
		functions.getLoginInfos = function (){
			return infoLogin;
		};
	
	return functions;
	
});

myApp.factory('AuthService', function($http, Session,LoginInfo,$window) {
	return {
		login : function(credentials) {
			return $http.post('/RestfulWS/service/login', credentials).then(function(res) {
				LoginInfo.setLoginInfos(res.data);
				Session.create(res.sessionID, res.id , res.role);
			});
		},
		isAuthenticated : function() {
			return !!Session.userId;
		},
		isAuthorized : function(authorizedRoles) {
			if (!angular.isArray(authorizedRoles)) {
				authorizedRoles = [ authorizedRoles ];
			}
			return (this.isAuthenticated() && authorizedRoles
					.indexOf(Session.userRole) !== -1);
		}
	};
});

myApp.service('Session', function() {
	this.create = function(sessionId, userId, userRole) {
		this.sessionId = sessionId;
		this.userId = userId;
		this.userRole = userRole;
	};
	this.destroy = function() {
		this.sessionId = null;
		this.userId = null;
		this.userRole = null;
	};
	return this;
});


myApp.controller('LoginController', function($scope, $rootScope, AUTH_EVENTS,AuthService) {
	$scope.credentials = {
		username : '',
		password : ''
	};
	$scope.login = function(credentials) {
		AuthService.login(credentials).then(function() {
			$rootScope.$broadcast("AUTH",AUTH_EVENTS.loginSuccess);
		}, function() {
			$rootScope.$broadcast("AUTH",AUTH_EVENTS.loginFailed);
		});
	};
	
});


myApp.controller('ApplicationController', function($scope, USER_ROLES,
		AuthService,LoginInfo,$window) {
	$scope.currentUser = null;
	$scope.userRoles = USER_ROLES;
	$scope.isAuthorized = AuthService.isAuthorized;
	
	$scope.showLoginInfos = function(){
			console.log($window.sessionStorage.token);
			
	};
});



myApp.factory('authHttpResponseInterceptor',['$q','$location','LoginInfo',function($q,$location,LoginInfo){
	return {
		request: function (config){
			config.headers =  {};
			
			return config;
		},
        response: function(response){
        	console.log($q);
            if (response.status === 401) {
                console.log("Response 401");
            }
            return response || $q.when(response);
        },
        responseError: function(rejection) {
            if (rejection.status === 401) {
                console.log("Response Error 401",rejection);
                $location.path('/login').search('returnTo', $location.path());
            }
            return $q.reject(rejection);
        }
    };
}]);

myApp.config(['$httpProvider',function($httpProvider) {
    //Http Intercpetor to check auth failures for xhr requests
    $httpProvider.interceptors.push('authHttpResponseInterceptor');
}]);



/*
myApp.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/', {
		templateUrl : '/index2.html',
		controller : 'UsersLoadController'
	}).when('/createuser', {
		templateUrl : '/angular/user/userCreate.html',
		controller : 'UserCreateController'
	}).otherwise({
		redirectTo : '/user'
	});
}]);
*/



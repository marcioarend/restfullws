var myApp = angular.module("myApp",['ngResource']);

myApp.factory('Data', function(){
	return {message: "eu venho de um servico"};
});

myApp.factory('facUtilits',function(){
	functions = {};
	functions.reverte = function(value){
		return value;
		
	};
	return functions;
});

myApp.filter('utils', function(){
	return function (Data){
		return Data.split("").reverse().join("");
	};
	
});

myApp.controller("myController" , function($scope){
	
	
	
});


myApp.controller("FirstController", function($scope,Data,facUtilits){
	
	$scope.data= facUtilits.reverte(Data);
	$scope.LogFunction = function(){
		console.log(Data);
	};
	
	
});


myApp.controller("SecondController", function($scope,Data){
	
	$scope.data= Data;
	 $scope.reversedMessage = function(message) {
		 return message.split("").reverse().join("");
		 };
	
});

var myApp = angular.module('myApp', ['ngResource']);



myApp.factory('BandFactory', function ($resource){
	return $resource('/RestfulWS/service/bandas');
});


myApp.controller('myController', function($scope,BandFactory){
	
	$scope.buscarBandas = function(){
		
		BandFactory.query(function(data){
			$scope.bands = data;
		});
	};
	


});
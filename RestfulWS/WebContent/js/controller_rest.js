var myApp = angular.module('myApp',['ngResource','ngRoute' ]);



myApp.factory('headersFactory',function(){
	return {"service_key":"",
		    "auth_token" :""	
		};
	
});

myApp.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/', {
		templateUrl : '/RestfulWS/Login/login_part.html',
		controller : 'crtlLogin'
	}).when('/comparation',{
		templateUrl : '/RestfulWS/SLP/comparation.html',
		controller : 'ComparationCtrl'
	}).when('/login',{
		templateUrl : '/RestfulWS/Login/login_part.html',
		controller : 'crtlLogin'
	}).otherwise({
		redirectTo : '/'
	});
}]);











myApp.factory('RESTConnection', function($resource,headersFactory){
	var defaultUrl = '/RestfulWS/service/';
	var defaultUrlTest = defaultUrl + "tests/";
	var defaultUrlLogin = defaultUrl + "restLogin/"; 
	var defaultUrlSLP = defaultUrl + "SLP/";
	var defaultUrlGeschaeft = defaultUrl + "geschaeft/"
	
	function transformResponse(headersGetters){
		console.log("transform");
		var temp = angular.fromJson(headersGetters);
		headersFactory.auth_token = temp.auth_token;
		console.log(headersFactory);
	}
	
	 // O parametro id será usado mais vezes, por isso fica como default	
	var teste = $resource(defaultUrl+':id', {id:"@id"},
	// declarando os métodos
	{ 
		'get' :{
				method:'GET',
				headers: headersFactory,
				params:{name:"@name"}, // aqui sao passados os parametros extras
				url:defaultUrlTest+':id/:name', // a url tem que ser montada novamente com os novos parametros
				headers: headersFactory,
				isArray:false // aqui ele espera um elemento como retorno
				},
		'getSLP':{
				method:'GET', // se nao tiver nenhuma paramentro no GET, ele vai pegar a lista dos elementos.
				url:defaultUrlSLP + "allSLP",
				headers: headersFactory,
				isArray:false // aqui ele espra uma lista como retorno
				},
		'getSLPById':{
				method:'GET',
				params:{begin:'@begin', end:'@end',bdlId:'@bdlId', gl:'@gl'},
				url:defaultUrlSLP + "getSLPByID/:begin:/:end/:bdlId/:gl",
				headers:headersFactory,
				isArray:false
				},
		'getAllGeschaeft':{
				method:'GET',
				url:defaultUrlGeschaeft,
				headers:headersFactory,
				isArray:true
			
				},		
		'list':{
				method:'GET', // se nao tiver nenhuma paramentro no GET, ele vai pegar a lista dos elementos.
				url:defaultUrlTest,
				headers: headersFactory,
				isArray:true // aqui ele espra uma lista como retorno 
				},
		'save':{
				method:'POST',
				params:{name:"@name",endereco:"@endereco"},
				url:defaultUrlTest,
				headers: headersFactory,
				isArray:false
			    },
		'actualize':{
				 method:'PUT',
				 params:{name:"@name",endereco:"@endereco"},
				 url:defaultUrlTest,
				 headers: headersFactory,
				 isArray:false
				},	    
		'remove':{
				method:'DELETE',
				url:defaultUrlTest,
				headers: headersFactory,
				},
		'login':{
				method:'POST',
				url:defaultUrlLogin+ "login/",
				headers: headersFactory,
				transformResponse:transformResponse,
				},
		'logout':{
				method:'POST'
				}		
				
	});
	return teste;
	
})

    


myApp.factory("errors", function(){
    return	function(message){
    		temp = angular.fromJson(message);
            console.log(temp.status);
            console.log(temp.statusText);
            alert( "Error " + temp.status + " Message " + temp.statusText);
            
     };
});

myApp.factory("success", function(){
    return	function(message){
            console.log(message);
     };
});


myApp.controller('crtlLogin', ['$scope','$location','headersFactory','RESTConnection','success','errors', function($scope,$location,headersFactory,RESTConnection,success,errors){
	
	$scope.functionLogin = function(){
		var login = RESTConnection;
		var userService = "f80ebc87-ad5c-4b29-9366-5359768df5a1";
		var adminService = "3b91cab8-926f-49b6-ba00-920bcf934c2a";
		if ($scope.user.name == "admin"){
			headersFactory.service_key =  adminService;
		}else {
			headersFactory.service_key =  userService;
		}
		login.login({username:$scope.user.name,password:$scope.user.pwd});
		$location.path('/comparation/');
	}
	
	
	
	$scope.functionTestAuth = function(){
		teste = RESTConnection;
		
		var temp = teste.getSLP().$promise.then(
		        function( value ){
		        	var temp = angular.fromJson(value);
		        	console.log(temp.dates);
		       	}, errors
		      );
		
		
		
//		teste.get({id:'1',name:'Antonio'});
//		teste.save({name:'Paula'});
//		teste.remove({id:1});
//		teste.actualize({id:1},{name:'Paula',endereco:'Rua das margaridas'});
		//console.log(teste.list());
	}
	
}])


//************************ comparation ****************************

myApp.filter('percentage', ['$filter', function ($filter) {
	return function (input, decimals) {
		return $filter('number')(input * 100, decimals) + '%';
	};
}]);


function miniFlex(){
			var tarif = {};
			var returnValue = 0;
			var demand = "";
			var peakHighDemand     = 0.25;
			var standardHighDemand = 0.08;
			var offPeakHighDemand   = 0.04;
			
			var peakLowDemand      = 0.09;
			var standardLowDemand  = 0.07;
			var offPeakLowDemand   = 0.04;
			
			var peak;
			var standard;
			var offPeak;
			
			var localName = "";
			tarif.getName = function(){
				return localName;
			}
			tarif.setName = function (name){
				localName = name;
			}

			tarif.calculate = function(uhrzeit, wert,datum ){
					returnValue = 0;
					
				    if ( datum.getMonth() >=3 &&  datum.getMonth() <= 8){
				    	demand= "low";
				    	
				    	peak= peakLowDemand;
				    	standard = standardLowDemand;
				    	offPeak = offPeakLowDemand;
				    } else {
				    	demand = "high";
				    	peak= peakHighDemand;
				    	standard = standardHighDemand;
				    	offPeak = offPeakHighDemand;
				    }
				    
				    returnValue = offPeakFunction(uhrzeit,wert,returnValue,datum);
					returnValue = peakFunction(uhrzeit,wert,returnValue,datum);
					returnValue = standardFunction(uhrzeit,wert,returnValue,datum);
					
					return returnValue;
			}

			function peakFunction(uhrzeit, wert,value,datum){
				if ( datum.getDay() != 0 && datum.getDay() != 6  ){
					if ("08:00" <= uhrzeit && uhrzeit < "10:00" ){
						return wert * peak;
						
					}else if ("18:00" <= uhrzeit && uhrzeit < "20:00" ){
						return wert * peak;
					}
				}	
				return value;
			}

			function standardFunction(uhrzeit, wert,value,datum){
				if ( datum.getDay() != 0 && datum.getDay() != 6  ){
					if ("07:00" <= uhrzeit && uhrzeit < "08:00" ){
						return wert * standard;
					}else if ("10:00" <= uhrzeit && uhrzeit < "18:00" ){
						return wert * standard;
					}else if ("20:00" <= uhrzeit && uhrzeit < "22:00" ){
						return wert * standard;
					}
				}else if (datum.getDay() == 6  ){//Samstag
					if ("08:00" <= uhrzeit && uhrzeit < "12:00" ){
						return wert * standard;
					}else if ("18:00" <= uhrzeit && uhrzeit < "20:00" ){
						return wert * standard;
					}
				
				}	
				return value;
			}

			function offPeakFunction(uhrzeit, wert,value,datum){
				if ( datum.getDay() != 0 && datum.getDay() != 6  ){
					if ("00:00" <= uhrzeit && uhrzeit < "07:00" ){
						return wert * offPeak;
					}else if ("22:00" <= uhrzeit && uhrzeit < "24:00" ){
						return wert * offPeak;
					}
					//Samstag
				}else if (datum.getDay() == 6  ){
					if ("12:00" <= uhrzeit && uhrzeit < "18:00" ){
						return wert * offPeak;
					}else if ("20:00" <= uhrzeit && uhrzeit < "24:00" ){
						return wert * offPeak;
					}else if ("00:00" <= uhrzeit && uhrzeit < "08:00" ){
						return wert * offPeak;
					}
					//Sonntag
				}else if (datum.getDay() == 0  ){
					return wert * offPeak;
				}

				return value;
			}
			return tarif;
}

function fixTarif(){
			var tarif = {};
			var returnValue = 0;
			var peak     = 0.25;
			
			var localName = "";
			tarif.getName = function(){
				return localName;
			}
			tarif.setName = function (name){
				localName = name;
			}


			tarif.calculate = function(uhrzeit, wert){
				    return wert * peak ;
			}

			return tarif;
}



function TagUndNacht(){
		var tarif = {};
		var returnValue = 0;
		var peakHighDemand     = 0.25;
		var offPeakHighDemand   = 0.04;
		
		var peakLowDemand      = 0.09;
		var offPeakLowDemand   = 0.04;
		
		var peak;
		var offPeak;
		
		var localName = "";
			tarif.getName = function(){
				return localName;
			}
			tarif.setName = function (name){
				localName = name;
			}

		tarif.calculate = function(uhrzeit, wert,datum ){
				    returnValue = 0;
				    
				    
				    if (  datum.getMonth() >=3 &&  datum.getMonth() <=8){
				    	demand= "low";
				    	peak= peakLowDemand;
				    	offPeak = offPeakLowDemand;
				    } else {
				    	demand = "high";
				    	peak= peakHighDemand;
				    	offPeak = offPeakHighDemand;
				    }
				    
				    
				    returnValue = offPeakFunction(uhrzeit,wert,returnValue,datum);
					returnValue = peakFunction(uhrzeit,wert,returnValue,datum);
					return returnValue;
			}

			function peakFunction(uhrzeit, wert,value,datum){
				if ( datum.getDay() != 0 && datum.getDay() != 6  ){
					if ("07:00" <= uhrzeit && uhrzeit < "18:00" ){
						return wert * peak;
					}
				}	
				return value;
			}

			function offPeakFunction(uhrzeit, wert,value,datum){
				if ( datum.getDay() != 0 && datum.getDay() != 6  ){
					if ( ("00:00" <= uhrzeit && uhrzeit < "07:00") || ("18:00" <= uhrzeit && uhrzeit < "24:00") ){
						return wert * offPeak;
					}
				} else  {
					return wert * offPeak;
				}

				return value;
			}

			return tarif;
}


myApp.factory("TarifFactory",function(){
	var functions= {};
	functions.getTarif = function(id){
		var tarif ={};
		if (id == 1 ){
			tarif = miniFlex();

		} else if ( id == 2){
			tarif = fixTarif();
		} else if ( id == 3){
			tarif = TagUndNacht();
		}

		return tarif;
	}

	return functions;
})
        





myApp.controller("ComparationCtrl", function($scope,$http,RESTConnection,TarifFactory,profilesData,SPL){
	
	var connection = RESTConnection;
	

	
	$scope.profile = connection.getAllGeschaeft();
//	SPL.get({service:'SLP'},function(data){
//		console.log(data);
//	});
	
	
	$scope.profilonchange = function(){
		var MiniFlex = TarifFactory.getTarif(1);
		MiniFlex.setName("MiniFlex");
		var fixTarifin = TarifFactory.getTarif(2);
		fixTarifin.setName("Fix Tarif");
		var tagUndNachtTarif = TarifFactory.getTarif(3);
		tagUndNachtTarif.setName("Tag und Nacht");
		$scope.totalFix = 0;
		$scope.totalMiniFlex = 0;
		$scope.totalTagUndNacht = 0;
		
		connection.getSLPById({begin:'2013-01-01', end:'2013-12-31',bdlId:'1', gl:$scope.myProfil.id},function(data){
//		SPL.get({service:'SLP', begin:'2013-01-01', end:'2013-12-31',bdlId:'4', gl:$scope.myProfil.id},function(data){
    		var teste = data.dates;
    		var summeMiniFlex = 0;
    	  	var summePauschalpreis = 0;
    	  	var summeTagUndNacht = 0;
    	  	var watt = 0;
	    	var werten;
	    	for (var i = 0; i < teste.length  ; i++) {
				temp = new Date(teste[i].d);
				werten = teste[i].v;
					for (var j=0; j < werten.length   ; j++) {
//							 console.log(" Zeit " + werten[j].z  + " Wert "+ werten[j].w + " date " + temp)
							 summeMiniFlex +=  MiniFlex.calculate(werten[j].z,werten[j].w,temp);
							 summePauschalpreis += fixTarifin.calculate(werten[j].z,werten[j].w,temp);
							 summeTagUndNacht +=  tagUndNachtTarif.calculate(werten[j].z,werten[j].w,temp);
							 watt += werten[j].w * 1;
					};

			};

			$scope.komparation = { tarifName : "MiniFlex",
								    pauschal :  ((summePauschalpreis/summeMiniFlex) -1 ), 
              						miniflex  : ((summeMiniFlex/summeMiniFlex)-1), 
              						tagundnacht : (( summeTagUndNacht/summeMiniFlex)-1)
									};
			console.log($scope.komparation);						
			
			$scope.totalFix = summePauschalpreis;
			$scope.totalMiniFlex = summeMiniFlex;
			$scope.totalTagUndNacht = summeTagUndNacht;
			
			console.log("watt" + watt);
  
    	});



	}

   
  	

});

function rechnen(anfangId, endId, stundenBenutzt ,watt, data){
		var totalWatt = watt * stundenBenutzt;
		var zwischen = endId - anfangId;
		var proStund = totalWatt/zwischen;
		for (; anfangId < endId; anfangId++) {
		 	data[anfangId][1]+= proStund;
		};

}

myApp.factory("profilesData",function($resource){
	var result = $resource('/RestfulWS/service/geschaeft'); 
	return result.query(function(data){
			return (data);
		}
	);
});

myApp.factory("SPL",function($resource){
	//var result = $resource('/RestfulWS/service/SLP/2013-01-01/2013-12-31/4/7');
    return $resource('/RestfulWS/service/:services/:begin/:end/:bdlId/:gl', {services:'SLP',begin:'', end:'',bdlId:'', gl:''}, {
        query: { method: 'GET', params: {}, isArray: true   }
    });

	
	
	
//	return result.query(function(data){
//			 console.log(data);
//			 //dates = data.dates;
//			 for( var j=0; j < dates.length; j ++ ){
//				 datum = dates[j].d;
//				 values = dates[j].v;
//				 console.log(datum);
//				 for ( var k=0; k < values.length; k ++ ){
//					 console.log(values[k]);
//				 }
//			 }
//		
//	});
	
});


function UhrzeitToId(Uhrzeit){
	var id = Uhrzeit.substring(0,2);

	return parseInt(id);

}

myApp.controller("dataController",function($scope,fakeData){

            $scope.wochenGraphData = fakeData;
            // var temp = fakeData[0].values;
            // temp[0]=["01:00",35];
            // fakeData[0].values = temp; 

            // console.log(temp);

     
})




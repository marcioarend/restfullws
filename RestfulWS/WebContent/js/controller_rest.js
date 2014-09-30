var myApp = angular.module('myApp',['ngResource','ngRoute' ]);



myApp.factory('headersFactory',function(){
	return {"service_key":"",
		    "auth_token" :""	
		};
	
});

myApp.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/', {
		templateUrl : '/RestfulWS/Partials/SLP/comparation.html',
		controller : 'ComparationCtrl'
	}).when('/comparation',{
		templateUrl : '/RestfulWS/Partials/SLP/comparation.html',
		controller : 'ComparationCtrl'
	}).when('/login',{
		templateUrl : '/RestfulWS/Partials/Login/login_part.html',
		controller : 'crtlLogin'
	}).when('/bericht',{
		templateUrl : '/RestfulWS/Partials/bericht/bericht.html',
		controller : 'crtlBericht'
	}).when('/tarifKunde',{
		templateUrl : '/RestfulWS/Partials/bericht/tarifKunde.html',
		controller : 'crtlBericht'
	}).when('/tarifvergleichen',{
		templateUrl : '/RestfulWS/Partials/bericht/tarifvergleichen.html',
		controller : 'crtlBericht'
	})			
	.otherwise({
		redirectTo : '/'
	});
}]);

myApp.factory('RESTConnection', function($resource,headersFactory){
	var defaultUrl = '/RestfulWS/service/';
	var defaultUrlTest = defaultUrl + "tests/";
	var defaultUrlLogin = defaultUrl + "restLogin/"; 
	var defaultUrlSLP = defaultUrl + "SLP/";
	var defaultUrlGeschaeft = defaultUrl + "geschaeft/"
	var defaultUrlBericht = defaultUrl + "bericht/"
	var defaultUrlTarif = defaultUrl + "tarif/"
	
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
		'getGesamtumsatz':{
				method:'GET',
				hedaders:headersFactory,
				url:defaultUrlBericht + "gesamtumsatz",
				isArray:false
				},
		'getTarifKunde':{
				method:'GET',
				hedaders:headersFactory,
				url:defaultUrlBericht + "kundetarifverbrauch",
				isArray:false
				},		
		'getTarif':{
				method:'GET',
				params:{id:'@id'},
				hedaders:headersFactory,
				url:defaultUrlTarif + "tarif/:id" ,
				isArray:false
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
    		datum = angular.fromJson(message);
            console.log(datum.status);
            console.log(datum.statusText);
            alert( "Error " + datum.status + " Message " + datum.statusText);
            
     };
});

myApp.factory("success", function(){
    return	function(message){
            console.log(message);
     };
});



myApp.factory("kundeTarif",function(){
	var temp = [];
	var functions = {};
	
	functions.setData = function(value){
		temp = value;
	}
	
	functions.getData = function(){
		return temp;
	}
	
	return functions;
})

myApp.controller('crtlBericht',['$scope','RESTConnection','kundeTarif',function($scope,RESTConnection,kundeTarif){
	
	var vergleich = function(value1,value2,T){
		if(value1<value2){
			T.mehr();
			var  r = (((value2)/value1) -1)*100;
			if ( r >= 10){
				T.mehrAls10();
			}
		}else if (value1>value2 ){
			T.weniger();
			var  r = (((value1)/value2) -1)*100;
			if ( r >= 10){
				T.wenigerAls90();
			}
		} else {
			T.gleiche();
		}
		
	}
	
	
	
	
	$scope.tarifonchange = function(){
		var tarifIdZuVergleichen = $scope.tarifModel;
		var T1 = new tarifKunde();
		var T2 = new tarifKunde();
		var T3 = new tarifKunde();
		
		if ( tarifIdZuVergleichen != "*"){
			var temp = kundeTarif.getData();
			for (i=0; i < temp.length; i++){
				vergleich( temp[i].values[tarifIdZuVergleichen].gesamt, temp[i].values[0].gesamt,T1);
				vergleich( temp[i].values[tarifIdZuVergleichen].gesamt, temp[i].values[1].gesamt,T2);	
				vergleich( temp[i].values[tarifIdZuVergleichen].gesamt, temp[i].values[2].gesamt,T3);
			}
			T1.setKunde(temp.length);
			T2.setKunde(temp.length);
			T3.setKunde(temp.length);
		}

		$scope.T1 = T1;
		$scope.T2 = T2;
		$scope.T3 = T3;
		
		
	}
	
	
	RESTConnection.getGesamtumsatz(function(data){
		$scope.berichts=data.values;
	});
	
	RESTConnection.getTarifKunde(function(data){
		kundeTarif.setData(data.values);
		$scope.kundeTarifen=data.values;
		var temp = data.values;
		
		
		
		
		
		
	});
	
	
}])

var tarifKunde = function(){
	var anzahlKunde = 0;
	var mehr = 0;
	var weniger = 0;
	var gleiche = 0;
	var mehrAls10 = 0;
	var wenigerAls90 = 0;
	this.mehr = function(){
		mehr++;
	}
	this.getMehr = function(){
		return mehr;
	}
	this.weniger = function(){
		weniger ++;
	}
	this.getWeniger = function(){
		return weniger;
	}
	this.gleiche = function(){
		gleiche ++;
	}
	this.getGleiche = function(){
		return gleiche;
	}
	this.mehrAls10 = function(){
		mehrAls10 ++;
	}
	
	this.getMehrAls10 = function(){
		return mehrAls10;
	}
	
	this.getMehrAls10Prozent = function(){
		return (mehrAls10/anzahlKunde)*100;
	}
	this.wenigerAls90 = function(){
		wenigerAls90 ++;
	}
	
	this.getWenigerAls90 = function(){
		return wenigerAls90;
	}
	
	this.getWenigerAls90Prozent = function(){
		return (wenigerAls90/anzahlKunde)*100;
	}
	this.getMehrInProzent = function(){
		return (mehr/anzahlKunde)*100;
	}
	this.getWenigerInProzent = function(){
		return (weniger/anzahlKunde)*100;
	}
	this.getGleicheInProzent = function(){
		return (gleiche/anzahlKunde)*100;
	}
	this.setKunde = function(value){
		anzahlKunde = value;
	}
}



myApp.controller('crtlLogin', ['$scope','$location','headersFactory','RESTConnection','success','errors', 
                               function($scope,$location,headersFactory,RESTConnection,success,errors){
	
	
		
	
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
		
		teste.getSLP().$promise.then(
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

//******************** Tarif mit REST *****************************
var Season = function(){
	var wochentag = [] ;
	var samstag = [] ;
	var sonntag =[] ;
	// 0 LowDemand 1 highdemand;
	var type = 0;

	this.getType = function(){
		return type;
	}

	// 0 LowDemand 1 highdemand;
	this.setType = function(value){
		type = value;
	}

	this.setWochentag = function(preis){
		wochentag.push(preis);
	}
	this.setWochentagJson = function(listPreis){
		for ( i=0; i < listPreis.length; i++){
			var preis = new Preis();
			preis.setPreis(listPreis[i].preis);
			preis.setGueltigkeitJson(listPreis[i].uhr);
			wochentag.push(preis);
			
		}
	}
	
	this.getWochentag = function(){
		return wochentag;
	}

	this.setSamstag =function(preis){
		samstag.push(preis);
	}

	this.setSamstagJson = function(listPreis){
		for ( i=0; i < listPreis.length; i++){
			var preis = new Preis();
			preis.setPreis(listPreis[i].preis);
			preis.setGueltigkeitJson(listPreis[i].uhr);
			samstag.push(preis);
			
		}
		
	}
	
	this.setSonntag = function(preis){
		sonntag.push(preis);
	}
	
	this.setSonntagJson = function(listPreis){
		for ( i=0; i < listPreis.length; i++){
			var preis = new Preis();
			preis.setPreis(listPreis[i].preis);
			preis.setGueltigkeitJson(listPreis[i].uhr);
			sonntag.push(preis);
			
		}
		
	}
	


	this.getPreis = function(day,uhr){
		if ( day.getDay() !=0 && day.getDay() != 6 ){
			var i=0;
			for (i=0; i < wochentag.length; i++){
				if (wochentag[i].isGueltig(uhr)){
					return wochentag[i];
				}	
			}
			return null;
		}else if ( day.getDay() == 0 ){
			var i=0;
			for (i=0; i < sonntag.length; i++){
				if (sonntag[i].isGueltig(uhr)){
					return sonntag[i];
				}
			}
			return null;
		} else  {
			var i=0;
			for (i=0; i < samstag.length; i++){
				if ( samstag[i].isGueltig(uhr)){
					return samstag[i];	
				}
			}
			return null;
		}

	}

};


var Preis = function(){
	var preis = 0;
	var uhrAnfang = [];
	var uhrEnde  = [];
	var name = "";

	this.setName = function(n){
		name  = n;
	}

	this.getName = function(){
		return name;
	}

	this.setPreis = function(p){
		preis = p;
	}

	this.getPreis = function(){
		return preis;
	}	

	this.getUhr = function(){
		return [uhrAnfang,uhrEnde];
	}
	
	this.setGueltigkeitJson = function(listUhr){
		// First element will allways the begin 
		uhrAnfang = listUhr[0];
		uhrEnde = listUhr[1];
		
	}
	
	this.setGueltigkeit = function(anfang, ende){
		uhrAnfang.push(anfang);
		uhrEnde.push(ende);
	}
	this.isGueltig = function(uhr){
		var i = 0;
		for ( i=0; i < uhrAnfang.length; i++){
			if (uhrAnfang[i] <= uhr && uhr < uhrEnde[i] ){
				return true;
			}
		}
		return false;

	}



};




var Tarif = function(){
	var Season = [];
	var lowDemandSeasonBegin = 0;
	var lowDemandSeasonEnd = 0;
	var name = "";
	
	this.setName = function(n){
		name = n;
	}
	
	this.getName = function(){
		return name;
	}
	
	this.setBeginEndSeason = function(begin, end){
		lowDemandSeasonBegin = begin;
		lowDemandSeasonEnd = end;
	}
	
	this.getPreis = function(Monate, Uhr){
		// 0 LowDemand 1 highdemand;
		var type = 1;
		if ( lowDemandSeasonBegin <= Monate.getMonth() +1  &&  Monate.getMonth() +1 < lowDemandSeasonEnd){
			type = 0;
		}
		for (i=0; i < Season.length; i++){
			if ( Season[i].getType() == type ){
				return Season[i].getPreis(Monate,Uhr);
			}
		}		    	
	}

	this.calculate = function(Monate, Uhr, Wert){
		var preis = this.getPreis(Monate, Uhr);
		return preis.getPreis() * Wert;
	}
	
	this.setSeason = function(s){
		Season.push(s);
	}

};





myApp.factory("loadTarifFromRest", function(RESTConnection) {
	
	var tarifRest = RESTConnection;
	var highSeason = new Season();
	var lowSeason = new Season();
	var tarif = new Tarif();
	tarifRest.getTarif({id:1}).$promise.then(
			function(value){
				var tarifObj = angular.fromJson(value);
				var highDemandTemp = tarifObj.HighDemand;
				var lowDemandTemp  = tarifObj.LowDemand;
				highSeason.setWochentagJson(highDemandTemp.wochentag);
				highSeason.setSamstagJson(highDemandTemp.samstag)
				highSeason.setSonntagJson(highDemandTemp.sonntag);
				highSeason.setType(highDemandTemp.type);
				
				lowSeason.setWochentagJson(lowDemandTemp.wochentag);
				lowSeason.setSamstagJson(lowDemandTemp.samstag);
				lowSeason.setSonntagJson(lowDemandTemp.sonntag);
				lowSeason.setType(lowDemandTemp.type);
				
				tarif.setSeason(highSeason);
				tarif.setSeason(lowSeason);
				tarif.setBeginEndSeason(tarifObj.monateAnfang, tarifObj.monateEnde);
				tarif.setName(tarifObj.name);
	
			}
	
	)
	return tarif;
	
})


myApp.factory("loadTarifFromRest2", function(RESTConnection) {
	
	var tarifRest = RESTConnection;
	var highSeason = new Season();
	var lowSeason = new Season();
	var tarif = new Tarif();
	tarifRest.getTarif({id:2}).$promise.then(
			function(value){
				var tarifObj = angular.fromJson(value);
				var highDemandTemp = tarifObj.HighDemand;
				var lowDemandTemp  = tarifObj.LowDemand;
				highSeason.setWochentagJson(highDemandTemp.wochentag);
				highSeason.setSamstagJson(highDemandTemp.samstag)
				highSeason.setSonntagJson(highDemandTemp.sonntag);
				highSeason.setType(highDemandTemp.type);
				
				lowSeason.setWochentagJson(lowDemandTemp.wochentag);
				lowSeason.setSamstagJson(lowDemandTemp.samstag);
				lowSeason.setSonntagJson(lowDemandTemp.sonntag);
				lowSeason.setType(lowDemandTemp.type);
				
				tarif.setSeason(highSeason);
				tarif.setSeason(lowSeason);
				tarif.setBeginEndSeason(tarifObj.monateAnfang, tarifObj.monateEnde);
				tarif.setName(tarifObj.name);
	
			}
	
	)
	return tarif;
	
})


myApp.factory("loadTarifFromRest3", function(RESTConnection) {
	
	var tarifRest = RESTConnection;
	var highSeason = new Season();
	var lowSeason = new Season();
	var tarif = new Tarif();
	tarifRest.getTarif({id:3}).$promise.then(
			function(value){
				var tarifObj = angular.fromJson(value);
				var highDemandTemp = tarifObj.HighDemand;
				var lowDemandTemp  = tarifObj.LowDemand;
				highSeason.setWochentagJson(highDemandTemp.wochentag);
				highSeason.setSamstagJson(highDemandTemp.samstag)
				highSeason.setSonntagJson(highDemandTemp.sonntag);
				highSeason.setType(highDemandTemp.type);
				
				lowSeason.setWochentagJson(lowDemandTemp.wochentag);
				lowSeason.setSamstagJson(lowDemandTemp.samstag);
				lowSeason.setSonntagJson(lowDemandTemp.sonntag);
				lowSeason.setType(lowDemandTemp.type);
				
				tarif.setSeason(highSeason);
				tarif.setSeason(lowSeason);
				tarif.setBeginEndSeason(tarifObj.monateAnfang, tarifObj.monateEnde);
				tarif.setName(tarifObj.name);
	
			}
	
	)
	return tarif;
	
})






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
        





myApp.controller("ComparationCtrl", function($scope,$http,RESTConnection,TarifFactory,profilesData,SPL,loadTarifFromRest,loadTarifFromRest2,loadTarifFromRest3){
	
	var connection = RESTConnection;
	

	
	$scope.profile = connection.getAllGeschaeft();
//	SPL.get({service:'SLP'},function(data){
//		console.log(data);
//	});
	
	
	$scope.profilonchange = function(){
		var MiniFlex =loadTarifFromRest;
		var fixTarifin = loadTarifFromRest3;
		var tagUndNachtTarif = loadTarifFromRest2;

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
				datum = new Date(teste[i].d);
				werten = teste[i].v;
					for (var j=0; j < werten.length   ; j++) {
//							 console.log(" Zeit " + werten[j].z  + " Wert "+ werten[j].w + " date " + datum)
							 summeMiniFlex +=  MiniFlex.calculate(datum,werten[j].z,werten[j].w);
							 summePauschalpreis += fixTarifin.calculate(datum,werten[j].z,werten[j].w); 
							 summeTagUndNacht += tagUndNachtTarif.calculate(datum,werten[j].z,werten[j].w); 
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




var myApp = angular.module('myApp',['ngResource']);



myApp.factory('headersFactory',function(){
	return {"service_key":"",
		    "auth_token" :""	
		};
	
});



myApp.factory('RESTConnection', function($resource,headersFactory){
	var defaultUrl = '/RestfulWS/service/';
	var defaultUrlTest = defaultUrl + "tests/";
	var defaultUrlLogin = defaultUrl + "restLogin/"; 
	var defaultUrlSLP = defaultUrl + "SLP/";
	
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
				url:defaultUrlSLP,
				headers: headersFactory,
				isArray:false // aqui ele espra uma lista como retorno
			
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

    

myApp.controller('crtlTest',function(headersFactory){
	//console.log(headersFactory);
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


myApp.controller('crtlLogin', ['$scope','headersFactory','RESTConnection','success','errors', function($scope,headersFactory,RESTConnection,success,errors){
	
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
		
	}
	
	
	
	$scope.functionTestAuth = function(){
		teste = RESTConnection;
		
		var temp = teste.getSLP().$promise.then(
		        function( value ){
		        	var temp = angular.fromJson(value);
		        	console.log(temp.dates);
		       	}, errors
		      )
		
		
		
//		teste.get({id:'1',name:'Antonio'});
//		teste.save({name:'Paula'});
//		teste.remove({id:1});
//		teste.actualize({id:1},{name:'Paula',endereco:'Rua das margaridas'});
		//console.log(teste.list());
	}
	
}])


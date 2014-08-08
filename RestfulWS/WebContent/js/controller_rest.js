var myApp = angular.module('myApp',['ngResource']);


    
myApp.controller('crtl1', ['$resource', function($resource){
	//$resource(url, [paramDefaults], [actions], options);
	var defaultUrl = '/RestfulWS/service/tests/';
											 // O parametro id será usado mais vezes, por isso fica como default	
	var teste = $resource(defaultUrl+':id', {id:"@id"},
	// declarando os métodos
	{ 
		'get' :{
				method:'GET',
				params:{name:"@name"}, // aqui sao passados os parametros extras
				url:defaultUrl+':id/:name', // a url tem que ser montada novamente com os novos parametros 
				isArray:false // aqui ele espera um elemento como retorno
				},
		'list':{
				method:'GET', // se nao tiver nenhuma paramentro no GET, ele vai pegar a lista dos elementos.
				isArray:true // aqui ele espra uma lista como retorno 
				},
		'save':{
				method:'POST',
				params:{name:"@name",endereco:"@endereco"},
				isArray:false
			    },
		'actualize':{
				 method:'PUT',
				 params:{name:"@name",endereco:"@endereco"},
				 isArray:false
				},	    
		'remove':{
				method:'DELETE'
				}	    
		
	});
	
	teste.get({id:'1',name:'Antonio'});
	teste.save({name:'Paula'});
	teste.remove({id:1});
	teste.actualize({id:1},{name:'Paula',endereco:'Rua das margaridas'});
	teste.list();

} ]);    

/**
 * IncendioService.js
 */
angular.module('app')
.factory('incendioService',['serviceUtils', '$rootScope', function(serviceUtils, $rootScope) {
	
	var service = {};	
	
	/**
	 * Consulta o webService e faz o parse da resposta
	 */
	service.consultarCep = function(obj){			
		var promisse = serviceUtils.consultarCep(obj.cep);
		if(!serviceUtils.isNullOrEmpty(promisse)){
			$('.loader').show();
			promisse.success(function(data){
				$('.loader').hide();
				obj.endereco = data.logradouro;
				obj.bairro = data.bairro;
				obj.uf = data.uf;
				obj.cidade = data.localidade;
				$rootScope.$digest();
			}).error(function(erro){
				$('.loader').hide();
				console.log(erro);
			});
		}
	}
	
	service.isNullOrEmpty = function(obj){
		return serviceUtils.isNullOrEmpty(obj);
	}
			
	return service;
	
}]);
angular.module('app').controller('IncendioController', ['$scope', '$http', '$confirm', '$acaoSistema', '$message', 'incendioService',
                                                        function($scope, $http,$confirm, $acaoSistema, $message, incendioService) {	
	
	var $modelConfirm = new $confirm();
	var service = incendioService;
	$scope.$acao = new $acaoSistema();
	$scope.$acao.acaoIncluir();
	$scope.tipoForm = 'calculo';
	$scope.obj = {};
	
	$scope.proximo = function(){
		$scope.tipoForm = 'dadosComplementar';
	}
	
	/**
	 * Consulta Cep por WebService.
	 */
	$scope.buscarCep = function(){
		if(!service.isNullOrEmpty($scope.obj.cep)){
			service.consultarCep($scope.obj);
		}
		
	}

	
	
}]);
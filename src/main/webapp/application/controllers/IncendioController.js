angular.module('app').controller('IncendioController', ['$scope', '$http', '$confirm', '$acaoSistema', '$message',
                                                        function($scope, $http,$confirm, $acaoSistema, $message) {	
	
	var $modelConfirm = new $confirm();
	$scope.$acao = new $acaoSistema();
	$scope.$acao.acaoIncluir();
	$scope.tipoForm = 'calculo';
	
	$scope.proximo = function(){
		$scope.tipoForm = 'dadosComplementar';
	}

	
	
	
}]);
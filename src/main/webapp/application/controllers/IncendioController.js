angular.module('app').controller('IncendioController', ['$scope', '$rootScope', '$http', '$confirm', '$acaoSistema', '$message',
                                                        function($scope, $rootScope, $http,$confirm, $acaoSistema, $message) {	
	
	var $modelConfirm = new $confirm();
	$scope.$acao = new $acaoSistema();
	$scope.$acao.acaoIncluir();
	$rootScope.tituloPainel = 'Incêndio';
}]);
/*
 * LoginController.js
 * 
 * 
 * @author Paulo Leonardo de O. Miranda.
 * 
 */
angular.module('app').controller('LoginController', ['$scope', '$http', '$confirm', '$message', 'Credential' , '$location', function($scope, $http, $confirm, $message, credential, $location) {	
	var $modelConfirm = new $confirm();	
	
	$scope.usuario = {};

	/** 
	 * Realiza o logout do Usuário da aplicaçao. 
	 */
	$scope.logout = function() {
		$modelConfirm.addConfirm({msg : 'Deseja realmente sair do sistema?', actionYes : function() {
			$http.post('usuario/logout').success(function(data) {
				credential.invalidate();
				$location.path('/login');
			}).error(function(data) {
				$message.addMsgDanger(data);
			});
		}});
	}
	
	/**
	 * Autentica o Usuário segundo o 'login' e 'senha' informado.
	 */
	$scope.login = function() {
		$http.post('usuario/login', $scope.usuario).success(function(data, status, headers, config) {
			credential.init(data.nome, data.token, data.acessRoles);
			
			$location.path('/principal');
		}).error(function(data, status, headers, config) {
			$message.addMsgDanger(data);
		});
	}

}]);
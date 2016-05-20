angular.module('app')
.controller('PerfilController', ['$scope', '$http', '$message', function($scope, $http, $message) {	

	$scope.perfis = [];
	
	/**
	 * Retorna a lista de perfil.
	 */
	var lista = function() {
		$http.get('perfil/lista').success(function(data) {
			$scope.perfis = data;
		}).error(function(data) {
			$message.addMsgDanger(data);
		});
	}
	
	/**
	 * Retorna o valor da chave do perfil.
	 */
	$scope.getDescricaoPerfil = function(perfil) {
		for ( var chave in $scope.perfis) {
			switch (perfil) {
				case chave:
					return $scope.perfis[chave];
					break;
			}
		}
		
	}
	
	
	lista();
		
}]);

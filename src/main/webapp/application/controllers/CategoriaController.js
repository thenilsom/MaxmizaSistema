angular.module('app')
.controller('CategoriaController', ['$scope', '$http', function($scope, $http) {	

	$scope.categorias = [];
	
	/**
	 * Recebe uma categoria como parametro e retorna o valor
	 * referente a sua chave
	 */
	$scope.getDescricaoCategoria = function(categoria) {
		  for ( var chave in $scope.categorias) {
		   switch (categoria) {
		    case chave:
		     return $scope.categorias[chave];
		     break;
		   }
		  }
		  
		 }
	
	/**
	 * Obtem a lista de categorias para cadastro de clientes 
	 */
	var lista = function() {
		$http.get('cliente/categorias').success(function(data){
			$scope.categorias = data;
		}).error(function(data){
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

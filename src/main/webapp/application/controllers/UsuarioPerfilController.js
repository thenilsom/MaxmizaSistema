/**
 * Controller responsavel por gerenciar as alteração no usuario da sessão 
 */
angular.module('app').controller('UsuarioPerfilController', ['$scope', '$http', '$acaoSistema','$location', 'Credential', '$message', function($scope, $http, $acaoSistema,$location,credential, $message) {	
	
	$scope.$acao = new $acaoSistema();
	$scope.$acao.acaoVisualizar();

	
	/**
	 * Carrega as informações do usuario na sessão
	 */
	var perfil = function() {
		$http.post('usuario/visualizarPerfil').success(function(data) {
			$scope.usuario = data;
		}).error(function(data) {
			$message.addMsgDanger(data);
		});
	}
		
	/**
	 * Altera o 'Usuario' na base de dados, e faz o logout para uma nova altenticação
	 */
	$scope.salvar = function() {
		$http.post('usuario/salvar', { usuario: $scope.usuario }).success(function(data) {
			logout();
			$message.addMsgInf(data);
		}).error(function(data) {
			$message.addMsgDanger(data);
		});
	}
	
	/** 
	 * Realiza o logout do Usuário da aplicaçao. 
	 */
	function logout() {
		$http.post('usuario/logout').success(function(data) {
				credential.invalidate();
				$location.path('/login');
				$scope.$apply();
			}).error(function(data) {
				$message.addMsgDanger(data);
			});
	}
	
	/**
	 * Apresenta o formulário de 'Usuario' para edição.
	 */
	$scope.alterar = function(usuario) {
		$scope.usuario = angular.copy(usuario);
		$scope.$acao.acaoAlterar();
	}
	
	/**
	 * Carrega novamente o usuario na sessão para visualização
	 */
	$scope.cancelar = function(){
		perfil();
		$scope.$acao.acaoVisualizar();
	}
	
	
	/* o perfil do usuario sera carregado ao abrir o caso de uso. */
	perfil();
	
}]);
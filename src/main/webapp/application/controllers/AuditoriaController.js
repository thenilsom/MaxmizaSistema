/*
 * AssuntoController.js
 *
 *
 * @author Samuel Oliveira.
 * 
 */
angular.module('app').controller('AuditoriaController', ['$scope', '$http', '$acaoSistema', '$message', function($scope, $http, $acaoSistema , $message) {	
	
	$scope.$acao = new $acaoSistema();
	$scope.$acao.acaoListar();
	$scope.listaTabela = [];
	$scope.filtro = {};
	$scope.acoes = [];
	$scope.usuarios = [];
	$scope.tabelas = [];
	
	//Inicia Ordem Reversa
	$scope.reverse = true;
	
	/**
	 * Retorna a lista com todas as Ultimas 100 Auditorias existentes na base de dados.
	 */
	var listar = function() {
		$http.get('auditoria/listarUltimas').success(function(data) {
			$scope.listaTabela = data;
		}).error(function(data) {
			$message.addMsgDanger(data);
		});
	}
	

	/**
	 * Filtra a Auditoria de acordo com o filtro
	 */
	$scope.filtrar = function(){
		$http.post('auditoria/filtro', { filtro: $scope.filtro }).success(function(data){
			
			$scope.listaTabela = data;

		}).error(function(data){
			$message.addMsgDanger(data);
		});
	}
	
	/**
	 * Recebe uma acao como parametro e retorna o valor
	 * referente a sua chave
	 */
	$scope.getDescricaoAcao = function(acao) {
		  for ( var chave in $scope.acoes) {
		   switch (acao) {
		    case chave:
		     return $scope.acoes[chave];
		     break;
		   }
		  }
		  
		 }
	
	/**
	 * Recebe uma tabela como parametro e retorna o valor
	 * referente a sua chave
	 */
	$scope.getDescricaoTabela = function(tabela) {
		  for ( var chave in $scope.tabelas) {
		   switch (tabela) {
		    case chave:
		     return $scope.tabelas[chave];
		     break;
		   }
		  }
		  
		 }
	
	/**
	 * Obtem a lista de acoes para Filtro 
	 */
	var listaAcoes = function() {
		$http.get('auditoria/acoes').success(function(data){
			$scope.acoes = data;
		}).error(function(data){
			$message.addMsgDanger(data);
		});
	}
	
	/**
	 * Obtem a lista de Usuários para Filtro 
	 */
	var listaUsuarios = function() {
		$http.get('usuario/listarTudo').success(function(data){
			$scope.usuarios = data;
		}).error(function(data){
			$message.addMsgDanger(data);
		});
	}
	
	/**
	 * Obtem a lista de Tabelas para Filtro 
	 */
	var listaTabelas = function() {
		$http.get('auditoria/tabelas').success(function(data){
			$scope.tabelas = data;
		}).error(function(data){
			$message.addMsgDanger(data);
		});
	}
		
	/* A lista será inicializada ao abrir o caso de uso. */
	listar();
	listaAcoes();
	listaUsuarios();
	listaTabelas();
	
}]);
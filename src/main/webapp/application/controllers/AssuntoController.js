/*
 * AssuntoController.js
 *
 *
 * @author Samuel Oliveira.
 * 
 */
angular.module('app').controller('AssuntoController', ['$scope', '$http', '$acaoSistema', '$message', '$confirm', function($scope, $http, $acaoSistema, $message, $confirm) {	
	
	var $modelConfirm = new $confirm();
	$scope.$acao = new $acaoSistema();
	$scope.$acao.acaoListar();
	$scope.listaTabela = [];

	/**
	 * Retorna a lista com todos os 'Assuntos' existentes na base de dados.
	 */
	var listar = function() {
		$http.get('assunto/listarTudo').success(function(data) {
			$scope.listaTabela = data;
		}).error(function(data) {
			$message.addMsgDanger(data);
		});
	}
	
	/**
	 * Apresenta o formulário de inclusão de 'Assunto'.
	 */
	$scope.incluir = function() {
		$scope.assunto = {};
		$scope.$acao.acaoIncluir();
	}
	
	/**
	 * Apresenta o formulário de 'Assunto' para edição do Assunto.
	 */
	$scope.alterar = function(assunto) {
		$scope.assunto = angular.copy(assunto);
		$scope.$acao.acaoAlterar();
	}
	
	/**
	 * Apresenta o formulário de 'Assunto' com campos Disable.
	 */
	$scope.visualizar = function(assunto) {
		$scope.assunto = assunto;
		$scope.$acao.acaoVisualizar();
	}
	
	/**
	 * Volta para o fluxo de listagem.
	 */
	$scope.cancelar = function() {
		$scope.assunto = {};
		$scope.$acao.acaoListar();
	}
	
	/**
	 * Salva o 'Assunto' na base de dados e volta para o fluxo de listagem.
	 */
	$scope.salvar = function() {
		$http.post('assunto/salvar', { assunto: $scope.assunto }).success(function(data) {
			
			$scope.assunto = undefined;
			$scope.$acao.acaoListar();
			listar();
			
			$message.addMsgInf(data);
		}).error(function(data) {
			$message.addMsgDanger(data);
		});
	}
	
	/**
	 * Exclui o 'Assunto' na base de dados e volta para o fluxo de listagem.
	 */
	$scope.excluir = function(assunto) {
		
		$modelConfirm.addConfirm({msg : 'Confirma Exclusão do Assunto ' + assunto.descricao + '?', actionYes : function() {
	
			$http.post('assunto/excluir', { assunto: assunto }).success(function(data) {

				listar();
				
				$message.addMsgInf(data);
			}).error(function(data) {
				$message.addMsgDanger(data);
			});
		}});
	}
	
	/* A lista será inicializada ao abrir o caso de uso. */
	listar();
	
}]);
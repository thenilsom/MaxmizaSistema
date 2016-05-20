/*
 * PontoController.js
 *
 *
 * @author Samuel Oliveira.
 * 
 */
angular.module('app').controller('PontoController', ['$rootScope','$scope', '$http', '$acaoSistema', '$message', '$confirm', function($rootScope, $scope, $http, $acaoSistema, $message, $confirm) {	
	
	var $modelConfirm = new $confirm();
	$scope.$acao = new $acaoSistema();
	$scope.$acao.acaoListar();
	$scope.filtro;
	$scope.listaTabela = [];
	$scope.periodo = {dia : ''};

	/**
	 * Retorna a lista com todos os 'Pontos' existentes na base de dados.
	 */
	$scope.listar = function() {
		if(!$scope.filtro){
			carregaFiltro();
		}
		$http.post('ponto/listarTudo', { filtro: $scope.filtro }).success(function(data) {
			$scope.listaTabela = data;
		}).error(function(data) {
			$message.addMsgDanger(data);
		});
	}
	
	
	
	/**
	 * Apresenta o formulário de inclusão de 'Ponto'.
	 */
	$scope.incluir = function() {
		$http.get('ponto/novo').success(function(data) {
				$scope.ponto = data;
				$scope.periodo.dia = 'ENTRADA';
				$scope.$acao.acaoIncluir();
			}).error(function(data) {
				$message.addMsgDanger(data);
			});
	}
	
	/**
	 * Carrega as Informações de Filtro
	 */
	var carregaFiltro = function() {
		$scope.filtro = {};
		var date = new Date();
		var ano  = date.getFullYear();
		var mes  = date.getMonth();
		var primeiroDia = new Date(ano, mes, 1).getDate();
		var ultimoDia = new Date(ano, mes + 1, 0).getDate();
		mes++;
		if(mes<10){
			mes = "0"+mes;
		}
		$scope.filtro.dataInicial = "0"+primeiroDia+"/"+mes+"/"+ano;
		$scope.filtro.dataFinal = ultimoDia+"/"+mes+"/"+ano;
	}
	
	/**
	 * Obtem a lista de Usuários para Filtro 
	 */
	var listaUsuarios = function() {
		$http.get('usuario/listarTudo').success(function(data){
			$scope.usuarioList = data;
			var user = $rootScope.$security.currentUserName();
			for(var i=0;i < $scope.usuarioList.length; i++){
				if(user === $scope.usuarioList[i].nome){
					$scope.filtro.usuario = $scope.usuarioList[i];
					break;
				}
			}
		}).error(function(data){
			$message.addMsgDanger(data);
		});
	}
	
	/**
	 * Obtem a lista de Periodos 
	 */
	var listaPeriodos = function() {
		$http.get('ponto/periodos').success(function(data){
			$scope.periodoList = data;
		}).error(function(data){
			$message.addMsgDanger(data);
		});
	}
	
	/**
	 * Volta para o fluxo de listagem.
	 */
	$scope.cancelar = function() {
		$scope.ponto = {};
		$scope.$acao.acaoListar();
	}
	
	/**
	 * Salva o 'Ponto' na base de dados e volta para o fluxo de listagem.
	 */
	$scope.salvar = function() {
		
		switch ($scope.periodo.dia) {
		
		case 'ENTRADA':
			if(!$scope.ponto.entradaDia){
				$scope.ponto.entradaDia = $scope.ponto.dataAtual;
			}else{
				$message.addMsgDanger("Ponto Já foi Registrado Nesse Período.");
				return;
			}
			break;
		
		case 'SAIDA':
			if(!$scope.ponto.saidaDia){
				$scope.ponto.saidaDia = $scope.ponto.dataAtual;
			}else{
				$message.addMsgDanger("Ponto Já foi Registrado Nesse Período.");
				return;
			}
			break;
		
		case 'SAIDAALMOCO':
			if(!$scope.ponto.saidaAlmoco){
				$scope.ponto.saidaAlmoco = $scope.ponto.dataAtual;
			}else{
				$message.addMsgDanger("Ponto Já foi Registrado Nesse Período.");
				return;
			}
			break;

		case 'RETORNOALMOCO':
			if(!$scope.ponto.entradaAlmoco){
				$scope.ponto.entradaAlmoco = $scope.ponto.dataAtual;
			}else{
				$message.addMsgDanger("Ponto Já foi Registrado Nesse Período.");
				return;
			}
			break;
		}
		
		$http.post('ponto/salvar', { ponto: $scope.ponto }).success(function(data) {
			
			$scope.ponto = undefined;
			$scope.$acao.acaoListar();
			$scope.listar();
			
			$message.addMsgInf(data);
		}).error(function(data) {
			$message.addMsgDanger(data);
		});
	}
	
	
	/* A lista será inicializada ao abrir o caso de uso. */
	listaUsuarios();
	$scope.listar();
	listaPeriodos();
	
}]);
/*
 * RelatorioGeralController.js
 *
 *
 * @author Danillo Santana.
 * 
 */
angular.module('app').controller('RelatorioGeralController', ['$scope', '$rootScope', '$http', '$message', '$confirm','$window' ,
         function($scope, $rootScope , $http, $message, $confirm,$window) {	
	
	$scope.periodo = {};
	$scope.dataInicioInputInvalido = false;
	$scope.dataFimInputInvalido = false;
	
	/**
	 *Gera relatório geral de acordo com as período passado
	 */
	$scope.gerar = function() {
		if(validarDados()){
			$http.post('relatorios/gerarRelatorioGeral', $scope.periodo).success(function(data) {
				var usuario = $rootScope.$security.currentUserName().replace(' ', '');
				var pdf = "http://www.autocomsistemas.com.br/saa/resources/Relatorios/"+"relatorio_geral_"+usuario+".pdf";
				$window.open(pdf , '_self');
				$message.addMsgInf("Relatório Gerado Com Sucesso.");
			}).error(function(data) {
				$message.addMsgDanger(data);
			});
		}	
	}
	
	/**
	 *  Verifica se os campos de preenchimento Obrigatório foram preenchidos..
	 */
	var validarDados =  function(){
		
		var flag1 = $scope.validaDataInicio();
		var flag2 = $scope.validaDataFim();
		
		if(flag1 && flag2 ){
			return true;
		}
		
		return false;
	}
	
	/**
	 * Verifica se o campo data inicio foi preenchido corretamente.
	 * */
	$scope.validaDataInicio = function(){
		if($scope.periodo.dataInicio === undefined ){
			$scope.dataInicioInputInvalido = true;
			return false;
		}
		$scope.dataInicioInputInvalido = false;
		return true;
	}
	
	/**
	 * Verifica se o campo data fim foi preenchido corretamente.
	 * */
	$scope.validaDataFim = function(){
		if($scope.periodo.dataFim === undefined ){
			$scope.dataFimInputInvalido = true;
			return false;
		}
		$scope.dataFimInputInvalido = false;
		return true;
	}
	
}]);
angular.module('app').controller('ClienteController', ['$scope', '$http', '$confirm', '$acaoSistema', '$message', function($scope, $http,$confirm, $acaoSistema, $message) {	
	
	var $modelConfirm = new $confirm();
	$scope.$acao = new $acaoSistema();
	$scope.$acao.acaoListar();
	$scope.listaTabela = [];
	
	/* variaveis para controlar os filtros na listagem de clientes*/
	$scope.filtro = {
		ativo : true,	
		categoria : null,
	};
	
	/**
	 * Filtro Cliente.
	 * Cria uma condinção para cada filtro desejado
	 */
	 $scope.filtroCliente = function(data) {
         var condicao1 = $scope.filtro.categoria == null || $scope.filtro.categoria == data.categoria;
         var condicao2 = $scope.filtro.ativo     == data.ativo;
         
         if(condicao1 && condicao2) {
             return true;
         }
         // else :
         return false;
     }
	
	/**
	 * Retorna a lista com todos os 'Clientes' existentes na base de dados.
	 */
	var listar = function() {
		$http.get('cliente/listarTudo').success(function(data) {
			$scope.listaTabela = data;
		}).error(function(data) {
			$message.addMsgDanger(data);
		});
	}
	
	/**
	 * Apresenta o formulário de inclusão de 'Cliente'.
	 */
	$scope.incluir = function() {
		$scope.cliente = {};
		$scope.cliente.ativo = true;
		$scope.$acao.acaoIncluir();
	}
	
	
	/**
	 * Salva o 'Cliente' na base de dados e volta para o fluxo de listagem.
	 */
	$scope.salvar = function(){
		$http.post('cliente/salvar', { cliente: $scope.cliente }).success(function(data){
			
			$scope.cliente = undefined;
			$scope.$acao.acaoListar();
			listar();
			
			$message.addMsgInf(data);
		}).error(function(data){
			$message.addMsgDanger(data);
		});
	}
	
	/**
	 * Volta para o fluxo de listagem.
	 */
	$scope.cancelar = function(){
		$scope.cliente = undefined;
		$scope.$acao.acaoListar();
	}
	
	$scope.alterar = function(cliente){
		$scope.cliente = angular.copy(cliente);
		$scope.$acao.acaoAlterar();
	}
	
	/**
	 * Apresenta o formulário de 'Cliente' com campos Disable.
	 */
	$scope.visualizar = function(cliente) {
		$scope.cliente = cliente;
		$scope.$acao.acaoVisualizar();
	}
	
	/**
	 * Exclui o cliente na base de dados e da um reload na listagem.
	 */
	$scope.excluir = function(clienteExclusao){
		$modelConfirm.addConfirm({msg: 'Confirma exclusão do cliente ' + clienteExclusao.razaoSocial + '?', actionYes : function(){
			$http.post('cliente/excluir', { cliente : clienteExclusao}).success(function(data){
				listar();
				
				$message.addMsgInf(data);
			}).error(function(data){
				$message.addMsgDanger(data);
			});
		}});
	}
	
		
	/* A lista será inicializada ao abrir o caso de uso. */
	listar();
	
}]);
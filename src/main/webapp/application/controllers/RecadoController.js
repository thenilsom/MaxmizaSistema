/*
 * RecadoController.js
 *
 *
 * @author Denilson Godinho.
 * 
 */
angular.module('app').config(function(tagsInputConfigProvider) {
	 tagsInputConfigProvider
	 .setDefaults('tagsInput', 
	    {
	      addOnEnter: true,
	      addOnBlur : false
	    })
	  .setDefaults('autoComplete', {
		  loadOnFocus: true,
	  	})  
	}).controller('RecadoController', ['$scope', '$http', '$acaoSistema', '$message', '$confirm', '$rootScope',
	                                   function($scope, $http, $acaoSistema, $message, $confirm, $rootScope) {	
	
	var $modelConfirm = new $confirm();
	$scope.$acao = new $acaoSistema();
	$scope.$acao.acaoListar();
	$scope.listaTabela = [];
	$scope.usuarios;
	$scope.msgUsuarios = "";
	

	/**
	 * Retorna a lista com todos os 'Recados Recebidos' existentes na base de dados.
	 */
	$scope.listarRecadosRecebidos = function() {
		$http.get('recado/listarRecadosRecebidos').success(function(data) {
			$scope.listaTabela = data;
			$scope.tab = 1;
		}).error(function(data) {
			$message.addMsgDanger(data);
		});
	}
	
	/**
	 * Retorna a lista com todos os 'Recados Enviados' existentes na base de dados.
	 */
	$scope.listarRecadosEnviados = function() {
		$http.get('recado/listarRecadosEnviados').success(function(data) {
			$scope.listaTabela = data;
			$scope.tab = 2;
		}).error(function(data) {
			$message.addMsgDanger(data);
		});
	}
	
	/**
	 * Retorna a lista de usuarios ativos
	 **/
	var listarUsuarios = function(){
		$http.get('usuario/listarAtivos').success(function(data) {
		$scope.usuarios = data;
		}).error(function(data) {
			$message.addMsgDanger(data);
		});
	}
	
	/**
	 * Apresenta o formulário de inclusão de 'Recado'.
	 */
	$scope.incluir = function() {
		$scope.recado = {};
		$scope.recado.para = [];
		$scope.usuariosTagInputInvalido = false; 
		$scope.$acao.acaoIncluir();
	}
	
	/**
	 * Apresenta o formulário de 'Recado' para edição do Assunto.
	 */
	$scope.alterar = function(recado) {
		$scope.recado = angular.copy(recado);
		$scope.usuariosTagInputInvalido = false; 
		$scope.$acao.acaoAlterar();
	}
	
	/**
	 * Marca o recado como lido e apresenta o formulario para visualizacao.
	 */
	$scope.ler = function(recadoLido) {
		if(!recadoLido.lido){
			$http.post('recado/lerRecado', {recado: recadoLido}).success(function(data){
				$rootScope.badgeRecado --;
			}).error(function(data){
				$message.addMsgDanger(data);
			});
		}
		$scope.recado = recadoLido;
		$scope.$acao.acaoVisualizar();
	}
	
	/**
	 * Volta para o fluxo de listagem.
	 */
	$scope.voltar = function() {
		$scope.recado = {};
		if($scope.tab == 1){
			$scope.listarRecadosRecebidos();
		}else{
			$scope.listarRecadosEnviados();
		}
		$scope.$acao.acaoListar();
	}
	
	/**
	 * Salva o 'Recado' na base de dados e volta para o fluxo de listagem.
	 */
	$scope.salvar = function() {
		$http.post('recado/salvar', { recado: $scope.recado }).success(function(data) {
			
			$scope.recado = undefined;
			$scope.$acao.acaoListar();
			$scope.listarRecadosRecebidos();
			
			$message.addMsgInf(data);
		}).error(function(data) {
			$message.addMsgDanger(data);
		});
	}
	
	/**
	 * Exclui o 'Recado' na base de dados e volta para o fluxo de listagem.
	 */
	$scope.excluir = function(recado) {
		
		$modelConfirm.addConfirm({msg : 'Confirma Exclusão do Recado ' + recado.recado + '?', actionYes : function() {
	
			$http.post('recado/excluir', { recado: recado }).success(function(data) {
				if($scope.tab == 1){
					$scope.listarRecadosRecebidos();
				}else{
					$scope.listarRecadosEnviados();
				}
				
				$message.addMsgInf(data);
			}).error(function(data) {
				$message.addMsgDanger(data);
			});
		}});
	}
	
	/**
	 * Verifica se o campo para foi preenchido corretamente.
	 * */
	$scope.validaUsuario = function(){
		if($scope.recado.para.length === 0){
			$scope.usuariosTagInputInvalido = true;
			$scope.msgUsuarios = "Campo com preenchimento obrigatório.";
			return false;
		}else{
			for ( var chave in $scope.recado.para) {
				if($scope.recado.para[chave].id === undefined){
					$scope.recado.para.splice([chave],1);
				}
			}
		}
		
		$scope.usuariosTagInputInvalido = false;
		
		return true;
	}
	
	/**
	  * Carrega a lista de usuarios para AutoComplete
	  */ 
	$scope.loadUsuarios = function($query){
		return $scope.usuarios.filter(function(usuario){
			return usuario.nome.toLowerCase().indexOf($query.toLowerCase()) != -1;
		});
	}
	
	/* A lista será inicializada ao abrir o caso de uso. */
	$scope.listarRecadosRecebidos();
	listarUsuarios();
	
}]);
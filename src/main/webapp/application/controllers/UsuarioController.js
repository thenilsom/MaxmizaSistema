angular.module('app').controller('UsuarioController', ['$scope', '$http', '$confirm', '$acaoSistema', '$message', function($scope, $http,$confirm, $acaoSistema, $message) {	
	
	var $modelConfirm = new $confirm();
	$scope.$acao = new $acaoSistema();
	$scope.$acao.acaoListar();
	$scope.listaTabela = [];
	
	/* variaveis para controlar o filtro e ordem na listagem de clientes*/
	$scope.filtro = {
		ativo : true,	
		perfil : null,
	};
	
	/**
	 * Filtro Usuarios.
	 * Cria uma condinção para cada filtro desejado
	 */
	 $scope.filtroUsuario = function(data) {
         var condicao1 = $scope.filtro.perfil == null || $scope.filtro.perfil == data.perfil;
         var condicao2 = $scope.filtro.ativo  == data.ativo;
         
         if(condicao1 && condicao2) {
             return true;
         }
         // else :
         return false;
     }

	/**
	 * Retorna a lista com todos os 'Usuarios' existentes na base de dados.
	 */
	var listar = function() {
		$http.get('usuario/listarTudo').success(function(data) {
			$scope.listaTabela = data;
		}).error(function(data) {
			$message.addMsgDanger(data);
		});
	}
	
	/**
	 * Apresenta o formulário de inclusão de 'Usuario'.
	 */
	$scope.incluir = function() {
		$scope.usuario = {};
		$scope.usuario.ativo = true;
		$scope.usuario.perfil = 'TECNICO';
		$scope.$acao.acaoIncluir();
	}
	
	/**
	 * Apresenta o formulário de 'Assunto' com campos Disable.
	 */
	$scope.visualizar = function(usuario) {
		$scope.usuario = usuario;
		$scope.$acao.acaoVisualizar();
	}
	
	/**
	 * Apresenta o formulário de inclusão com os dados do 'Usuario' setados para alteração.
	 */
	$scope.alterar = function(usuario){
		$scope.usuario = usuario;
		$scope.$acao.acaoIncluir();
	}
	
	/**
	 * Volta para o fluxo de listagem.
	 */
	$scope.cancelar = function() {
		$scope.usuario = undefined;
		$scope.$acao.acaoListar();
	}
	
	/**
	 * Excluir o usuario na base de dados e volta para o fluxo de listagem.
	 */
	$scope.excluir = function(usuarioExclusao){
		$modelConfirm.addConfirm({msg: 'Confirma exclusão do usuário: '+usuarioExclusao.nome, actionYes : function(){
			$http.post('usuario/excluir', { usuario : usuarioExclusao}).success(function(data){
				
				$scope.$acao.acaoListar();
				listar();
				
				$message.addMsgInf(data);
			}).error(function(data){
				$message.addMsgDanger(data);
			});
		}});	
	}
		
	
	/**
	 * Salva o 'Usuario' na base de dados e volta para o fluxo de listagem.
	 */
	$scope.salvar = function() {
		$http.post('usuario/salvar', { usuario: $scope.usuario }).success(function(data) {
			
			$scope.usuario = undefined;
			$scope.$acao.acaoListar();
			listar();
			
			$message.addMsgInf(data);
		}).error(function(data) {
			$message.addMsgDanger(data);
		});
	}
	
	/* A lista será inicializada ao abrir o caso de uso. */
	listar();
	
}]);
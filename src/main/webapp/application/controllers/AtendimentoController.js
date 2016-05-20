/*
 * AtendimentoController.js
 * 
 * @author Danillo Santana D'Afonseca.
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
	})
	.controller('AtendimentoController' , ['$scope','$timeout', '$http', '$confirm', '$acaoSistema', 
       '$message','$security','$rootScope',
       function($scope,$timeout, $http,$confirm, $acaoSistema, $message, $security, $rootScope) {	
	
	var $modelConfirm = new $confirm();
	$scope.$acao = new $acaoSistema();
	$scope.$acao.acaoListar();
	$scope.modal = "listar";
	$scope.assuntos;
	$scope.usuarios;	
	$scope.clientes;	
	$scope.status ;
	$scope.categorias = [];
	$scope.listaTabela = [];
	$scope.filtroGeral = {};
	$scope.msgAssunto  = "";
	$scope.msgUsuarios = "";
	$scope.msgCliente  = "";
	$rootScope.badgeAtendimento;
	$rootScope.badgeRecado = 0;
	var auxCliente = [];
	var isModoFiltro = false;
	
	/* variaveis para controlar os filtros na listagem de atendimentos*/
	$scope.filtro = {	
			
		status : null,
		usuario : null
	};
	
	//variaveis para controlar o refresh da listagem
	var TEMPO_REFRESH = 10;
	var versaoAnterior;
	$scope.contador = TEMPO_REFRESH;
	$scope.promise;
	
	/**
	 * thread para ficar verificando se existe um novo atendimento,
	 * se existir atualiza a listagem
	 */
	function ativarRefresh(){
		$scope.contador--;
		if($scope.contador === 0){
			$(".loader").addClass('hidden');// parar a execução do popUp de carregamento
			versaoAnterior = angular.copy($scope.versao)
			versaoAtual();
			if($scope.versao > versaoAnterior){
				if(!isModoFiltro){
					listar();
				}
				qntAtendimentos();
			}	
			qntRecados();
			$scope.contador = TEMPO_REFRESH;
			$(".loader").removeClass('hidden');
		}
		
		$scope.promise = $timeout(ativarRefresh, 1000);
	}
	
	/**
	 * obtem a versao atual do atendimento de forma sincrona
	 */
	function versaoAtual(){
		$.ajax({
		    url: 'atendimento/versaoAtual',
		    method: "GET",
		    async: false
		}).done(function(data) {
		  $scope.versao = data.versaoAtual;
		});
	}
		
/**
 * ao sair do controller cancela a thread de verificação.*/
 
 $scope.$on(
             "$destroy",
             function( event ) {
            		//$timeout.cancel($scope.promise);
            		$(".loader").removeClass('hidden');
             }
         );

	
	/**
	 * Filtro Atendimento.
	 * Cria uma condinção para cada filtro desejado
	 */
	 $scope.filtroAtendimento = function(data) {
		 
         var condicao1 = $scope.filtro.status == null || $scope.filtro.status == data.status;
         var condicao2 = verificaUsuario(data.usuarios);
         
         if(condicao1 && condicao2) {
             return true;
         }

         return false;
     }
	 
	 /**
	  * Verifica Usuarios no Array para o Filtro
	  */
	 var verificaUsuario = function(usuarios) {
		 if($scope.filtro.usuario == null){
			 return true;
		 }
		
		 for (var i=0; i<usuarios.length; i++) {
			 if($scope.filtro.usuario.login     == usuarios[i].login ){
				 return true;
			 }
		 }
		 
		 return false;
	 }
	
	var inicializa = function(){
		$scope.assuntoTagInputInvalido  = false;
		$scope.usuariosTagInputInvalido = false; 
		$scope.clienteTagInputInvalido  = false;
		$scope.contatoTagInputInvalido  = false;
		$scope.invalidoSolucao          = false;
		$scope.invalidoProblema         = false;
	}
		
	/**
	 * Retorna a lista com todos os 'Atendimentos' existentes na base de dados.
	 */
	var listar = function() {
		$.ajax({
		    url: 'atendimento/listarTudo',
		    method: "GET",
		    async: false
		}).done(function(data) {
			
			$scope.listaTabela = data;
		});
		
	}
	
	
	/**
	 * Apresenta o formulário de inclusão de 'Atendimento'.
	 */
	$scope.incluir = function() {
		inicializa();
		carregarRelacionamentos();
		$http.get('atendimento/novo').success(function(data) {
				$scope.atendimento = data;
				$scope.atendimento.assuntos =[];
				$scope.atendimento.problema = '';
				$scope.atendimento.solucao = '';
				$scope.$acao.acaoIncluir();
			}).error(function(data) {
				$message.addMsgDanger(data);
			});
	}
	
	
	/**
	 * Apresenta o formulário de inclusão com os dados do 'Atendimento' setados para alteração.
	 */
	$scope.alterar = function(atendimento){
		inicializa();
		carregarRelacionamentos();
		$scope.atendimento = atendimento;
		$scope.$acao.acaoAlterar();
	}
	
	/**
	 * Volta para o fluxo de listagem.
	 */
	$scope.cancelar = function() {
		inicializa();
		$scope.atendimento = undefined;
		listar();
		$scope.$acao.acaoListar();
	}
	
	/**
	 * Excluir o atendimento na base de dados e volta para o fluxo de listagem.
	 */
	$scope.excluir = function(atendimentoExclusao){
		$modelConfirm.addConfirm({msg: 'Deseja realmente excluir este atendimento?', actionYes : function(){
			$http.post('atendimento/excluir', { atendimento : atendimentoExclusao}).success(function(data){
				$scope.$acao.acaoListar();
				listar();
				$message.addMsgInf(data);
			}).error(function(data){
				$message.addMsgDanger(data);
			});
		}});	
	}
	
	/**
	 * Apresenta o formulário de 'Atendimento' com campos Disable.
	 */
	$scope.visualizar = function(atendimento) {
		inicializa();
		carregarRelacionamentos();
		$scope.atendimento = atendimento;
		$scope.$acao.acaoVisualizar();
	}
	
	
	/**
	 * Faz As Validações e testes Antes de Salvar
	 */
	$scope.salvar = function() {
		if(validarDados() === true){
			if(validaSolucaoProblema() === true){
				if($scope.$acao.isAcaoIncluir() && verificaCLiente($scope.atendimento)){
					$modelConfirm.addConfirm({msg: 'Já Existe Atendimento Aberto para o Cliente '+$scope.atendimento.cliente.nomeFantasia+', deseja adicionar novo Atendimento?', actionYes : function(){
						salvarBanco();
					}});
					
				}else{
					salvarBanco();
				}
				
			}	
		}
	}
	
	/**
	 * Salva o 'Atendimento' na base de dados e volta para o fluxo de listagem.
	 */
	var salvarBanco = function(){
		$http.post('atendimento/salvar', { atendimento: $scope.atendimento }).success(function(data) {
			$scope.atendimento = undefined;
			$scope.$acao.acaoListar();
			listar();
			$message.addMsgInf(data);
		}).error(function(data) {
			$message.addMsgDanger(data);
		});
	}
	
	/**
	 * Filtra o Atendimento de acordo com o filtro
	 */
	$scope.filtrar = function(){
		$http.post('atendimento/filtro', {filtro: $scope.filtroGeral}).success(function(data){
			$scope.listaTabela = data;
			$scope.filtroGeral = {};
			isModoFiltro = true;
		}).error(function(data){
			$message.addMsgDanger(data);
		});
	}
	
	/**
	 * Carrega os relacionamentos do atendimento para os campos de autoComplete do filtro
	 */
	$scope.prepararFiltros = function(){
		carregarRelacionamentos();
	}
	
	/**
	 * reseta os parametros dos filtros e refaz a listagem
	 */
	$scope.fecharFiltro = function(){
		$scope.filtroGeral = {};
		isModoFiltro = false;
		listar();
	}
	
	/**
	 *  Verifica se os campos de preenchimento Obrigatório foram preenchidos..
	 */
	var validarDados =  function(){
		
		var flag1 = $scope.validaAssunto();
		var flag2 = $scope.validaUsuario();
		var flag3 = $scope.validaCliente();
		var flag4 = $scope.validaContato();
		
		if(flag1 && flag2 &&flag3 &&flag4){
			return true;
		}
		
		return false;
	}
	

	/**
	 * Verifica se o campo assunto foi preenchido corretamente.
	 * */
	$scope.validaAssunto = function(){
		if($scope.atendimento.assuntos.length === 0){
			$scope.assuntoTagInputInvalido = true;
			$scope.msgAssunto = "Campo com preenchimento obrigatório.";
			return false;
		}else{
			for ( var chave in $scope.atendimento.assuntos) {
				if($scope.atendimento.assuntos[chave].id === undefined){
					$scope.atendimento.assuntos.splice([chave],1);
				}
			}
		}

		$scope.assuntoTagInputInvalido = false;
		
		return true;
	}
	
	/**
	 * Verifica e Avisa Caso Já Exista Atendimento Aberto para esse Cliente
	 * */
	var verificaCLiente = function(atendimento){
		for (var i=0; i< $scope.listaTabela.length; i++) {
			var atend =  $scope.listaTabela[i];
			if(atend.status != "FINALIZADO"){
				if(atend.cliente.id === atendimento.cliente.id){
					return true
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Verifica se o campo usuário foi preenchido corretamente.
	 * */
	$scope.validaUsuario = function(){
		if($scope.atendimento.usuarios.length === 0){
			$scope.usuariosTagInputInvalido = true;
			$scope.msgUsuarios = "Campo com preenchimento obrigatório.";
			return false;
		}else{
			for ( var chave in $scope.atendimento.usuarios) {
				if($scope.atendimento.usuarios[chave].id === undefined){
					$scope.atendimento.usuarios.splice([chave],1);
				}
			}
		}
		
		$scope.usuariosTagInputInvalido = false;
		
		return true;
	}
	

	/**
	 * Verifica se o campo cliente foi preenchido corretamente.
	 * */
	$scope.validaCliente = function(){	
		if($scope.atendimento.cliente === undefined || $scope.atendimento.cliente.length === 0){
			$scope.clienteTagInputInvalido = true;
			$scope.msgCliente = "Campo com preenchimento obrigatório.";
			return false;
		}else{
			if(typeof $scope.atendimento.cliente === "string"){
				$scope.clienteTagInputInvalido = true;
				$scope.msgCliente = "Cliente: '"+$scope.atendimento.cliente+"' inválido";
				return false;
			}
			
		}
		
		$scope.clienteTagInputInvalido = false;
		
		return true;
	}

	/**
	 * Verifica se o campo contato foi preenchido corretamente.
	 * */
	$scope.validaContato = function(){
		if($scope.atendimento.contato === undefined || $scope.atendimento.contato.length === 0){
			$scope.contatoTagInputInvalido = true;
			return false;
		}
		$scope.contatoTagInputInvalido = false;
		return true;
	}
	

	/**
	 * Verifica se os campos solução e problema  foram preenchidos corretamente.
	 * */
	var validaSolucaoProblema = function(){
		if($scope.atendimento.status === "PENDENTE" || $scope.atendimento.status === "EM_ANDAMENTO"){
			return true;
		}else if($scope.atendimento.status === "FINALIZADO" && ( $scope.atendimento.solucao != ""  )
				&& ( $scope.atendimento.problema != ""  )  ){
			return true;
		}else{
			($scope.atendimento.solucao  === "" ) ? $scope.invalidoSolucao  =  true   : $scope.invalidoSolucao   =  false;
			($scope.atendimento.problema === "" ) ? $scope.invalidoProblema =  true   : $scope.invalidoProblema  =  false;
			return false;
		}
	}
		
	
	/**
	 * Retorna a lista de assuntos 
	 **/
	 var listarAssuntos = function(){
		$http.get('assunto/listarTudo').success(function(data) {
			$scope.assuntos = data;
		}).error(function(data) {
			$message.addMsgDanger(data);
		});
	}
	 
	 /**
	  * Carrega a lista de assuntos para AutoComplete
	  */ 
	$scope.loadAssuntos = function($query){
		return $scope.assuntos.filter(function(assunto){
			return assunto.descricao.toLowerCase().indexOf($query.toLowerCase()) != -1;
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
	  * Carrega a lista de usuarios para AutoComplete
	  */ 
	$scope.loadUsuarios = function($query){
		return $scope.usuarios.filter(function(usuario){
			return usuario.nome.toLowerCase().indexOf($query.toLowerCase()) != -1;
		});
	}
	
	
	/**
	 * Retorna a lista de clientes ativos
	 **/
	var listarClientes = function(){
		$http.get('cliente/listarAtivos').success(function(data) {
		$scope.clientes = data;
		}).error(function(data) {
		$message.addMsgDanger(data);
		});
	}	 	 
		
	/**
	 * Retorna a lista de status 
	 **/
	var listarStatus =  function(){
		$http.get('atendimento/listarStatus').success(function(data) {
			$scope.status = data;
		}).error(function(data) {
			$message.addMsgDanger(data);
		});
	}
	
	/**
	 * Retorna a Quantidade de Atendimentos Pendentes / Em Atendiemnto
	 **/
	var qntAtendimentos =  function(){
		$.ajax({
		    url: 'atendimento/qtdAtendimentos',
		    method: "GET",
		    async: false
		}).done(function(data) {
			$rootScope.badgeAtendimento = data;
		});
	}
	
	/**
	 * Retorna a Quantidade de Recados Não Lidos
	 */
	var qntRecados = function(){		
		$.ajax({
		    url: 'recado/qtdRecados',
		    method: "GET",
		    async: false
		}).done(function(data) {
			$rootScope.badgeRecado = data;
		});
	}
	
	/**
	 * Carrega a lista de relacionamentos usados nos autoCompletes.
	 */
	var carregarRelacionamentos = function(){
		if($scope.assuntos == null){listarAssuntos();}
		if($scope.clientes == null){listarClientes();}
		if($scope.usuarios == null){listarUsuarios();}
	}
	
	/**
	 * Adiciona o assunto selecionado na listagem do modal
	 * se o assunto ja existir será removido.
	 */
	$scope.adicionarAssunto = function(assunto){
		var assuntoIncluido = false;
	
		angular.forEach($scope.atendimento.assuntos, function(value, key){
			if(angular.equals(assunto.id, value.id)){
				assuntoIncluido = true;
				$scope.atendimento.assuntos.splice(key,1);
			}
		});
		
		if(!assuntoIncluido){
			$scope.atendimento.assuntos.push(assunto);
		}
	}
	
	/**
	 * Destaca a linha da lista de assuntos selecionados para o atendimento
	 */
	$scope.destacarLinha = function(assunto){
		var destacarLinha = false;
		angular.forEach($scope.atendimento.assuntos, function(value, key){
			if(angular.equals(assunto.id, value.id)){
				destacarLinha = true;
			}
		});
		return destacarLinha;
	}
	
	/*
	 * Controla a vizualização do modal de seleção de assuntos
	 */
	$scope.toogleAssunto = function(acao){
		$scope.assunto = {};
		if(angular.equals(acao,'incluir')){
			$scope.modal = "incluir";
		}
		if(angular.equals(acao,'listar')){
			$scope.modal = "listar";
		}
			
	}
	
	/*
	 * Incluir um novo assunto e adiciona-o a lista de assuntos do atendimento.
	 */
	$scope.novoAssunto = function(){
		$http.post('assunto/novoAssunto', { assunto: $scope.assunto }).success(function(data) {
			$scope.atendimento.assuntos.push(data);	
			listarAssuntos();
			$('#modalAssunto').modal('hide');
			$scope.modal = "listar";
		}).error(function(data) {
			$message.addMsgDanger(data);
		});
	}
	
		
	/* A lista será inicializada ao abrir o caso de uso. */
	listar();
	if(!$security.hasRoles('TECNICO')){
		listarUsuarios();
	}
	listarStatus();
	qntRecados();
	qntAtendimentos();
	versaoAtual();
	ativarRefresh();
	
}]);
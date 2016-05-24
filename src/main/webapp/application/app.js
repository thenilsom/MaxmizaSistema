/*

 * app.js
 * Copyright (c) AutoCom Sistemas.
 * 
 * Este software é confidencial e propriedade da AutoCom Sistemas.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da AutoCom Sistemas.
 * Este arquivo contém informações proprietárias.
 * 
 * @author Samuel Oliveira.
 */

var app = angular.module('app',['ngRoute', 'message', 'security', 'validation', 'directives','ngTagsInput','ui.bootstrap']);

/**
 * 
 * Definições de rotas de acesso as páginas da aplicação. 
 */
app.config(['$routeProvider', '$securityConfigProvider', '$validationProvider', function($routeProvider, $securityConfigProvider, $validationProvider){	
	
  /** Security Config */
  $securityConfigProvider.setCredentialNameStored('maxmizaCredentialStored');
  $securityConfigProvider.setNotAuthenticatedUrl('/login');
  $securityConfigProvider.setNotAuthorizedUrl('/principal');
   
  /** Regra de navegação associada a aplicação. */
 
  $routeProvider.when($securityConfigProvider.addInterceptUrl('/'), {
  }) .when($securityConfigProvider.addInterceptUrl('/principal', { accessRoles: ['ADMIN', 'SECRETARIA']}), {
	  templateUrl: 'partials/principal.html'
 }).when($securityConfigProvider.addInterceptUrl('/incendio', { accessRoles: ['ADMIN', 'SECRETARIA']}), {
     templateUrl: 'partials/incendio.html',
     controller: 'IncendioController'
}).when($securityConfigProvider.addInterceptUrl('/usuario', { accessRoles: ['ADMIN', 'SECRETARIA']}), {
       templateUrl: 'partials/usuario.html',
       controller: 'UsuarioController'
  }).when($securityConfigProvider.addInterceptUrl('/usuarioPerfil'), {
      templateUrl: 'partials/usuarioPerfil.html',
      controller: 'UsuarioPerfilController'
  }).when($securityConfigProvider.addInterceptUrl('/login'), {
	  templateUrl: 'partials/login.html',
	  controller: 'LoginController'
 }).when($securityConfigProvider.addInterceptUrl('/cliente', { accessRoles: ['ADMIN', 'SECRETARIA']}), {
      templateUrl: 'partials/cliente.html',
      controller: 'ClienteController'
  }).otherwise({redirectTo:'/principal'});
  
  /** Regras de Validações de Campos */
  $validationProvider.showSuccessMessage = false;

  $validationProvider.setExpression({  
		required: function(value, scope, element, attrs) {
			
			/* Validação pontual para o componente Checkbox. */
			if (angular.equals(element[0].type, 'checkbox')){
				
				/* Para garantir que a validação seja consistente em situações onde o 'checkbox' é unico o atributo 'value' será verificado, 
				 * caso o valor seja falso, será realizada a navegação pelo DOM buscando elementos irmãos ao elemento validado. 
				 * 
				 * Obs: Para que a validação de um grupo de 'checkbox' sejam consistentes, os elementos devem estár dentro de uma 'Div' com o 
				 * seletor '.form-group'. Caso contrario a validação se tornará inconsistente. 
				 */
				return value || element.closest('div.form-group').find('[type=checkbox]').is(':checked');
			}
			
			/* Validação pontual para o componente ngDate. 
			 * O componente ngCalendar define a data vigente como padrão para o ngDate, 
			 * mesmo que o campo esteja vazio, o atributo value sempre está preenchido. 
			 */
			if(angular.isDefined(attrs.ngDate)) {
				return angular.isDefined(element.val()) && element.val().trim().length > 0;
			}
			
			/* Validação para campos String. Caso a String não esteja 
			 * undefined mas seu valor seja vazio o retorno será falso. 
			 * 
			 * Obs: Os elementos com mascara geradas pela api jquery.maskedinput, 
			 * quando estão vazios retornam o valor 'und.efi.ned-' ou 'un.def.ine/d', gerando inconsistência na validação
			 * de campos obrigatórios, como solução, a validação !angular.equals('und.efi.ned-', value.trim()) foi adicionada.   
			 */
			if(angular.isDefined(value) && angular.isString(value)) {
				return value.trim().length > 0 && !(angular.equals('und.efi.ned-', value.trim()) || angular.equals('un.def.ine/d', value.trim()));
			}
			return angular.isDefined(value);
  		}
  });

  $validationProvider.setDefaultMsg({  
	   required: {error: 'Campo de preenchimento obrigat\u00F3rio.', success: '' }
  });
 
}]);

/** Ponto de inicialização do módulo do modulo Principal. */
app.run(['$rootScope', '$security', '$http', '$location', function($rootScope, $security, $http, $location){
	
	/* Torna a instância de '$security' publica, permitindo o acesso a informações sobre credencial do Usuário e 
	 * o a gestão de papeis relevante na personalização de view.  */
	$rootScope.$security = $security;
	
	/**
	 * Método a será executado quando o evento 'validUser' for invocado, verificando 
	 * se existe Usuário válido na aplicação.
	 */
	$rootScope.$on('validUser', function(event, $defer){
		$http.post('usuario/validarUsuarioSession').success(function(data){ 
			$defer.resolve();
		}).error(function(data){
			$defer.reject();
		});
	});
	
	/** 
	 * Método a será executado quando o evento 'notAuthenticated' for invocado.  
	 * Caso o Usuário não esteja autenticado na aplicação, será redirecionado para página inicial.
	 */
	$rootScope.$on('notAuthenticated', function(event, credential){
		$location.path('/login');
	});

	/** 
	 * Método a será executado quando o evento 'isAccessValidate' for invocado. Verifica se o Usuário está autenticado e tem permissão para acessar a url informada.
	 */
	$rootScope.$on('validAccess', function(event, $defer, urlRoles){
		$http.post('usuario/validarAcesso', { roles: urlRoles}).success(function(data){ 
			$defer.resolve();
		}).error(function(data){
			$defer.reject();
		});
	});

	/** 
	 * Método a será executado quando o evento 'findUserRoles' for invocado. 
	 * 
	 * @param event
	 * @param $defer 
	 */
	$rootScope.$on('findUserRoles', function(event, $defer){
		$http.get('usuario/getAccessRoles').success(function(data){
			$defer.resolve(data);
		}).error(function(){
			$defer.reject();
		});
	});
}]);

/**
 * Constantes que representa as possíveis interações do Usuário com o Sistema.
 */
app.constant('ACAO_SISTEMA', {
	    INCLUIR: 'INCLUIR',
	    ALTERAR: 'ALTERAR',
	    LISTAR: 'LISTAR',
	    VISUALIZAR: 'VISUALIZAR',
	    SELECIONAR: 'SELECIONAR'
});


/** Implementação de 'factory' para manipulação das interações entre o Usuário e o Sistema. */
app.factory('$acaoSistema', ['ACAO_SISTEMA', function (ACAO_SISTEMA) {

	   var acaoSistema = function() {

		   var acaoVigente = null;

		   /** Altera o valor da ação para Incluir. */
		   this.acaoIncluir = function() {
			   acaoVigente = ACAO_SISTEMA.INCLUIR;
		   };

		   /** Altera o valor da ação para Alterar. */
		   this.acaoAlterar = function() {
			   acaoVigente = ACAO_SISTEMA.ALTERAR;
		   }

		   /** Altera o valor da ação para Visualizar. */
		   this.acaoVisualizar = function() {
			   acaoVigente = ACAO_SISTEMA.VISUALIZAR;
		   }

		   /** Altera o valor da ação para Listar. */
		   this.acaoListar = function() {
			   acaoVigente = ACAO_SISTEMA.LISTAR;
		   }

		   /** Altera o valor da ação para Selecionar. */
		   this.acaoSelecionar = function() {
			   acaoVigente = ACAO_SISTEMA.SELECIONAR;
		   }

		   /** Verifica se ação é referente a 'Incluir'. */
		   this.isAcaoIncluir = function() {
			   return angular.equals(ACAO_SISTEMA.INCLUIR, acaoVigente); 
		   };

		   /** Verifica se ação é referente a 'Alterar'. */
		   this.isAcaoAlterar = function() {
			   return angular.equals(ACAO_SISTEMA.ALTERAR, acaoVigente); 
		   }

		   /** Verifica se ação é referente a 'Visualizar'. */
		   this.isAcaoVisualizar = function() {
			   return angular.equals(ACAO_SISTEMA.VISUALIZAR, acaoVigente);
		   }

		   /** Verifica se ação é referente a 'Listar'. */
		   this.isAcaoListar = function() {
			   return angular.equals(ACAO_SISTEMA.LISTAR, acaoVigente);
		   }

		   /** Verifica se ação é referente a 'Seleção'. */
		   this.isAcaoSelecionar = function() {
			   return angular.equals(ACAO_SISTEMA.SELECIONAR, acaoVigente);
		   }

		   this.getAcaoSistema = function() {
			   return acaoVigente;
		   }
		   
		   this.setAcaoSistema = function(acao) {
			   acaoVigente = acao;
		   }
	   };

	   return acaoSistema;
 }]);

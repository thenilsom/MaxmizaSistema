app.factory('serviceUtils', ['$confirm','$filter', '$rootScope', function($confirm, $filter,$rootScope) {
	
	var $modelConfirm = new $confirm();
	var service = {};
	
	/**
	 * Recebe um objeto e verifica se é nullo ou undefined
	 */
	service.isEmpty = function(obj){
		return (obj === undefined || obj === null);
	}
	
	/**
	 * Recebe um objeto e verifica se é nullo, undefined ou string vazia ('').
	 */
	service.isNullOrEmpty = function(obj){
		return service.isEmpty(obj) || obj === '';
	}

	/************ CEP WEB SERVICE ******/
	 
	 /**
	   * Consulta o webservice viacep.com.br/ pelo cep informado
	   * e retorna a promessa.
	   */
	  service.consultarCep = function(cep){
	   var formatCep = cep.replace('.', '').replace('-','');
	   var url = "//viacep.com.br/ws/"+ formatCep +"/json/?callback=?"
	   
	   //se o cep for valido efetua a consulta no webservice
	   if(/^[0-9]{8}$/.test(formatCep)){
		   return $.getJSON(url);
	   }
     
	 }
	  
	  /**
	   * Verifica a String e Substitui acentos pelos Normais
	   */
	  service.retiraAcento= function(palavra){
		    var com_acento = 'áàãâäéèêëíìîïóòõôöúùûüçÁÀÃÂÄÉÈÊËÍÌÎÏÓÒÕÖÔÚÙÛÜÇ´`^¨~';  
		    var sem_acento = 'aaaaaeeeeiiiiooooouuuucAAAAAEEEEIIIIOOOOOUUUUC     ';
		    for (l in palavra){
		        for (l2 in com_acento){
		            if (palavra[l] == com_acento[l2]){
		                palavra=palavra.replace(palavra[l],sem_acento[l2]);
		    		}
		        }
		    }
		    return palavra;
		}
	 
	//retorno do servico
	return service;	
	
}]);

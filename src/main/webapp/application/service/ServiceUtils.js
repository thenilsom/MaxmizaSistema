app.factory('serviceUtils', ['$confirm','$filter', '$rootScope', function($confirm, $filter,$rootScope) {
	
	var $modelConfirm = new $confirm();
	var service = {};

	/************ CEP WEB SERVICE ******/
	 
	 /**
	   * Consulta o webservice viacep.com.br/ pelo cep informado
	   * e preenche o endereço com as informações vindas.
	   */
	  service.consultarCep = function(obj){
	   var url = "//viacep.com.br/ws/"+ obj.cep +"/json/?callback=?"
	    
	   //se o cep for valido efetua a consulta no webservice
	   if(/^[0-9]{8}$/.test(obj.cep)){
	    
	  $('.loader').show();
	     $.getJSON(url, function(dados) {
	      $('.loader').hide(); 
	         if (!("erro" in dados)) { 
	          obj.endereco = dados.logradouro;
	          obj.setor = dados.bairro;
	          obj.cidade =  dados.localidade;
	          $rootScope.$digest();
	         }
	         else {
	             //CEP pesquisado não foi encontrado.
	             console.log("CEP não encontrado.");
	         }
	     });
	       
	         
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

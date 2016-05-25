package br.com.autocom.saa.controller;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.maximiza.config.MessageCode;
import br.com.maximiza.exception.NegocioException;
import br.com.maximiza.util.ResourceMessageUtil;
import br.com.sw3.security.Credential;

/**
 * Classe padrão de controller.
 * 
 * @author Paulo Leonardo de O. Miranda
 */
public abstract class AbstractController implements Serializable {

	private static final long serialVersionUID = -6155811957522056040L;

	@Inject
	protected Result result;
	
	@Inject
	protected Credential credential;
	
	/**
	 * Adiciona mensagem de erro associadio ao Negócio.
	 * 
	 * @param e
	 */
	protected void adicionarMensagemErroNegocio(NegocioException e) {
		if(e.getCode() != null) {
			result.use(Results.http()).body(e.getMessage()).setStatusCode(400);
		} else {
			adicionarMensagemErroInesperado(e);
		}
	}
	
	/**
	 * Adiciona mensagem de erro inesperado.
	 * 
	 * @param e
	 */
	protected void adicionarMensagemErroInesperado(Throwable e) {
		result.use(Results.http()).body(ResourceMessageUtil.getDescricaoErro(MessageCode.MSG_001, e)).setStatusCode(500);
	}

}

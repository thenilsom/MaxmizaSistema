package br.com.autocom.saa.controller;
import java.util.List;

import javax.inject.Inject;

import br.com.autocom.saa.bo.AssuntoBO;
import br.com.autocom.saa.config.MessageCode;
import br.com.autocom.saa.dom.Assunto;
import br.com.autocom.saa.exception.NegocioException;
import br.com.autocom.saa.util.ResourceMessageUtil;
import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.view.Results;
import br.com.sw3.security.annotation.Secured;

/**
 * Classe Controller responsável pela autenticação dos Assunto na aplicação.
 * 
 * 
 * @author Samuel Borges de Oliveira
 */
@Controller
public class AssuntoController extends AbstractController {

	private static final long serialVersionUID = 4210621996255196057L;

	@Inject
	private AssuntoBO assuntoBO;

	/**
	 * Salva o Assunto informado na base de dados.
	 * 
	 * @param assunto
	 */
	@Post
	@Consumes("application/json")
	@Path("assunto/salvar")
	public void salvar(Assunto assunto) {
		try {
			
			assuntoBO.salvar(assunto);
			result.use(Results.http()).body(ResourceMessageUtil.getDescricao(MessageCode.MSG_005)).setStatusCode(200);	
			
		} catch (NegocioException e) {
			adicionarMensagemErroNegocio(e);
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}
	
	/**
	 * Salva e retorna um novo assunto
	 * @param assunto
	 */
	@Post
	@Consumes("application/json")
	@Path("assunto/novoAssunto")
	public void novoAssunto(Assunto assunto){
		try {
			Assunto novoAssunto = assuntoBO.salvar(assunto);
			result.use(Results.json()).withoutRoot().from(novoAssunto).serialize();
		} catch (NegocioException e) {
			adicionarMensagemErroNegocio(e);
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}
	
	/**
	 * Exclui o Assunto informado na base de dados.
	 * 
	 * @param assunto
	 */
	@Post
	@Consumes("application/json")
	@Path("assunto/excluir")
	public void excluir(Assunto assunto) {
		try {
			assuntoBO.excluir(assunto);
			result.use(Results.http()).body(ResourceMessageUtil.getDescricao(MessageCode.MSG_005)).setStatusCode(200);		
		} catch (NegocioException e) {
			adicionarMensagemErroNegocio(e);
		} catch(Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}
	
	/**
	 * Retorna a lista com todos os Assuntos existentes na base de dados.
	 */
	@Get
	@Path("assunto/listarTudo")
	@Secured
	public void listarTudo() {
		try {
			List<Assunto> assuntos = assuntoBO.listarTudo();
			result.use(Results.json()).withoutRoot().from(assuntos).serialize();
		} catch (NegocioException e) {
			adicionarMensagemErroNegocio(e);
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}
	
}

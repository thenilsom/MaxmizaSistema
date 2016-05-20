package br.com.autocom.saa.controller;

import java.util.List;

import javax.inject.Inject;

import br.com.autocom.saa.bo.RecadoBO;
import br.com.autocom.saa.config.MessageCode;
import br.com.autocom.saa.dom.Recado;
import br.com.autocom.saa.exception.NegocioException;
import br.com.autocom.saa.util.ResourceMessageUtil;
import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.view.Results;
import br.com.sw3.security.annotation.Secured;

@Controller
public class RecadoController extends AbstractController {

	private static final long serialVersionUID = 5696792780874048120L;
	
	@Inject
	private RecadoBO recadoBO;
		
	/**
	 * Salva o Recado informado na base de dados.
	 * 
	 * @param recado
	 */
	@Post
	@Consumes("application/json")
	@Path("recado/salvar")
	public void salvar(Recado recado) {
		try {
			recadoBO.salvar(recado, credential);
			result.use(Results.http()).body(ResourceMessageUtil.getDescricao(MessageCode.MSG_005)).setStatusCode(200);	
			
		} catch (NegocioException e) {
			adicionarMensagemErroNegocio(e);
		} catch (Exception e) {
			e.printStackTrace();
			adicionarMensagemErroInesperado(e);
		}
	}
	
	/**
	 * Marca o recado como lido
	 * @param recado
	 */
	@Post
	@Consumes("application/json")
	@Path("recado/lerRecado")
	public void lerRecado(Recado recado){
		try {
			recadoBO.lerRecado(recado);
			result.use(Results.http()).body(ResourceMessageUtil.getDescricao(MessageCode.MSG_005)).setStatusCode(200);
		} catch (NegocioException e) {
			adicionarMensagemErroNegocio(e);
		} catch (Exception e) {
			e.printStackTrace();
			adicionarMensagemErroInesperado(e);
		}
	}
	
	/**
	 * Exclui o Recado informado na base de dados.
	 * 
	 * @param recado
	 */
	@Post
	@Consumes("application/json")
	@Path("recado/excluir")
	public void excluir(Recado recado) {
		try {
			recadoBO.excluir(recado);
			result.use(Results.http()).body(ResourceMessageUtil.getDescricao(MessageCode.MSG_005)).setStatusCode(200);		
		} catch (NegocioException e) {
			adicionarMensagemErroNegocio(e);
		} catch(Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}
	
	/**
	 * Retorna a lista com todos os Recados enviados do usuario na sessão
	 */
	@Get
	@Path("recado/listarRecadosEnviados")
	@Consumes("application/json")
	@Secured
	public void listarRecadosEnviados() {
		try {
			List<Recado> enviados = recadoBO.listarRecadosEnviados(credential.getLogin());
			result.use(Results.json()).withoutRoot().from(enviados).include("de", "para").serialize();
		} catch (NegocioException e) {
			adicionarMensagemErroNegocio(e);
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}
	
	/**
	 * Retorna a lista com todos os Recados recebidos do usuario na sessão
	 */
	@Get
	@Path("recado/listarRecadosRecebidos")
	@Consumes("application/json")
	@Secured
	public void listarRecadosRecebidos() {
		try {
			List<Recado> recebidos = recadoBO.listarRecadosRecebidos(credential.getLogin());
			result.use(Results.json()).withoutRoot().from(recebidos).include("de", "para").serialize();
		} catch (NegocioException e) {
			adicionarMensagemErroNegocio(e);
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}	
	
	/**
	 * Retorna a Quantidade de Recados não Lidos.
	 */
	
	@Get
	@Consumes("application/json")
	@Path("recado/qtdRecados")
	public void qtdRecados(){
		try {
			Long qtd = recadoBO.qtdRecados(credential.getLogin());
			result.use(Results.json()).withoutRoot().from(qtd).serialize();
		} catch (NegocioException e) {
			adicionarMensagemErroNegocio(e);
		} catch(Exception e){
			adicionarMensagemErroInesperado(e);
		}
	}
	

}

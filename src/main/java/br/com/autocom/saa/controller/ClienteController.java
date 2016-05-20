package br.com.autocom.saa.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import br.com.autocom.saa.bo.ClienteBO;
import br.com.autocom.saa.config.MessageCode;
import br.com.autocom.saa.dom.Cliente;
import br.com.autocom.saa.dom.enums.Categoria;
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
 * Classe Controller responsável pelo controle de clientes na aplicação.
 * 
 * @author Denilson Godinho
 *
 */

@Controller
public class ClienteController extends AbstractController {

	private static final long serialVersionUID = -4389654058512099905L;
	
	@Inject
	private ClienteBO clienteBO;
	
	/**
	 * Salvar o Cliente informado na base de dados.
	 * @param cliente
	 */
	@Post
	@Consumes("application/json")
	@Path("cliente/salvar")
	public void salvar(Cliente cliente){
		try {
			clienteBO.salvar(cliente);
			result.use(Results.http()).body(ResourceMessageUtil.getDescricao(MessageCode.MSG_005)).setStatusCode(200);
		} catch(NegocioException e){
			adicionarMensagemErroNegocio(e);
		}catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}
	
	/**
	 * Excluir o Usuario informado no banco de dados.
	 * @param usuario
	 */
	@Post
	@Consumes("application/json")
	@Path("cliente/excluir")
	public void excluir(Cliente cliente){
		try {
			clienteBO.excluir(cliente);
			result.use(Results.http()).body(ResourceMessageUtil.getDescricao(MessageCode.MSG_005)).setStatusCode(200);
		} catch (NegocioException e) {
			adicionarMensagemErroNegocio(e);
		} catch(Exception e) {
			adicionarMensagemErroInesperado(e);
		}
		
	}
	
	/**
	 * Retorna a lista com todos os Clientes existentes na base de dados.
	 */
	
	@Get
	@Path("cliente/listarTudo")
	@Consumes("application/json")
	@Secured
	public void listarTudo(){
		try {
			List<Cliente> clientes = clienteBO.listarTudo();
			result.use(Results.json()).withoutRoot().from(clientes).serialize();
		} catch(NegocioException e){
			adicionarMensagemErroNegocio(e);
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}
	
	/**
	 * Retorna a lista com todos os Clientes ativos na base de dados.
	 */
	
	@Get
	@Path("cliente/listarAtivos")
	@Consumes("application/json")
	@Secured
	public void listarAtivos(){
		try {
			List<Cliente> clientes = clienteBO.listarAtivos();
			result.use(Results.json()).withoutRoot().from(clientes).serialize();
		} catch(NegocioException e){
			adicionarMensagemErroNegocio(e);
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}
	
	
	/**
	 * Retorna a lista de categorias
	 */
	@Get
	@Path("cliente/categorias")
	@Consumes("application/json")
	public void getCategorias(){
		try {
			Map<Categoria, String> mapCategoria = new HashMap<Categoria, String>();
			for(Categoria categoria : Categoria.values()){
				mapCategoria.put(categoria, categoria.getDescricao());
			}
			result.use(Results.json()).withoutRoot().from(mapCategoria).serialize();
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}

}

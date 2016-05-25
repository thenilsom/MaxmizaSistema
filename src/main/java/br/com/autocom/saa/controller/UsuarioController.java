package br.com.autocom.saa.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import br.com.autocom.saa.dom.Usuario;
import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.view.Results;
import br.com.maximiza.bo.UsuarioBO;
import br.com.maximiza.config.MessageCode;
import br.com.maximiza.exception.NegocioException;
import br.com.maximiza.to.UsuarioTO;
import br.com.maximiza.util.ResourceMessageUtil;
import br.com.sw3.security.CredencialParameter;
import br.com.sw3.security.CredentialParameterType;
import br.com.sw3.security.annotation.Secured;

/**
 * Classe Controller responsável pela autenticação dos Usuário na aplicação.
 * 
 * @author Paulo Leonardo de O. Miranda
 */
@Controller
public class UsuarioController extends AbstractController {

	private static final long serialVersionUID = 7628069694305368151L;
	
	@Inject
	private UsuarioBO usuarioBO;
	
	@Inject
	private HttpSession httpSession;

	/**
	 * Salva o Usuario informado na base de dados.
	 * 
	 * @param usuario
	 */
	@Post
	@Consumes("application/json")
	@Path("usuario/salvar")
	public void salvar(Usuario usuario) {
		try {
			usuarioBO.salvar(usuario);
			result.use(Results.http()).body(ResourceMessageUtil.getDescricao(MessageCode.MSG_005)).setStatusCode(200);		
		} catch (NegocioException e) {
			adicionarMensagemErroNegocio(e);
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}
	
	/**
	 * Excluir o Usuario informado no banco de dados.
	 * @param usuario
	 */
	@Post
	@Consumes("application/json")
	@Path("usuario/excluir")
	public void excluir(Usuario usuario){
		try {
			usuarioBO.excluir(usuario);
			result.use(Results.http()).body(ResourceMessageUtil.getDescricao(MessageCode.MSG_005)).setStatusCode(200);
		} catch (NegocioException e) {
			adicionarMensagemErroNegocio(e);
		} catch(Exception e) {
			adicionarMensagemErroInesperado(e);
		}
		
	}
	
	/**
	 * Retorna a lista com todos os Usuarios existentes na base de dados.
	 */
	@Get
	@Path("usuario/listarTudo")
	@Secured
	public void listarTudo() {
		try {
			List<Usuario> usuarios = usuarioBO.listarTudo();
			
			result.use(Results.json()).withoutRoot().from(usuarios).serialize();
		} catch (NegocioException e) {
			adicionarMensagemErroNegocio(e);
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}
	
	/**
	 * Retorna a lista com todos os Usuarios ativos na base de dados.
	 */
	@Get
	@Path("usuario/listarAtivos")
	@Secured
	public void listarAtivos() {
		try {
			List<Usuario> usuarios = usuarioBO.listarAtivos();
			
			result.use(Results.json()).withoutRoot().from(usuarios).serialize();
		} catch (NegocioException e) {
			adicionarMensagemErroNegocio(e);
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}
	
	/**
	 * Retorna o Usuario na sessão.
	 */
	@Post
	@Path("usuario/visualizarPerfil")
	@Consumes("application/json")
	public void visualizarPerfil(){
		try {
			result.use(Results.json()).withoutRoot().from(usuarioBO.getUsuarioLogin(credential.getLogin())).serialize();
		} catch (NegocioException e) {
			adicionarMensagemErroNegocio(e);
		} catch(Exception e){
			adicionarMensagemErroInesperado(e);
		}
		
	}
	
	/**
	 * Realiza o processo de autenticação do usuário na aplicação.
	 * 
	 * @param chave
	 */
	@Post
	@Path("usuario/login")
	@Consumes("application/json")
	public void logar(final UsuarioTO usuarioTO) {
		try {
			Usuario usuario = usuarioBO.getUsuario(usuarioTO);
			
			List<String> acessRoles = new ArrayList<String>();
			acessRoles.add(usuario.getPerfil().toString());
			
			String token = UUID.randomUUID().toString();
			
			/* Inicializa a Credencial do Usuário. */
			List<CredencialParameter> parametros = new ArrayList<CredencialParameter>();
			parametros.add(new CredencialParameter(CredentialParameterType.USER_NAME, usuario.getNome()));
			parametros.add(new CredencialParameter(CredentialParameterType.LOGIN, usuario.getLogin()));
			parametros.add(new CredencialParameter(CredentialParameterType.ACCESS_ROLES, acessRoles));
			parametros.add(new CredencialParameter(CredentialParameterType.TOKEN, token));
			credential.initialize(parametros);
			
			/* Atualiza os dados do UsuarioTO para a resposta. */
			usuarioTO.setNome(usuario.getNome());
			usuarioTO.setLogin(usuario.getLogin());
			usuarioTO.setAcessRoles(acessRoles);
			usuarioTO.setToken(token);
			usuarioTO.setSenha(null);
			
			result.use(Results.json()).withoutRoot().from(usuarioTO).include("acessRoles").serialize();
		} catch (NegocioException e) {
			adicionarMensagemErroNegocio(e);
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}		
	}
	
	/**
	 * Realiza o Logout do Usuário invalidando o 'Token', que garante o acesso à aplicação.
	 */
	@Post
	@Path("usuario/logout")
	public void logout() {
		try {
			credential.invalidate();
			httpSession.invalidate();
			result.use(Results.status()).ok();
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}
	
	/**
	 * Verifica se o Usuário está autenticado.
	 */
	@Post
	@Secured
	@Path("usuario/validarUsuarioSession")
	@Consumes("application/json")
	public void validarUsuarioSession() {
		try {
			result.use(Results.http()).setStatusCode(200);
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}
	
	/**
	 * Verifica se o Usuário tem os níveis de acesso necessário para acessar determinada 
	 * funcionalidade, segundo nos níveis informados.
	 */
	@Post
	@Secured
	@Path("usuario/validarAcesso")
	@Consumes("application/json")
	public void validarAcesso(List<String> roles) {
		try {
			if(credential.hasAccessRoles(roles)) {
				result.use(Results.http()).setStatusCode(200);
			} else {
				result.use(Results.http()).setStatusCode(400);
			}
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}
	
	/**
	 * Retorna a lista de permissões de acessos associado ao Usuário logado.
	 */
	@Get
	@Secured
	@Path("usuario/getAccessRoles")
	public void getAccessRoles() {
		try {
			result.use(Results.json()).withoutRoot().from(credential.getAccessRoles()).serialize();
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}
}

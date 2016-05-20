package br.com.autocom.saa.controller;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import br.com.autocom.saa.bo.AuditoriaBO;
import br.com.autocom.saa.bo.UsuarioBO;
import br.com.autocom.saa.dom.Auditoria;
import br.com.autocom.saa.dom.enums.Acao;
import br.com.autocom.saa.dom.enums.Tabela;
import br.com.autocom.saa.dom.filtro.AuditoriaFiltro;
import br.com.autocom.saa.exception.NegocioException;
import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.view.Results;
import br.com.sw3.security.annotation.Secured;

/**
 * Classe Controller responsável pela autenticação das Auditorias na aplicação.
 * 
 * 
 * @author Samuel Borges de Oliveira
 */

@Controller
public class AuditoriaController extends AbstractController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Auditoria auditoria;
	
	@Inject 
	private UsuarioBO usuarioBO;
	
	@Inject 
	private AuditoriaBO auditoriaBO;
	
	/**
	 * Salva a Auditoria informada na base de dados.
	 * 
	 * @param assunto
	 */
	public void salvar(Acao acao , Long entidade_id, Tabela tabela) throws NegocioException {
		
		auditoria.setAcao(acao);
		auditoria.setEntidade_id(entidade_id);
		auditoria.setTabela(tabela);
		auditoria.setUsuario(usuarioBO.consultar(credential.getLogin()));
		auditoria.setData(LocalDateTime.now());
		
		auditoriaBO.salvar(auditoria);
	}
	
	/**
	 * Filtra a Auditoria de acordo com o filtro.
	 * @param filtro
	 */
	@Post
	@Consumes("application/json")
	@Path("auditoria/filtro")
	public void filtro(AuditoriaFiltro filtro){
		try {
			List<Auditoria> auditorias = auditoriaBO.listarFiltro(filtro);
			result.use(Results.json()).withoutRoot().from(auditorias).include("usuario" , "data").serialize();
		} catch(NegocioException e){
			adicionarMensagemErroNegocio(e);
		}catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}
	
	/**
	 * Retorna a lista com todas as Ultimas 100 Auditorias existentes na base de dados.
	 */
	@Get
	@Path("auditoria/listarUltimas")
	@Secured
	public void listarUltimas() throws NegocioException{
		
		AuditoriaFiltro filtro = new AuditoriaFiltro();
		filtro.setRegistros("100");
		List<Auditoria> auditorias = auditoriaBO.listarFiltro(filtro);
		result.use(Results.json()).withoutRoot().from(auditorias).include("usuario" , "data").serialize();

	}
	
	/**
	 * Retorna a lista de Acoes
	 */
	@Get
	@Path("auditoria/acoes")
	@Consumes("application/json")
	public void getAcoes(){
		try {
			Map<Acao, String> mapAcao = new HashMap<Acao, String>();
			for(Acao acao : Acao.values()){
				mapAcao.put(acao, acao.getDescricao());
			}
			result.use(Results.json()).withoutRoot().from(mapAcao).serialize();
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}
	
	/**
	 * Retorna a lista de Acoes
	 */
	@Get
	@Path("auditoria/tabelas")
	@Consumes("application/json")
	public void getTabelas(){
		try {
			Map<Tabela, String> mapTabela = new HashMap<Tabela, String>();
			for(Tabela tabela : Tabela.values()){
				mapTabela.put(tabela, tabela.getDescricao());
			}
			result.use(Results.json()).withoutRoot().from(mapTabela).serialize();
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}
	
	
		
}

package br.com.autocom.saa.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import br.com.autocom.saa.bo.AtendimentoBO;
import br.com.autocom.saa.bo.UsuarioBO;
import br.com.autocom.saa.bo.VersaoAtendimentoBO;
import br.com.autocom.saa.config.MessageCode;
import br.com.autocom.saa.dom.Atendimento;
import br.com.autocom.saa.dom.Usuario;
import br.com.autocom.saa.dom.enums.Perfil;
import br.com.autocom.saa.dom.enums.Status;
import br.com.autocom.saa.dom.filtro.AtendimentoFiltro;
import br.com.autocom.saa.exception.NegocioException;
import br.com.autocom.saa.to.AtendimentoTO;
import br.com.autocom.saa.util.DataUtil;
import br.com.autocom.saa.util.ResourceMessageUtil;
import br.com.autocom.saa.util.Util;
import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.view.Results;
import br.com.sw3.security.annotation.Secured;

/**
 * Classe Controller responsável pela autenticação dos Atendimentos na aplicação.
 * 
 * @author Danillo Santana
 */
@Controller
public class AtendimentoController extends AbstractController {

	
	private static final long serialVersionUID = -6887453331359418605L;
	
	
	@Inject
	private AtendimentoBO atendimentoBO;
	
	@Inject
	private VersaoAtendimentoBO versaoAtendimentoBO;
	
	@Inject
	private UsuarioBO usuarioBO;
	
	/**
	 * Salva o Atendimento informado na base de dados.
	 * 
	 * @param atendimento
	 */
	@Post
	@Consumes("application/json")
	@Path("atendimento/salvar")
	public void salvar(Atendimento atendimento) {
		try {
			atendimentoBO.salvar(atendimento, credential);
			result.use(Results.http()).body(ResourceMessageUtil.getDescricao(MessageCode.MSG_005)).setStatusCode(200);		
		} catch (NegocioException e) {
			adicionarMensagemErroNegocio(e);
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}
	/**
	 * Excluir o Atendimento informado no banco de dados.
	 * @param usuario
	 */
	@Post
	@Consumes("application/json")
	@Path("atendimento/excluir")
	public void excluir(Atendimento atendimento){
		try {
			atendimentoBO.excluir(atendimento);
			result.use(Results.http()).body(ResourceMessageUtil.getDescricao(MessageCode.MSG_005)).setStatusCode(200);
		} catch (NegocioException e) {
			adicionarMensagemErroInesperado(e);
		} catch(PersistenceException e) {
			adicionarMensagemErroNegocio(new NegocioException(MessageCode.MSG_011));
		} catch(Exception e) {
			adicionarMensagemErroInesperado(e);
		}
		
	}
	
	@Get
	@Consumes("application/json")
	@Path("atendimento/versaoAtual")
	public void getVersaoAtual(){
		try {
			result.use(Results.json()).withoutRoot().from(versaoAtendimentoBO.versaoAtual()).serialize();
		} catch (NegocioException e) {
			adicionarMensagemErroNegocio(e);
		}catch(Exception e){
			adicionarMensagemErroInesperado(e);
		}
	}
	
	
	/**
	 * Retorna a lista com todos os Atendimentos existentes na base de dados.
	 */
	@Get
	@Path("atendimento/listarTudo")
	public void listarTudo() {
		try {
			List<Atendimento> atendimentos = isPerfilTecnico() ? atendimentoBO.listarTudo(credential.getLogin()) :  atendimentoBO.listarTudo();
			List<AtendimentoTO> atendimentosTO = new ArrayList<AtendimentoTO>();
			
			for(Atendimento atendimento : atendimentos){
				AtendimentoTO atendimentoTO = new AtendimentoTO(atendimento);
				if(Util.isNull(atendimento.getDataFim())){
					atendimentoTO.setDataFim(DataUtil.getLocalDateTime());
				}
				
				atendimentoTO.setTempo(DataUtil.CalculaTempo(atendimentoTO.getDataInicio(), atendimentoTO.getDataFim()));
				
				if(atendimentoTO.getStatus() != Status.FINALIZADO){
					atendimentoTO.setDataFim(null);
				}
				
				atendimentosTO.add(atendimentoTO);

			}

			result.use(Results.json()).withoutRoot().from(atendimentosTO).include("assuntos").include("usuarioSolucao").include("dataInicio").include("dataFim").include("cliente").include("usuarios").serialize();
		} catch (NegocioException e) {
			adicionarMensagemErroNegocio(e);
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}
	
	/**
	 * Retorna a Quantidade de Atendimentos Pendentes / Em Atendimento
	 */
	@Get
	@Path("atendimento/qtdAtendimentos")
	public void qtdAtendimentos() {
		try {
			Long qtd = atendimentoBO.qtdAtendimentos(credential.getLogin());
			result.use(Results.json()).withoutRoot().from(qtd).serialize();
		} catch (NegocioException e) {
			adicionarMensagemErroNegocio(e);
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}
	
	/**
	  *Filtra o Atendimento de acordo com o filtro. 
	  * @param filtro
	  */
	 
	 @Post
	 @Consumes("application/json")
	 @Path("atendimento/filtro")
	 public void filtro(AtendimentoFiltro filtro){
	  try {
	   if(isPerfilTecnico()){
		   filtro.setLogin(credential.getLogin());
	   }
	   List<Atendimento> atendimentos = atendimentoBO.listarFiltro(filtro);
	   List<AtendimentoTO> atendimentosTO = new ArrayList<AtendimentoTO>();
		
		for(Atendimento atendimento : atendimentos){
			AtendimentoTO atendimentoTO = new AtendimentoTO(atendimento);
			if(Util.isNull(atendimento.getDataFim())){
				atendimentoTO.setDataFim(DataUtil.getLocalDateTime());
			}
			
			atendimentoTO.setTempo(DataUtil.CalculaTempo(atendimentoTO.getDataInicio(), atendimentoTO.getDataFim()));
			
			atendimentosTO.add(atendimentoTO);

		}
 
	   
	   result.use(Results.json()).withoutRoot().from(atendimentosTO).include("assuntos").include("usuarioSolucao").include("dataInicio").include("dataFim").include("cliente").include("usuarios").serialize();
	  } catch (NegocioException e) {
	   adicionarMensagemErroNegocio(e);
	  }catch (Exception e) {
	   adicionarMensagemErroInesperado(e);
	  }
	 }
	
	/**
	 * Inicia Novo Atendimento
	 */
	@Get
	@Path("atendimento/novo")
	@Secured
	public void novoAtendimento() {
			
			try {
				Atendimento atendimento = new Atendimento();
				atendimento.setStatus(Status.EM_ANDAMENTO);
				atendimento.setFavorito(false);
				atendimento.setDataInicio(LocalDateTime.now());
				if(credential.getAccessRoles().contains(Perfil.ADMIN.toString()) || credential.getAccessRoles().contains(Perfil.TECNICO.toString())){
					Usuario usuarioAtendimento = usuarioBO.consultar(credential.getLogin());
					atendimento.getUsuarios().add(usuarioAtendimento);
				}			
				result.use(Results.json()).withoutRoot().from(atendimento).include("dataInicio").include("usuarios").serialize();
			} catch (NegocioException e) {
				adicionarMensagemErroNegocio(e);
			}
			
		
	}
	
	/**
	 * Monta o Map Com os Status.
	 */
	@Get
	@Path("atendimento/listarStatus")
	@Secured
	public void getStatus(){
		try {
			Map<Status, String> mapStatus = new HashMap<Status, String>();
			for (Status status : Status.values()) {
				mapStatus.put(status, status.getDescricao());
			}
			result.use(Results.json()).withoutRoot().from(mapStatus).serialize();
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}
	
	/**
	 * Retorna true se o perfil do usuario logado for tecnico
	 * @return
	 */
	public boolean isPerfilTecnico(){
		return credential.getAccessRoles().contains(Perfil.TECNICO.toString());
	}
}

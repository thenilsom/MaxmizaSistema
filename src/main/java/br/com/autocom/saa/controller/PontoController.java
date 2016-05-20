package br.com.autocom.saa.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import br.com.autocom.saa.bo.PontoBO;
import br.com.autocom.saa.config.MessageCode;
import br.com.autocom.saa.dom.Ponto;
import br.com.autocom.saa.dom.Usuario;
import br.com.autocom.saa.dom.enums.PeriodoPonto;
import br.com.autocom.saa.dom.filtro.PontoFiltro;
import br.com.autocom.saa.exception.NegocioException;
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
 * Classe Controller responsável pelos Pontos da Aplicação
 * 
 * @author Samuel Oliveira
 */
@Controller
public class PontoController extends AbstractController {

	private static final long serialVersionUID = -2248694295671368838L;
	
	@Inject
	private PontoBO pontoBO;

	/**
	 * Salva o Ponto informado na base de dados.
	 * 
	 * @param ponto
	 */
	@Post
	@Consumes("application/json")
	@Path("ponto/salvar")
	public void salvar(Ponto ponto) {
		try {
			pontoBO.salvar(ponto);
			result.use(Results.http()).body(ResourceMessageUtil.getDescricao(MessageCode.MSG_005)).setStatusCode(200);		
		} catch (NegocioException e) {
			adicionarMensagemErroNegocio(e);
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}
	
	/**
	 * Retorna a lista com todos os Pontos existentes na base de dados.
	 */
	@Post
	@Path("ponto/listarTudo")
	@Consumes("application/json")
	public void listarTudo(PontoFiltro filtro) {
		try {
			if(Util.isNull(filtro.getUsuario())){
				Usuario usuario = new Usuario();
				usuario.setLogin(credential.getLogin());
				filtro.setUsuario(usuario);
			}
			List<Ponto> pontos = pontoBO.listarTudo(filtro);
			
			result.use(Results.json()).withoutRoot().from(pontos).include("data").include("entradaDia").include("saidaDia").include("entradaAlmoco").include("saidaAlmoco").include("dataAtual").include("usuario").serialize();
		} catch (NegocioException e) {
			adicionarMensagemErroNegocio(e);
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}
	
	/**
	 * Retorna a lista de Acoes
	 */
	@Get
	@Path("ponto/periodos")
	@Consumes("application/json")
	public void getPeriodos(){
		try {
			Map<PeriodoPonto, String> mapPeriodo = new HashMap<PeriodoPonto, String>();
			for(PeriodoPonto periodoPonto : PeriodoPonto.values()){
				mapPeriodo.put(periodoPonto, periodoPonto.getDescricao());
			}
			result.use(Results.json()).withoutRoot().from(mapPeriodo).serialize();
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}

	/**
	 * Inicia Novo Ponto
	 */
	@Get
	@Path("ponto/novo")
	@Secured
	public void novoAtendimento() {
		try{	
			result.use(Results.json()).withoutRoot().from(pontoBO.novoPonto(credential.getLogin())).include("data").include("entradaDia").include("saidaDia").include("entradaAlmoco").include("saidaAlmoco").include("dataAtual").include("usuario").serialize();
		} catch (NegocioException e) {
			adicionarMensagemErroNegocio(e);
		}
			
		
	}
	
}

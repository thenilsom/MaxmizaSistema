package br.com.autocom.saa.controller;

import javax.inject.Inject;
import br.com.autocom.saa.bo.RelatorioGeralBO;
import br.com.autocom.saa.exception.NegocioException;
import br.com.autocom.saa.to.PeriodoTO;
import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.view.Results;

/**
 * Classe Controller responsável pela geração dos relatório.
 * 
 * @author Danillo Santana
 */
@Controller
public class RelatoriosController extends AbstractController{

	private static final long serialVersionUID = 7012739644669268786L;
	
	@Inject
	private RelatorioGeralBO relatorioGeralBO;
	
	@Post
	@Path("relatorios/gerarRelatorioGeral")
	@Consumes("application/json")
	public void gerarRelatorioGeral(final PeriodoTO periodoTO){
		try {
			result.use(Results.json()).withoutRoot().from(relatorioGeralBO.getRelatorioGeral(credential.getUserName(), periodoTO)).serialize();
		} catch (NegocioException e) {
			adicionarMensagemErroNegocio(e);
		} catch(Exception e){
			adicionarMensagemErroInesperado(e);
		}
	}
	
}

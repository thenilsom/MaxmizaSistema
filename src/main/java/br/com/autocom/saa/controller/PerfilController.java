package br.com.autocom.saa.controller;

import java.util.HashMap;
import java.util.Map;

import br.com.autocom.saa.dom.enums.Perfil;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.view.Results;
import br.com.sw3.security.annotation.Secured;

/**
 * Classe Controller respons�vel pela mater lista de perfil.
 * 
 * @author Danillo Santana D'Afonseca
 */
@Controller
public class PerfilController extends AbstractController {
	
	
	private static final long serialVersionUID = 2382587160768134524L;

	
	/**
	 * Retorna lista de chaves/valores do perfil.
	 */
	@Get
	@Path("perfil/lista")
	@Secured
	public void getPerfis(){
		try {
			Map<Perfil, String> mapPerfil = new HashMap<Perfil, String>();
			for (Perfil perfil : Perfil.values()) {
				mapPerfil.put(perfil, perfil.getDescricao());
			}
			result.use(Results.json()).withoutRoot().from(mapPerfil).serialize();
		} catch (Exception e) {
			adicionarMensagemErroInesperado(e);
		}
	}
	
	
}

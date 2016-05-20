package br.com.autocom.saa.bo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.autocom.saa.controller.AuditoriaController;
import br.com.autocom.saa.dom.Ponto;
import br.com.autocom.saa.dom.Usuario;
import br.com.autocom.saa.dom.enums.Acao;
import br.com.autocom.saa.dom.enums.Tabela;
import br.com.autocom.saa.dom.filtro.PontoFiltro;
import br.com.autocom.saa.exception.NegocioException;
import br.com.autocom.saa.presistence.PontoDAO;
import br.com.autocom.saa.util.DataUtil;
import br.com.autocom.saa.util.Util;
import br.com.sw3.persistence.exception.DAOException;

/**
 * Classe de Implementação de Negócio referente a {@link Ponto}.
 * 
 * @author Samuel Oliveira
 *
 */

@Named
@Dependent
@Transactional(rollbackOn = { NegocioException.class })
public class PontoBO {
	
	@Inject
	private PontoDAO pontoDAO;
	
	@Inject
    private UsuarioBO usuarioBO;
	
	@Inject
	private AuditoriaController auditoriaController;

	public Ponto salvar(Ponto ponto) throws NegocioException{
		
		Acao acao = Acao.ALTERAR;
		
		try{
			
			ponto.setMinutosTrabalhados(calculaMinutos(ponto));
		
			Ponto pontoSalvo = pontoDAO.persistir(ponto);
			
			//Auditoria 
			auditoriaController.salvar(acao , pontoSalvo.getId() , Tabela.PONTO);
			
			return pontoSalvo;
			
		}catch (DAOException e) {
			throw new NegocioException(e);
		}
	}
	
	/**
	 * Retorna a lista com todos os pontos existentes na base de dados.
	 * 
	 * @return
	 * @throws NegocioException
	 */
	public List<Ponto> listarTudo(PontoFiltro filtro) throws NegocioException {
		try {
			
			List<Ponto> pontos =  pontoDAO.listarTudo(filtro);
			List<Ponto> pontos2 = new ArrayList<Ponto>();
			
			for (Ponto ponto : pontos) {
				pontos2.add(calculaTempo(ponto));
			}
			return pontos2;
		} catch (DAOException e) {
			throw new NegocioException(e);
		}
	}

	/**
	 * @param data
	 * @return
	 * @throws NegocioException 
	 */
	public Ponto novoPonto(String login) throws NegocioException {
	
		Ponto ponto = new Ponto();
		LocalDateTime data = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
		
		try {
			
			Usuario usuario = usuarioBO.consultar(login);
			ponto = pontoDAO.getPonto(data,usuario);
			
			if(Util.isNull(ponto)){		
				ponto = new Ponto();
				ponto.setData(data);
				ponto.setUsuario(usuario);
				ponto.setMinutosTrabalhados(0);
			}
			
		} catch (DAOException e) {
			throw new NegocioException(e);
		}
		
		ponto.setDataAtual(LocalDateTime.now());

		return ponto;
	}
	
	private long calculaMinutos(Ponto ponto){
		
		long tempo = 0 ;
		
		if(!Util.isNull(ponto.getEntradaDia())    && !Util.isNull(ponto.getSaidaDia()) &&
		   !Util.isNull(ponto.getEntradaAlmoco()) && !Util.isNull(ponto.getSaidaAlmoco())){
			
			tempo = DataUtil.calculaMinutos(ponto.getEntradaDia(), ponto.getSaidaAlmoco(),ponto.getEntradaAlmoco(), ponto.getSaidaDia());
			
		}else if(!Util.isNull(ponto.getEntradaDia()) && !Util.isNull(ponto.getSaidaAlmoco())){
			tempo = DataUtil.calculaMinutos(ponto.getEntradaDia(), ponto.getSaidaAlmoco(),null,null);
		}else if(!Util.isNull(ponto.getEntradaDia()) && !Util.isNull(ponto.getSaidaDia())){
			tempo = DataUtil.calculaMinutos(ponto.getEntradaDia(), ponto.getSaidaDia(),null,null);
		}
		
		return tempo;
	}
	
	private Ponto calculaTempo(Ponto ponto){

		long horas = ponto.getMinutosTrabalhados()/60;
		long minutos = ponto.getMinutosTrabalhados() - (horas * 60);
		
		String minuts = String.valueOf(minutos);
		if(minuts.length() < 2){
			minuts = "0" +minuts;
		}
				
		String tempo = String.valueOf(horas) +":"+ minuts;
		
		
		ponto.setTempoTrabalhado(tempo);
		
		return ponto;
	}
	
}

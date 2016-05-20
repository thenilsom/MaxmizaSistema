package br.com.autocom.saa.bo;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.autocom.saa.dom.VersaoAtendimento;
import br.com.autocom.saa.exception.NegocioException;
import br.com.autocom.saa.presistence.VersaoAtendimentoDAO;
import br.com.autocom.saa.util.Util;
import br.com.sw3.persistence.exception.DAOException;

/**
 * Classe de Implementação de Negócio referente a {@link VersaoAtendimento}.
 * 
 * @author Denilson Godinho
 *
 */

@Named
@Dependent
@Transactional(rollbackOn = { NegocioException.class })
public class VersaoAtendimentoBO {
	
	@Inject
	private VersaoAtendimentoDAO versaoAtendimentoDAO;

	/**
	 * Obtem a versao atual do atendimento se for null seta 0
	 * @return
	 * @throws NegocioException
	 */
	public VersaoAtendimento versaoAtual() throws NegocioException {
		try {
			VersaoAtendimento versaoAtendimento = versaoAtendimentoDAO.consultar(new Long(1));
			if(Util.isNull(versaoAtendimento)){
				versaoAtendimento = new VersaoAtendimento();
				versaoAtendimento.setVersaoAtual(new Long(0));
			}
			
			return versaoAtendimento;
		} catch (DAOException e) {
			throw new NegocioException(e);
		}
	}
	
	/**
	 * Incrementa a versao atual do atendimento
	 * @throws NegocioException
	 */
	public void incrementarVersao() throws NegocioException {
		try {
			VersaoAtendimento versaoAtendimento = versaoAtual();
			Long versaoAtual = versaoAtendimento.getVersaoAtual() +1;
			versaoAtendimento.setVersaoAtual(versaoAtual);
			versaoAtendimentoDAO.persistir(versaoAtendimento);
		} catch (DAOException e) {
			throw new NegocioException(e);
		}
	}

}

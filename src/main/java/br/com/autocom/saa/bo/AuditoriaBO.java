package br.com.autocom.saa.bo;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.autocom.saa.dom.Auditoria;
import br.com.autocom.saa.dom.filtro.AuditoriaFiltro;
import br.com.autocom.saa.exception.NegocioException;
import br.com.autocom.saa.presistence.AuditoriaDAO;
import br.com.sw3.persistence.exception.DAOException;

/**
 * Classe de Implementação de Negócio referente a {@link Auditoria}
 * 
 * @author Denilson Godinho
 *
 */

@Named
@Dependent
@Transactional(rollbackOn = { NegocioException.class })
public class AuditoriaBO {
	
	@Inject
	private AuditoriaDAO auditoriaDAO;
	
	public void salvar(Auditoria auditoria) throws NegocioException{
		
		try {
			
			auditoriaDAO.persistir(auditoria);
			
		} catch (DAOException e) {
			throw new NegocioException(e);
		}
	}
	
	/**
	 * Retorna a lista com todos os assuntos existentes na base de dados.
	 * 
	 * @return
	 * @throws NegocioException
	 */
	public List<Auditoria> listarTudo() throws NegocioException {
		try {
			return auditoriaDAO.listarTudo();
		} catch (DAOException e) {
			throw new NegocioException(e);
		}
	}

	/**
	 * @return
	 * @throws NegocioException 
	 */
	public List<Auditoria> listarFiltro(AuditoriaFiltro filtro) throws NegocioException {
		
		try {
			return auditoriaDAO.listarFiltro(filtro);
		} catch (DAOException e) {
			throw new NegocioException(e);
		}
	}
}

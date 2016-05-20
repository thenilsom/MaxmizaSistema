package br.com.autocom.saa.bo;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.autocom.saa.config.MessageCode;
import br.com.autocom.saa.controller.AuditoriaController;
import br.com.autocom.saa.dom.Assunto;
import br.com.autocom.saa.dom.enums.Acao;
import br.com.autocom.saa.dom.enums.Tabela;
import br.com.autocom.saa.exception.NegocioException;
import br.com.autocom.saa.presistence.AssuntoDAO;
import br.com.autocom.saa.util.Util;
import br.com.sw3.persistence.exception.DAOException;

/**
 * Classe de Implementação de Negócio referente a {@link Assunto}.
 * 
 * 
 * @author Samuel Boreges de Oliveira
 */
@Named
@Dependent
@Transactional(rollbackOn = { NegocioException.class })
public class AssuntoBO {
	
	@Inject
	private AssuntoDAO assuntoDAO;
		
	@Inject
	private AuditoriaController auditoriaController;
	
	/**
	 * Salva o {@link Assunto} na base de dados.
	 * 
	 * @param assunto
	 * @return
	 * 
	 * @throws NegocioException
	 */
	
	public Assunto salvar(Assunto assunto) throws NegocioException {
		
		Acao acao = Acao.ALTERAR;
		
		try {
			
			assunto = setarCampoMaiusculo(assunto);
			validarDadosObrigatorios(assunto);

			if (assunto.getId() == null) {
				acao = Acao.INCLUIR;
			} 
			
			Assunto assuntoSalvo = assuntoDAO.persistir(assunto);
			
			//Auditoria 
			auditoriaController.salvar(acao , assuntoSalvo.getId() , Tabela.ASSUNTO);
			
			return assuntoSalvo;
			
		} catch (DAOException e) {
			throw new NegocioException(e);
		}
	}
	
	/**
	 * Exclui o {@link Assunto} na base de dados.
	 * 
	 * @param assunto
	 * @return
	 * 
	 * @throws NegocioException
	 */
	public void excluir(Assunto assunto) throws NegocioException {
		try {
			
			if(assuntoDAO.existeAssunto(assunto.getId())){
				throw new NegocioException(MessageCode.MSG_011);
			}
			
			assuntoDAO.excluir(assunto);
			
			//Auditoria 
			auditoriaController.salvar(Acao.DELETAR , assunto.getId() ,Tabela.ASSUNTO);
			
		} catch (DAOException e) {
			throw new NegocioException(e);
		}
	}
	
	/**
	 * Retorna a instância de {@link Assunto} segundo o código informado.
	 * 
	 * @param codigo
	 * @return
	 * 
	 * @throws NegocioException 
	 */
	public Assunto consultar(Long id) throws NegocioException {
		try {
			return assuntoDAO.consultar(id);
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
	public List<Assunto> listarTudo() throws NegocioException {
		try {
			return assuntoDAO.listarTudo();
		} catch (DAOException e) {
			throw new NegocioException(e);
		}
	}

	/**
	 * Verifica se os campos de preenchimento Obrigatório foram preenchidos.
	 * 
	 * @param assunto
	 * @throws NegocioException
	 */
	private void validarDadosObrigatorios(final Assunto assunto) throws NegocioException {
		try{
			if (Util.isBlank(assunto.getDescricao())) {
				throw new NegocioException(MessageCode.MSG_006);
			}
			
			Long assuntoID = assuntoDAO.getID(assunto.getDescricao());
			if(assuntoID != null){
				if(assuntoID  != assunto.getId()){
					throw new NegocioException(MessageCode.MSG_014);
				}
			}
		} catch (DAOException e) {
			throw new NegocioException(e);
		}
	}
	
	/**
	 * Seta os campos que são maiusculos
	 * 
	 * @param assunto
	 */
	private Assunto setarCampoMaiusculo(Assunto assunto) {
		assunto.setDescricao(assunto.getDescricao().toUpperCase());	
		return assunto;
	}


}
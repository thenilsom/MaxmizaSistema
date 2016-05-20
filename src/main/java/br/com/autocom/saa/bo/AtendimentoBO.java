package br.com.autocom.saa.bo;

import java.time.LocalDateTime;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.autocom.saa.config.MessageCode;
import br.com.autocom.saa.controller.AuditoriaController;
import br.com.autocom.saa.dom.Atendimento;
import br.com.autocom.saa.dom.enums.Acao;
import br.com.autocom.saa.dom.enums.Status;
import br.com.autocom.saa.dom.enums.Tabela;
import br.com.autocom.saa.dom.filtro.AtendimentoFiltro;
import br.com.autocom.saa.exception.NegocioException;
import br.com.autocom.saa.presistence.AtendimentoDAO;
import br.com.autocom.saa.presistence.UsuarioDAO;
import br.com.autocom.saa.util.Util;
import br.com.sw3.persistence.exception.DAOException;
import br.com.sw3.security.Credential;

@Named
@Dependent
@Transactional(rollbackOn = { NegocioException.class })
public class AtendimentoBO {
	
	@Inject
	AtendimentoDAO atendimentoDAO;
	
	@Inject
	UsuarioDAO usuarioDAO;
	
	@Inject
	private AuditoriaController auditoriaController;
	
	@Inject
	private VersaoAtendimentoBO versaoAtendimentoBO;
	
	/**
	 * Salva o {@link Atendimento} na base de dados.
	 * 
	 * @param atendimento
	 * @return
	 * 
	 * @throws NegocioException
	 */
	public Atendimento salvar(Atendimento atendimento , Credential credential) throws NegocioException {
		
		Acao acao = Acao.ALTERAR;
		
		try {
			
			if(atendimento.getStatus() == Status.FINALIZADO ){
				atendimento.setDataFim(LocalDateTime.now());
				atendimento.setUsuarioSolucao(usuarioDAO.getUsuario(credential.getLogin()));
			}else{
				atendimento.setDataFim(null);
			}
			
			validarDadosObrigatorios(atendimento);
			
			if (atendimento.getId() == null) {
				acao = Acao.INCLUIR;
			} 
			
			Atendimento atendimentoSalvo = atendimentoDAO.persistir(atendimento);
			versaoAtendimentoBO.incrementarVersao();
			
			//Auditoria 
			auditoriaController.salvar(acao , atendimentoSalvo.getId() , Tabela.ATENDIMENTO);
			
			return atendimentoSalvo;
			
		} catch (DAOException e) {
			throw new NegocioException(e);
		}
	}
	
	/**
	 * Excluir o {@link Atendimento} na base de dados.
	 * @param atendimento
	 * @throws NegocioException
	 */
	public void excluir(Atendimento atendimento) throws NegocioException {
		try {

			atendimentoDAO.excluir(atendimento);
			
			//Auditoria 
			auditoriaController.salvar(Acao.DELETAR , atendimento.getId() ,Tabela.ATENDIMENTO);
			
		} catch (Exception e) {
			throw new NegocioException(e);
		}
		
		
	}
	
	/**
	 * Retorna a Quantidade de Atendimentos Pendentes / Em Atendimento
	 * 
	 * @return
	 * @throws NegocioException
	 */
	public Long qtdAtendimentos(String login) throws NegocioException {
		try {
			return atendimentoDAO.qtdAtendimentos(login); 
		} catch (DAOException e) {
			throw new NegocioException(e);
		}
	}
	
	/**
	 * Retorna a lista com todos os Atendimentos existentes na base de dados.
	 * 
	 * @return
	 * @throws NegocioException
	 */
	public List<Atendimento> listarTudo() throws NegocioException {
		try {
			List<Atendimento> lista = atendimentoDAO.listarTudo(); 
			return lista;
		} catch (DAOException e) {
			throw new NegocioException(e);
		}
	}
	
	/**
	 * Retorna a lista com todos os Atendimentos existentes do login passado.
	 * 
	 * @return
	 * @throws NegocioException
	 */
	public List<Atendimento> listarTudo(String login) throws NegocioException {
		try {
			List<Atendimento> lista = atendimentoDAO.listarTudo(login);
			return lista;
		} catch (DAOException e) {
			throw new NegocioException(e);
		}
	}
	
	/**
	 * retorna a lista de atendimentos de acordo com os filtros passados
	 * @param filtro
	 * @return
	 * @throws NegocioException
	 */
	public List<Atendimento> listarFiltro(AtendimentoFiltro filtro) throws NegocioException {
	try {
		return atendimentoDAO.listarFiltro(filtro);
	} catch (DAOException e) {
		throw new NegocioException(e);
	}
	}

	/**
	 * Verifica se os campos de preenchimento Obrigatório foram preenchidos.
	 * 
	 * @param atendimento
	 * @throws NegocioException
	 */
	private void validarDadosObrigatorios(final Atendimento atendimento) throws NegocioException {

		if (Util.isBlank(atendimento.getContato()) || (atendimento.getUsuarios().size() == 0) 
				|| ( atendimento.getCliente() == null ) || ( atendimento.getAssuntos() == null ) ) {
			throw new NegocioException(MessageCode.MSG_006);
		} 

	}
	
	
}

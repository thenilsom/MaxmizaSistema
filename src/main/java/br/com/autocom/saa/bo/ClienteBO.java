package br.com.autocom.saa.bo;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.autocom.saa.config.MessageCode;
import br.com.autocom.saa.controller.AuditoriaController;
import br.com.autocom.saa.dom.Cliente;
import br.com.autocom.saa.dom.enums.Acao;
import br.com.autocom.saa.dom.enums.Tabela;
import br.com.autocom.saa.exception.NegocioException;
import br.com.autocom.saa.presistence.ClienteDAO;
import br.com.autocom.saa.util.Util;
import br.com.autocom.saa.util.ValidaUtils;
import br.com.sw3.persistence.exception.DAOException;

/**
 * Classe de Implementação de Negócio referente a {@link Cliente}.
 * 
 * @author Denilson Godinho
 *
 */

@Named
@Dependent
@Transactional(rollbackOn = { NegocioException.class })
public class ClienteBO {

	@Inject
	private ClienteDAO clienteDAO;
	
	@Inject
	private AuditoriaController auditoriaController;
	
	/**
	 * Salvar o {@link Cliente} na base de dados.
	 * @param cliente
	 * @return
	 * @throws NegocioExceptiondfdfdf
	 */
	public Cliente salvar(Cliente cliente) throws NegocioException{
		
		Acao acao = Acao.ALTERAR;
		
		try {
			validarDadosObrigatorios(cliente);
			//retira os caracteres especiais para salvar no banco de dados
			cliente.setCnpj(cliente.getCnpj().replace(".", "").replace("/", "").replace("-", ""));
			validarNegocio(cliente);
			
			if (cliente.getId() == null) {
				acao = Acao.INCLUIR;
			}
			
			Cliente clienteSalvo = clienteDAO.persistir(cliente);
			
			//Auditoria 
			auditoriaController.salvar(acao , clienteSalvo.getId() ,Tabela.CLIENTE);
			
			return clienteSalvo;
		} catch (DAOException e) {
			throw new NegocioException(e);
		}
	}
	
	
	/**
	 * Retorna a lista com todos os Clientes existentes na base de dados.
	 * 
	 * @return
	 * @throws NegocioException
	 */
	public List<Cliente> listarTudo() throws NegocioException {
		try {
			return clienteDAO.listarTudo();
		} catch (DAOException e) {
			throw new NegocioException(e);
		}
	}
	
	/**
	 * Retorna a lista com todos os Clientes ativos na base de dados.
	 * 
	 * @return
	 * @throws NegocioException
	 */
	public List<Cliente> listarAtivos() throws NegocioException {
		try {
			return clienteDAO.listarAtivos();
		} catch (DAOException e) {
			throw new NegocioException(e);
		}
	}
	
	private void validarDadosObrigatorios(Cliente cliente) throws NegocioException {
		
		if(Util.isBlank(cliente.getNomeFantasia()) || Util.isBlank(cliente.getRazaoSocial()) || 
				cliente.getCnpj() == null || cliente.getCodigo() ==null){
			throw new NegocioException(MessageCode.MSG_006);
		}
		
	}
	
	private void validarNegocio(Cliente cliente) throws NegocioException {
		if(!ValidaUtils.isValidCNPJ(cliente.getCnpj())){
			throw new NegocioException(MessageCode.MSG_008);
		}

		Long id = clienteDAO.getID(cliente.getCnpj());
		if(!Util.isNull(id)){
			if(!id.equals(cliente.getId())){
				throw new NegocioException(MessageCode.MSG_009);
			}
		}
		id = clienteDAO.getID(cliente.getCodigo());
		if(!Util.isNull(id)){
			if(!id.equals(cliente.getId())){
				throw new NegocioException(MessageCode.MSG_012);
			}
		}
	}



	/**
	 * Excluir o {@link Cliente} na base de dados.
	 * @param cliente
	 * @throws NegocioException
	 */
	public void excluir(Cliente cliente) throws NegocioException {
		try {
			
			if(clienteDAO.existeCliente(cliente.getId())){
				throw new NegocioException(MessageCode.MSG_011);
			}
			
			clienteDAO.excluir(cliente);
			
			//Auditoria 
			auditoriaController.salvar(Acao.DELETAR , cliente.getId() , Tabela.CLIENTE);
			
		} catch (DAOException e) {
			throw new NegocioException(e);
		}
		
		
	}
	

}

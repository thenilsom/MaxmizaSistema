package br.com.autocom.saa.bo;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.autocom.saa.config.MessageCode;
import br.com.autocom.saa.dom.Recado;
import br.com.autocom.saa.dom.Usuario;
import br.com.autocom.saa.exception.NegocioException;
import br.com.autocom.saa.presistence.RecadoDAO;
import br.com.autocom.saa.util.Util;
import br.com.sw3.persistence.exception.DAOException;
import br.com.sw3.security.Credential;

@Named
@Dependent
@Transactional(rollbackOn = {NegocioException.class})
public class RecadoBO {
	
	@Inject
	private RecadoDAO recadoDAO;
	
	@Inject
	private UsuarioBO usuarioBO;

	/**
	 * Salva um novo recado na base de dados
	 * @param recado
	 * @return
	 * @throws NegocioException
	 */
	public Recado salvar(Recado recado, Credential credential) throws NegocioException {
		try {
			
			validarDadosObrigatorios(recado);
		
			Usuario usuarioRecado = usuarioBO.consultar(credential.getLogin());
			recado.setDe(usuarioRecado);
			
			Recado recadoSalvo = recadoDAO.persistir(recado);
			return recadoSalvo;
		} catch (DAOException e) {
			throw new NegocioException(e);
		}
		
	}
	
	/**
	 * Exclui o {@link Recado} na base de dados.
	 * 
	 * @param recado
	 * @return
	 * 
	 * @throws NegocioException
	 */
	public void excluir(Recado recado) throws NegocioException {
		try {			
			recadoDAO.excluir(recado);
						
		} catch (DAOException e) {
			throw new NegocioException(e);
		}
	}
	
	/**
	 * Retorna a instância de {@link Recado} segundo o código informado.
	 * 
	 * @param codigo
	 * @return
	 * 
	 * @throws NegocioException 
	 */
	public Recado consultar(Long id) throws NegocioException {
		try {
			return recadoDAO.consultar(id);
		} catch (DAOException e) {
			throw new NegocioException(e);
		}
	}

	/**
	 * Retorna a lista com todos os recados enviados do usuario passado como parametro.
	 * 
	 * @return
	 * @throws NegocioException
	 */
	public List<Recado> listarRecadosEnviados(String login) throws NegocioException {
		try {
			return recadoDAO.listarRecadosEnviados(login);
		} catch (DAOException e) {
			throw new NegocioException(e);
		}
	}
	
	/**
	 * Retorna a lista com todos os recados recebidos do usuario passado como parametro.
	 * 
	 * @return
	 * @throws NegocioException
	 */
	public List<Recado> listarRecadosRecebidos(String login) throws NegocioException {
		try {
			return recadoDAO.listarRecadosRecebidos(login);
		} catch (DAOException e) {
			throw new NegocioException(e);
		}
	}

	private void validarDadosObrigatorios(final Recado recado) throws NegocioException {
		
		if(Util.isNull(recado.getPara().size() == 0) || Util.isBlank(recado.getRecado())){
			throw new NegocioException(MessageCode.MSG_006);
		}
	}

	/**
	 * Retorna a Quantidade de Recados não Lidos.
	 * @param login
	 * @return
	 * @throws NegocioException
	 */
	public Long qtdRecados(String login) throws NegocioException {
		try {
			return recadoDAO.qtdRecados(login);
		} catch (DAOException e) {
			throw new NegocioException(e);
		}
	}

	/**
	 * Marca o recado passado como lido
	 * @param recado
	 * @return
	 * @throws NegocioException
	 */
	public Recado lerRecado(Recado recado) throws NegocioException {
		try {
			recado.setLido(true);
			Recado lido = recadoDAO.persistir(recado);
			return lido;
		} catch (DAOException e) {
			throw new NegocioException(e);
		}
		
	}

}

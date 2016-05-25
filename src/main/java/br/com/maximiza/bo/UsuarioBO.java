package br.com.maximiza.bo;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.com.autocom.saa.dom.Usuario;
import br.com.maximiza.config.MessageCode;
import br.com.maximiza.exception.NegocioException;
import br.com.maximiza.presistence.UsuarioDAO;
import br.com.maximiza.to.UsuarioTO;
import br.com.maximiza.util.Util;
import br.com.sw3.persistence.exception.DAOException;

/**
 * Classe de Implementação de Negócio referente a {@link Usuario}.
 * 
 * @author Paulo Leonardo de O. Miranda
 */
@Named
@Dependent
@Transactional(rollbackOn = { NegocioException.class })
public class UsuarioBO {
	
	@Inject
	private UsuarioDAO usuarioDAO;
	
		
/**
	 * Salva o {@link Usuario} na base de dados.
	 * 
	 * @param usuario
	 * @return
	 * 
	 * @throws NegocioException
	 */
	public Usuario salvar(Usuario usuario) throws NegocioException {
		
	
		
		try {
				validarDadosObrigatorios(usuario);
				if(!validarLogin(usuario)){
					//Se for novo usuário criptografa senha
					//Se for editar usuário e ele alterou a senha, criptografa senha novamente.
					if(usuario.getId() != null){
						Usuario auxUsuario = consultar(usuario.getId());
						if(!usuario.getSenha().equals(auxUsuario.getSenha())){
							usuario.setSenha(Util.getValorCriptografadoMD5(usuario.getSenha()));
						}
					}else{
						usuario.setSenha(Util.getValorCriptografadoMD5(usuario.getSenha()));
					}
				}else{
					throw new NegocioException(MessageCode.MSG_010);
				}
				
				Usuario usuarioSalvo = usuarioDAO.persistir(usuario);
								
				return usuarioSalvo;
			
		}catch (DAOException e) {
			throw new NegocioException(e);
		}
	}
	
	/**
	 * Edita o {@link Usuario} na base de dados.
	 * 
	 * @param usuario
	 * @return
	 * 
	 * @throws NegocioException
	 */
	public Usuario editar(Usuario usuario) throws NegocioException {
		try {
			validarDadosObrigatorios(usuario);
			if(!validarLogin(usuario)){
				return usuarioDAO.persistir(usuario);
			}else{
				throw new NegocioException(MessageCode.MSG_010);
			}
			
		} catch (DAOException e) {
			throw new NegocioException(e);
		}
	}
	
	/**
	 * Excluir o {@link Usuario} na base de dados.
	 * @param usuario
	 * @throws NegocioException
	 */
	public void excluir(Usuario usuario) throws NegocioException {
		try {
			
			if(usuarioDAO.existeUsuario(usuario.getId())){
				throw new NegocioException(MessageCode.MSG_011);
			}
			
			usuarioDAO.excluir(usuario);
			
			
		} catch (DAOException e) {
			throw new NegocioException(e);
		}
		
		
	}
	
	
		
	/**
	 * Retorna a instância de {@link Usuario} segundo o código informado.
	 * 
	 * @param codigo
	 * @return
	 * 
	 * @throws NegocioException 
	 */
	public Usuario consultar(Long id) throws NegocioException {
		try {
			return usuarioDAO.consultar(id);
		} catch (DAOException e) {
			throw new NegocioException(e);
		}
	}
	
	/**
	 * Retorna a instância de {@link Usuario} segundo o login informado.
	 * 
	 * @param login
	 * @return
	 * 
	 * @throws NegocioException 
	 */
	public Usuario consultar(String login) throws NegocioException {
		
			try {
				return usuarioDAO.getUsuario(login);
			} catch (DAOException e) {
				throw new NegocioException(e);
			}
		
	}
	
	/**
	 * Retorna a instância de {@link Usuario} pelo login informado.
	 * @param login
	 * @return
	 * @throws NegocioException
	 */
	public Usuario getUsuarioLogin(String login)throws NegocioException{
		try {
			return usuarioDAO.getUsuario(login);
		} catch (DAOException e) {
			throw new NegocioException(e);
		}
	}

	
	/**
	 * Retorna a lista com todos os Usuarios existentes na base de dados.
	 * 
	 * @return
	 * @throws NegocioException
	 */
	public List<Usuario> listarTudo() throws NegocioException {
		try {
			return usuarioDAO.listarTudo();
		} catch (DAOException e) {
			throw new NegocioException(e);
		}
	}

	/**
	 * Retorna a lista com todos os Usuarios ativos na base de dados.
	 * 
	 * @return
	 * @throws NegocioException
	 */
	public List<Usuario> listarAtivos() throws NegocioException {
		try {
			return usuarioDAO.listarAtivos();
		} catch (DAOException e) {
			throw new NegocioException(e);
		}
	}
	
	
	/**
	 * Verifica se os campos de preenchimento Obrigatório foram preenchidos.
	 * 
	 * @param usuario
	 * @throws NegocioException
	 */
	private void validarDadosObrigatorios(final Usuario usuario) throws NegocioException {

		if (Util.isBlank(usuario.getLogin()) || Util.isBlank(usuario.getNome()) 
				|| Util.isBlank(usuario.getSenha()) || ( usuario.getPerfil() == null ) ) {
			throw new NegocioException(MessageCode.MSG_006);
		}

	}
	
	/**
	 * Recupera o {@link Usuario} segundo o Usuário e Senha informada.
	 * 
	 * @param usuarioTO
	 * @return
	 * @throws NegocioException
	 */
	public Usuario getUsuario(final UsuarioTO usuarioTO) throws NegocioException {
		try {
			if(Util.isBlank(usuarioTO.getLogin()) || Util.isBlank(usuarioTO.getSenha())) {
				throw new NegocioException(MessageCode.MSG_003);
			}
			
			String senha = Util.getValorCriptografadoMD5(usuarioTO.getSenha());
			//String senha = usuarioTO.getSenha();
			Usuario autenticado = usuarioDAO.getUsuario(usuarioTO.getLogin(), senha);
			
			if(autenticado == null) {
				throw new NegocioException(MessageCode.MSG_002);
			}
			
			if(!autenticado.isAtivo()){
				throw new NegocioException(MessageCode.MSG_013);
			}
			
			return autenticado;
		} catch (DAOException e) {
			throw new NegocioException(e);
		}
	}

	
	/**
	 * Verifica se já existe login no banco de dados.
	 * 
	 * @param usuario
	 * @return
	 * @throws NegocioException
	 */
	private boolean validarLogin(Usuario usuario) throws NegocioException {
		try {
			Long auxId = usuarioDAO.getID(usuario.getLogin());
			if(auxId != null){
				if(auxId != usuario.getId()){
					return true;
				}
			}
			return false;
		} catch (DAOException e) {
			throw new NegocioException(e);
		}
	}

}
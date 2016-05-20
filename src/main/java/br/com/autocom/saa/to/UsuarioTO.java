package br.com.autocom.saa.to;

import java.util.List;

/**
 * Classe de transporte responsavel por manter os dados do Usuário.
 * 
 * @author Paulo Leonardo de O. Miranda
 */
public class UsuarioTO {

	private String login;
	
	private String nome;
	
	private String senha;
	
	private String token;
	
	private List<String> acessRoles;

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return the senha
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * @param senha the senha to set
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the acessRoles
	 */
	public List<String> getAcessRoles() {
		return acessRoles;
	}

	/**
	 * @param acessRoles the acessRoles to set
	 */
	public void setAcessRoles(List<String> acessRoles) {
		this.acessRoles = acessRoles;
	}
	
	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	
}

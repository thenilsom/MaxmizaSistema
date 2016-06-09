package br.com.maximiza.dom;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.maximiza.dom.enums.Perfil;
import br.com.sw3.persistence.entity.Entidade;

/**
 * Class de representação de Usuário.
 * 
 * @author Paulo Leonardo de O. Miranda
 * 
 */
@Entity
@Table(name = "usuario")
@SequenceGenerator(name = "usuario_seq", sequenceName = "usuario_seq", initialValue = 1, allocationSize = 1)
public class Usuario implements Entidade<Long> {

	private static final long serialVersionUID = -5446916461543864082L;

	@Id
	@Column(name = "usuario_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
	private Long id;
	
	@Column(name = "nome", length = 50)
	private String nome;
	
	@Column(name = "login", length = 50)
	private String login;
	
	@Column(name = "senha", length = 255)
	private String senha;
	
	@Column(name = "admin")
	private boolean administrador;
	
	@Column(name = "comissao")
	private BigDecimal comissao;
	
		
	@Transient
	private String confirmacaoSenha;

	
	/**
	 * @see arq.persistence.entity.Entidade#getId()
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	

	public boolean isAdministrador() {
		return administrador;
	}

	public void setAdministrador(boolean administrador) {
		this.administrador = administrador;
	}

	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}
	
	/**
	 * Retorna o Perfil do Usuario
	 * @return
	 */
	public Perfil getPerfilUsuario(){
	 return isAdministrador() ? Perfil.ADMIN : Perfil.SECRETARIA;
	}

	public BigDecimal getComissao() {
		return comissao;
	}

	public void setComissao(BigDecimal comissao) {
		this.comissao = comissao;
	}
	

}

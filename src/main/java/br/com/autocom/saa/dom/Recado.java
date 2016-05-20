package br.com.autocom.saa.dom;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.sw3.persistence.entity.Entidade;

@Entity
@Table(name = "recado")
@SequenceGenerator(name = "recado_seq", sequenceName = "recado_seq", initialValue = 1, allocationSize = 1)
public class Recado implements Entidade<Long> {

	private static final long serialVersionUID = 3189802955092326878L;

	@Id
	@Column(name = "recado_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recado_seq")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "de")
	private Usuario de;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "recado_usuario", joinColumns = {@JoinColumn(name="recado_id")},
	inverseJoinColumns = {@JoinColumn(name="usuario_id")})
	private Set<Usuario> para = new HashSet<Usuario>();
	
	@Column(name = "recado", length = 300)
	private String recado;
	
	@Column(name = "lido")
	private boolean lido;

	/**
	 * @return the id
	 */
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
	 * @return the de
	 */
	public Usuario getDe() {
		return de;
	}

	/**
	 * @param de the de to set
	 */
	public void setDe(Usuario de) {
		this.de = de;
	}

	/**
	 * @return the para
	 */
	public Set<Usuario> getPara() {
		return para;
	}

	/**
	 * @param para the para to set
	 */
	public void setPara(Set<Usuario> para) {
		this.para = para;
	}

	/**
	 * @return the recado
	 */
	public String getRecado() {
		return recado;
	}

	/**
	 * @param recado the recado to set
	 */
	public void setRecado(String recado) {
		this.recado = recado;
	}

	/**
	 * @return the lido
	 */
	public boolean isLido() {
		return lido;
	}

	/**
	 * @param lido the lido to set
	 */
	public void setLido(boolean lido) {
		this.lido = lido;
	}

	
}

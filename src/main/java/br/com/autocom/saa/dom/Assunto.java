package br.com.autocom.saa.dom;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.sw3.persistence.entity.Entidade;

/**
 * Class de representação de Assunto.
 * 
 * 
 * @author Samuel Oliveira
 * 
 */
@Entity
@Table(name = "assunto")
@SequenceGenerator(name = "assunto_seq", sequenceName = "assunto_seq", initialValue = 1, allocationSize = 1)
public class Assunto implements Entidade<Long> {

	private static final long serialVersionUID = -5446916461543865982L;

	@Id
	@Column(name = "assunto_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assunto_seq")
	private Long id;
	
	@Column(name = "descricao", length = 50)
	private String descricao;

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
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	

}

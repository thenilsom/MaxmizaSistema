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
 * Classe de representação da versao atual do atendimento
 * @author Denilson
 *
 */

@Entity
@Table(name = "versao_atendimento")
@SequenceGenerator(name = "versao_atendimento_seq", sequenceName = "versao_atendimento_seq", initialValue = 1, allocationSize = 1)
public class VersaoAtendimento implements Entidade<Long> {
	
	private static final long serialVersionUID = -5084688671186665322L;
	
	@Id
	@Column(name = "versao_atendimento_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "versao_atendimento_seq")
	private Long id;
	
	@Column(name = "versao_atual")
	private Long versaoAtual;
	
	

	/**
	 * @see arq.persistence.entity.Entidade#getId()
	 */
	public Long getId() {
		return this.id;
	}
	
	/**
	 * 
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 
	 * @return the versaoAtual
	 */
	public Long getVersaoAtual() {
		return versaoAtual;
	}


	/**
	 * 
	 * @param versaoAtual the versaoAtual to set
	 */
	public void setVersaoAtual(Long versaoAtual) {
		this.versaoAtual = versaoAtual;
	}


}

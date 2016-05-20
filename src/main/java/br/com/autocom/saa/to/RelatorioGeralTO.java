package br.com.autocom.saa.to;

import br.com.sw3.persistence.entity.Entidade;

/**
 * Classe de representação do relatório atendimento_por_usuario.
 * 
 * @author Danillo Santana
 */

public class RelatorioGeralTO implements Entidade<Long> {
	
	
	private static final long serialVersionUID = 5370255302304018855L;
	private String nome;
	private Long atendimentos;//quantidade de atendimentos
	private Long atendimentoSolucao; //quantidade de atendimentos Solucionados
	
	
	public RelatorioGeralTO() {
		super();
	}

	public RelatorioGeralTO(String nome, Long qtdAtendimentos) {
		super();
		this.nome = nome;
		this.atendimentos = qtdAtendimentos;
	}
	
	public RelatorioGeralTO(String nome, Long qtdAtendimentos, Long qtdAtendimentosSolucao) {
		super();
		this.nome = nome;
		this.atendimentos = qtdAtendimentos;
		this.atendimentoSolucao = qtdAtendimentosSolucao;
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
	 * @return the qtdAtendimentos
	 */
	public Long getAtendimentos() {
		return atendimentos;
	}

	/**
	 * @param qtdAtendimentos the qtdAtendimentos to set
	 */
	public void setAtendimentos(Long qtdAtendimentos) {
		this.atendimentos = qtdAtendimentos;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the atendimentoSolucao
	 */
	public Long getAtendimentoSolucao() {
		return atendimentoSolucao;
	}

	/**
	 * @param atendimentoSolucao the atendimentoSolucao to set
	 */
	public void setAtendimentoSolucao(Long atendimentoSolucao) {
		this.atendimentoSolucao = atendimentoSolucao;
	}
	
}

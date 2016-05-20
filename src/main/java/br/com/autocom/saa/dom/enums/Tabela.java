package br.com.autocom.saa.dom.enums;

public enum Tabela {

	ASSUNTO("Assunto"), CLIENTE("Cliente"), USUARIO("Usuário"), ATENDIMENTO("Atendimento"), PONTO("Ponto");
	
	private final String descricao;
	
	private Tabela(final String descricao){
		this.descricao = descricao;
	}
	
	/**
	 * 
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}
}

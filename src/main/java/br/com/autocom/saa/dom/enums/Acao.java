package br.com.autocom.saa.dom.enums;

public enum Acao {

	ALTERAR("Alterar"), INCLUIR("Incluir"), DELETAR("Deletar");
	
	private final String descricao;
	
	private Acao(final String descricao){
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

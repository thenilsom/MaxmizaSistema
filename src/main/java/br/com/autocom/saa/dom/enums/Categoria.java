package br.com.autocom.saa.dom.enums;

public enum Categoria {
	
	RG("RG"), RIC("Ricardo"), GIU("Giuliano");
	
	private final String descricao;
	
	private Categoria(final String descricao){
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

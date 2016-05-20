package br.com.autocom.saa.dom.enums;

public enum Status {
	
	EM_ANDAMENTO("Em Andamento"),PENDENTE("Pendente"), FINALIZADO("Finalizado");
	
	private final String descricao;
	
	private Status(final String descricao){
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

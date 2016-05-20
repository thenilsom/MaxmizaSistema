package br.com.autocom.saa.dom.enums;

public enum PeriodoPonto {
	
	ENTRADA("Entrada"),SAIDAALMOCO("Saída Almoço"),RETORNOALMOCO("Retorno Almoço"), SAIDA("Saída");
	
	private final String descricao;
	
	private PeriodoPonto(final String descricao){
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

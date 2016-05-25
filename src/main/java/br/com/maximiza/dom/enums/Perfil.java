package br.com.maximiza.dom.enums;

/**
 * Enum com as possíveis representações de Perfis de acesso a aplicação.
 * 
 * @author Paulo Leonardo O. Miranda
 * 
 */
public enum Perfil {
	ADMIN("Administrador"), SECRETARIA("Secretária"), TECNICO("Técnico");

	private final String descricao;

	private Perfil(final String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

}

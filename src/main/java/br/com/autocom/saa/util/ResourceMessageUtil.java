package br.com.autocom.saa.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Classe utilitária que provê os métodos para manipulação do arquivo
 * *.properties com as mensagens e descrições apresentadas no sistema.
 * 
 * @author Paulo Leonardo de O. Miranda.
 */
public final class ResourceMessageUtil {

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("message");

	/**
	 * Retorna a mensagem associada a chave informada.
	 * 
	 * @param chave
	 * @return
	 */
	public static String getDescricao(final String chave) {
		if (chave == null || chave.length() == 0) {
			throw new IllegalArgumentException("A chave do atributo informado é inválida.");
		}
		return RESOURCE_BUNDLE.getString(chave);
	}

	/**
	 * Retorna a mensagem associada a chave informada.
	 * 
	 * @param code
	 * @return
	 */
	public static String getDescricao(final Message code) {
		return getDescricao(code.getCode());
	}

	/**
	 * Retorna a mensagem de erro, formatada com a descrição do
	 * {@link Throwable} informado.
	 * 
	 * @param e
	 * @return
	 */
	public static String getDescricaoErro(final String chave, final Throwable e) {
		String descricao = getDescricao(chave);
		return MessageFormat.format(descricao, e);
	}

	/**
	 * Retorna a mensagem de erro, formatada com a descrição do
	 * {@link Throwable} informado.
	 * 
	 * @param chave
	 * @param e
	 * @return
	 */
	public static String getDescricaoErro(final Message chave, final Throwable e) {
		return getDescricaoErro(chave.getCode(), e);
	}
}

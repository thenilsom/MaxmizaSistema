package br.com.maximiza.exception;

import br.com.maximiza.util.Message;
import br.com.maximiza.util.ResourceMessageUtil;

/**
 * Exceção a ser lançada na ocorrência de falhas e validações de negócio.
 * 
 * @author Paulo Leonardo de O. Miranda
 */
public class NegocioException extends Exception {

	private static final long serialVersionUID = 7524792821876845930L;

	private Message code;
	
	/**
	 * Construtor da classe.
	 * 
	 * @param e
	 */
	public NegocioException(Throwable e) {
		super(e);
	}

	/**
	 * Construtor da classe.
	 * 
	 * @param code
	 */
	public NegocioException(Message code) {
		this((Throwable) null);
		this.code = code;
	}

	/**
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return code != null ? ResourceMessageUtil.getDescricao(code) : super.getMessage();
	}

	/**
	 * @return the code
	 */
	public Message getCode() {
		return code;
	}
}
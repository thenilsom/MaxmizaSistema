package br.com.autocom.saa.config;

import br.com.autocom.saa.util.Message;

/**
 * Enum que representa as chaves de mensagens necessárias no sistema.
 * 
 * @author Paulo Leonardo de O. Miranda
 * 
 */
public enum	MessageCode implements Message {
	MSG_001, MSG_002, MSG_003, MSG_005, MSG_006, MSG_007, MSG_008, MSG_009, MSG_010, MSG_011,MSG_012,MSG_013,MSG_014,MSG_015,MSG_016,MSG_017;

	/**
	 * @see br.com.autocom.saa.util.Message#getCode()
	 */
	@Override
	public String getCode() {
		return this.toString();
	}
}
